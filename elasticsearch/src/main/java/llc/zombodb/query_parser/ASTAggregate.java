/*
 * Copyright 2013-2015 Technology Concepts & Design, Inc
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
package llc.zombodb.query_parser;

import llc.zombodb.query_parser.metadata.IndexMetadata;

public class ASTAggregate extends QueryParserNode {
    private boolean isNested;

    public ASTAggregate(int i) {
        super(i);
    }

    public ASTAggregate getSubAggregate() {
        for (QueryParserNode node : this) {
            if (node instanceof ASTAggregate)
                return (ASTAggregate) node;
        }
        return null;
    }

    public String getStem() {
        QueryParserNode node = getChild(0);
        String stem = String.valueOf(node.getValue());
        if (stem.startsWith("^"))
            stem = stem.substring(1);

        if (node instanceof ASTPrefix)
            stem += "*";

        return stem;
    }


    public void setIsNested(boolean isNested) {
        this.isNested = isNested;
    }

    public boolean isSpecifiedAsNested() {
        return isNested;
    }

    public boolean isNested(IndexMetadata md) {
        return isNested && md.isNested(getFieldname());
    }

    public ASTAggregate(QueryParser p, int i) {
        super(p, i);
    }
}
