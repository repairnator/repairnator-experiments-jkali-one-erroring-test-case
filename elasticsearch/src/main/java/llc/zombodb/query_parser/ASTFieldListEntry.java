/* Generated By:JJTree: Do not edit this line. ASTFieldListEntry.java Version 6.1 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package llc.zombodb.query_parser;

import java.util.ArrayList;
import java.util.List;

public
class ASTFieldListEntry extends QueryParserNode {
  public ASTFieldListEntry(int id) {
    super(id);
  }

  public ASTFieldListEntry(QueryParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(QueryParserVisitor visitor, Object data) {

    return
    visitor.visit(this, data);
  }

  public List<String> getFields() {
    List<String> l = new ArrayList<>();
    for (QueryParserNode child : this) {
      if (child instanceof ASTArray) {
        for (QueryParserNode child2 : child)
          l.add(String.valueOf(child2.getValue()));
      }
    }
    return l;
  }

  @Override
  public String toString() {
    return "FieldListEntry (fieldname=" + this.fieldname + ")";
  }
}
/* JavaCC - OriginalChecksum=2b250058abb70d370b152210cb17b352 (do not edit this line) */
