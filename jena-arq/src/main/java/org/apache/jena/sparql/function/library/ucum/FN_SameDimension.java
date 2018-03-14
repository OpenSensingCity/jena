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
package org.apache.jena.sparql.function.library.ucum;

import javax.measure.Quantity;
import javax.measure.UnconvertibleException;
import org.apache.jena.atlas.lib.Lib;
import org.apache.jena.sparql.expr.ExprEvalException;
import org.apache.jena.sparql.expr.ExprEvalTypeException;
import org.apache.jena.sparql.expr.NodeValue;
import org.apache.jena.sparql.expr.nodevalue.NodeValueBoolean;
import org.apache.jena.sparql.function.FunctionBase2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author maxime.lefrancois
 */
public class FN_SameDimension extends FunctionBase2 {
    
    private static Logger LOG = LoggerFactory.getLogger(FN_SameDimension.class);

    public static final String IRI = "http://w3id.org/lindt/custom_datatypes#sameDimension";

    public FN_SameDimension() {
        super();
    }

    public NodeValue exec(NodeValue nv1, NodeValue nv2) {
        try {
            Quantity q1 = nv1.getQuantity();
            Quantity q2 = nv2.getQuantity();
            Quantity q3 = q2.to(q1.getUnit());
            return new NodeValueBoolean(true);
        } catch (ExprEvalTypeException ex) {
            LOG.debug("Arguments are not Quantity literals");
            System.out.println("Arguments are not Quantity literals");
            throw new ExprEvalException(Lib.className(this) + ": " + "Arguments must be Quantity literals");
        } catch (UnconvertibleException ex) {
            return new NodeValueBoolean(false);
        } catch (Exception ex) {
            LOG.debug("Other exception while casting: " + ex);
            System.out.println("Other exception while casting: " + ex);
            return new NodeValueBoolean(false);
        }
    }
}
