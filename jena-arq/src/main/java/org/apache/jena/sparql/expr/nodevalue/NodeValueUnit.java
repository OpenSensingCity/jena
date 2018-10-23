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

import java.util.Objects;
import javax.measure.Quantity;
import javax.measure.Unit;
import org.apache.jena.graph.Node;
import org.apache.jena.graph.NodeFactory;
import org.apache.jena.graph.impl.LiteralLabel;
import org.apache.jena.graph.impl.LiteralLabelFactory;
import org.apache.jena.sparql.expr.Expr;
import org.apache.jena.sparql.expr.NodeValue;
import tec.uom.se.quantity.Quantities;

/**
 *
 * @author maxime.lefrancois
 */
public class NodeValueUnit extends NodeValue {
    
    private final Unit unit;
    
    public NodeValueUnit(Unit unit) {
        this.unit = unit;
    }
    
    public NodeValueUnit(Unit unit, Node n) {
        super(n);
        this.unit = unit;
    }

    @Override
    public Unit getUnit() {
        return unit;
    }
    
    @Override
    public boolean isUnit()  {
        return true;
    }
    
    @Override
    protected Node makeNode() {
        final LiteralLabel label = LiteralLabelFactory.createTypedLiteral(unit);
        return NodeFactory.createLiteral(label);
    }

    @Override
    public void visit(NodeValueVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public boolean equals(Expr other, boolean bySyntax) {
        if ( other == null ) return false ;
        if ( this == other ) return true ;
        // This is the equality condition Jena uses - lang tags are different by case. 

        if ( ! ( other instanceof NodeValueUnit ) )
            return false ;
        NodeValueUnit nvq = (NodeValueUnit)other ;
        try {
            final Unit u1 = getUnit();
            final Quantity q1 = Quantities.getQuantity(1, u1);
            final Unit u2 = nvq.getUnit();
            final Quantity q2 = Quantities.getQuantity(1, u2);
            final Quantity q3 = q2.to(q1.getUnit());
            return Objects.equals(q1.getUnit(), q3.getUnit())
                    && q1.getValue().doubleValue()== q3.getValue().doubleValue();
        } catch (Exception e) {
            return  false;
        }
    }
    
    
}
