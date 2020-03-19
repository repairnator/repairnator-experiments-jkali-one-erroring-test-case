/* Generated By:JJTree: Do not edit this line. ASTBoolQuery.java Version 6.1 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package llc.zombodb.query_parser;

public
class ASTBoolQuery extends QueryParserNode {
  public ASTBoolQuery(int id) {
    super(id);
  }

  public ASTBoolQuery(QueryParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(QueryParserVisitor visitor, Object data) {

    return
    visitor.visit(this, data);
  }
  
  public ASTMust getMust() {
    for (Node n : this)
      if (n instanceof ASTMust)
        return (ASTMust) n;
    return null;
  }

  public ASTShould getShould() {
    for (Node n : this)
      if (n instanceof ASTShould)
        return (ASTShould) n;
    return null;
  }

  public ASTMustNot getMustNot() {
    for (Node n : this)
      if (n instanceof ASTMustNot)
        return (ASTMustNot) n;
    return null;
  }
}
/* JavaCC - OriginalChecksum=9ae37c95f5d7d0ffe2cff5661c9fc8c9 (do not edit this line) */
