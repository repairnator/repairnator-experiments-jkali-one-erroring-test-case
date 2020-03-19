package brave.grpc;

import brave.internal.MapPropagationFields;
import brave.internal.PropagationFieldsFactory;
import brave.propagation.MutableTraceContext;
import brave.propagation.Propagation;
import brave.propagation.TraceContext;
import brave.propagation.TraceContext.Extractor;
import brave.propagation.TraceContext.Injector;
import brave.propagation.TraceContextOrSamplingFlags;
import io.grpc.Metadata;
import io.grpc.MethodDescriptor;
import java.util.List;
import java.util.Map;

/** see {@link GrpcTracing.Builder#grpcPropagationFormatEnabled} for documentation. */
final class GrpcPropagation<K> implements Propagation<K> {

  /**
   * This creates a compatible metadata key based on Census, except this extracts a brave trace
   * context as opposed to a census span context
   */
  static final Metadata.Key<byte[]> GRPC_TRACE_BIN =
      Metadata.Key.of("grpc-trace-bin", Metadata.BINARY_BYTE_MARSHALLER);

  /** This stashes the tag context in "extra" so it isn't lost */
  static final Metadata.Key<Map<String, String>> GRPC_TAGS_BIN =
      Metadata.Key.of("grpc-tags-bin", new TagContextBinaryMarshaller());

  /** The census tag key corresponding to the {@link MethodDescriptor#fullMethodName}. */
  static final String RPC_METHOD = "method";

  static Propagation.Factory newFactory(Propagation.Factory delegate) {
    if (delegate == null) throw new NullPointerException("delegate == null");
    return new Factory(delegate);
  }

  static final class Factory extends Propagation.Factory {
    final Propagation.Factory delegate;
    final TagsFactory tagsFactory = new TagsFactory();

    Factory(Propagation.Factory delegate) {
      this.delegate = delegate;
    }

    @Override
    public boolean supportsJoin() {
      return false;
    }

    @Override
    public boolean requires128BitTraceId() {
      return true;
    }

    @Override
    public final <K> Propagation<K> create(KeyFactory<K> keyFactory) {
      return new GrpcPropagation<>(this, keyFactory);
    }

    @Override
    public <C, K> MutableTraceContext.Extractor<C> extractor(KeyFactory<K> keyFactory,
        Getter<C, K> getter) {
      MutableTraceContext.Extractor<C> extractorDelegate = delegate.extractor(keyFactory, getter);
      return new GrpcMutableExtractor<>(extractorDelegate, getter);
    }

    @Override public void decorate(MutableTraceContext mutableContext) {
      delegate.decorate(mutableContext);
      tagsFactory.decorate(mutableContext);
    }

    @Override protected boolean isDecorated(TraceContext context) {
      return tagsFactory.isDecorated(context);
    }
  }

  final Propagation<K> delegate;
  final TagsFactory extraFactory;

  GrpcPropagation(Factory factory, KeyFactory<K> keyFactory) {
    this.delegate = factory.delegate.create(keyFactory);
    this.extraFactory = factory.tagsFactory;
  }

  @Override
  public List<K> keys() {
    return delegate.keys();
  }

  @Override
  public <C> Injector<C> injector(Setter<C, K> setter) {
    return new GrpcInjector<>(this, setter);
  }

  @Override
  public <C> Extractor<C> extractor(Getter<C, K> getter) {
    return new GrpcExtractor<>(this, getter);
  }

  static final class GrpcInjector<C, K> implements Injector<C> {
    final Injector<C> delegate;
    final Propagation.Setter<C, K> setter;

    GrpcInjector(GrpcPropagation<K> propagation, Setter<C, K> setter) {
      this.delegate = propagation.delegate.injector(setter);
      this.setter = setter;
    }

    @Override
    public void inject(TraceContext traceContext, C carrier) {
      if (carrier instanceof Metadata) {
        byte[] serialized = TraceContextBinaryMarshaller.toBytes(traceContext);
        ((Metadata) carrier).put(GRPC_TRACE_BIN, serialized);
        Tags tags = traceContext.findExtra(Tags.class);
        if (tags != null) ((Metadata) carrier).put(GRPC_TAGS_BIN, tags.toMap());
      }
      delegate.inject(traceContext, carrier);
    }
  }

  static final class GrpcExtractor<C, K> implements Extractor<C> {
    final Extractor<C> delegate;
    final Propagation.Getter<C, K> getter;

    GrpcExtractor(GrpcPropagation<K> propagation, Getter<C, K> getter) {
      this.delegate = propagation.delegate.extractor(getter);
      this.getter = getter;
    }

    @Override
    public TraceContextOrSamplingFlags extract(C carrier) {
      Tags tags = null;
      if (carrier instanceof Metadata) {
        Map<String, String> extractedTags = ((Metadata) carrier).get(GRPC_TAGS_BIN);
        // Remove the incoming RPC method as we should replace it with our current server method.
        if (extractedTags != null) tags = new Tags(extractedTags, extractedTags.remove(RPC_METHOD));
        byte[] bytes = ((Metadata) carrier).get(GRPC_TRACE_BIN);
        if (bytes != null) {
          MutableTraceContext destination = new MutableTraceContext();
          TraceContextBinaryMarshaller.extract(bytes, destination);
          if (tags != null) destination.addExtra(tags);
          return TraceContextOrSamplingFlags.create(destination.toTraceContext());
        }
      }
      TraceContextOrSamplingFlags result = delegate.extract(carrier);
      if (tags == null) return result;
      return result.toBuilder().addExtra(tags).build();
    }
  }

  static final class GrpcMutableExtractor<C, K> implements MutableTraceContext.Extractor<C> {
    final MutableTraceContext.Extractor<C> delegate;
    final Propagation.Getter<C, K> getter;

    GrpcMutableExtractor(MutableTraceContext.Extractor<C> delegate, Getter<C, K> getter) {
      this.delegate = delegate;
      this.getter = getter;
    }

    @Override public void extract(C carrier, MutableTraceContext destination) {
      Tags tags = null;
      if (carrier instanceof Metadata) {
        Map<String, String> extractedTags = ((Metadata) carrier).get(GRPC_TAGS_BIN);
        // Remove the incoming RPC method as we should replace it with our current server method.
        if (extractedTags != null) tags = new Tags(extractedTags, extractedTags.remove(RPC_METHOD));
        byte[] bytes = ((Metadata) carrier).get(GRPC_TRACE_BIN);
        if (bytes != null) {
          TraceContextBinaryMarshaller.extract(bytes, destination);
          if (tags != null) destination.addExtra(tags);
          return;
        }
      }
      delegate.extract(carrier, destination);
      if (tags == null) return;
      destination.addExtra(tags);
    }
  }

  static final class TagsFactory extends PropagationFieldsFactory<Tags> {
    @Override
    protected Class type() {
      return Tags.class;
    }

    @Override
    protected Tags create() {
      return new Tags();
    }

    @Override
    protected Tags create(Tags parent) {
      return new Tags(parent);
    }
  }

  static final class Tags extends MapPropagationFields {
    final String parentMethod;

    Tags() {
      parentMethod = null;
    }

    Tags(Tags parent) {
      super(parent);
      parentMethod = null;
    }

    Tags(Map<String, String> extracted, String parentMethod) {
      super(extracted);
      this.parentMethod = parentMethod;
    }
  }
}
