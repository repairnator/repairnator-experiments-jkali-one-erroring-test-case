/*
 * Copyright (c) 2010-2014. Axon Framework
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.axonframework.eventhandling.async;

/**
 * Interface to a policy definition for concurrent processing, for example event handling.
 * <p/>
 * Some implementations are provided by default: <ul>
 * <li>{@link SequentialPolicy}: Default policy. Requires that all
 * tasks are handled in the order they arrive at the processor. This also means that at most 1 thread is processing
 * tasks for this processor at any time.</li>
 * <li>{@link FullConcurrencyPolicy}: Allows each tasks to be handled independently of any other tasks. Tasks
 * processing will typically start in the same order the tasks were scheduled in, although no guarantees can be given
 * about the actual processing order.</li>
 * <li>{@link SequentialPerAggregatePolicy}: Will force events (only supports Event Handling tasks) generated by the
 * same aggregate to be handled sequentially. At most one thread will be processing events of a single aggregate at any
 * time</li>
 * </ul>
 *
 * @param <T> The type of object representing the processing instruction for the event.
 * @author Allard Buijze
 * @since 0.3
 */
public interface SequencingPolicy<T> {

    /**
     * Returns the sequence identifier for the given {@code event}. When two events have the same identifier (as
     * defined by their equals method), they will be executed sequentially. A {@code null} value indicates that
     * there are no sequencing requirements for the handling of this event.
     *
     * @param event the event for which to get the sequencing identifier
     * @return a sequence identifier for the given event
     */
    Object getSequenceIdentifierFor(T event);
}
