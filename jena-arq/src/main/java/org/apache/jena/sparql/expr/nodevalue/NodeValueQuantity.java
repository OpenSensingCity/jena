/*
 * Copyright 2018 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.jena.sparql.expr.nodevalue;

import javax.measure.Quantity;
import org.apache.jena.graph.Node;
import org.apache.jena.graph.NodeFactory;
import org.apache.jena.graph.impl.LiteralLabel;
import org.apache.jena.graph.impl.LiteralLabelFactory;
import org.apache.jena.sparql.expr.Expr;
import org.apache.jena.sparql.expr.NodeValue;

/**
 *
 * @author maxime.lefrancois
 */
public class NodeValueQuantity extends NodeValue {
    
    private final Quantity quantity;
    
    public NodeValueQuantity(Quantity quantity) {
        this.quantity = quantity;
    }
    
    public NodeValueQuantity(Quantity quantity, Node n) {
        super(n);
        this.quantity = quantity;
    }

    @Override
    public Quantity getQuantity() {
        return quantity;
    }
    
    @Override
    public boolean isQuantity()  {
        return true;
    }
    
    @Override
    protected Node makeNode() {
        final LiteralLabel label = LiteralLabelFactory.createTypedLiteral(quantity);
        return NodeFactory.createLiteral(label);
    }

    @Override
    public void visit(NodeValueVisitor visitor) {
        visitor.visit(this);
    }    
}
