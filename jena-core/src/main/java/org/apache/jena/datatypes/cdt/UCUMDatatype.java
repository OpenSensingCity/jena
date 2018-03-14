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
package org.apache.jena.datatypes.cdt;

import java.util.Objects;
import javax.measure.Quantity;
import javax.measure.Unit;
import javax.measure.format.ParserException;
import javax.measure.format.UnitFormat;
import javax.measure.spi.ServiceProvider;
import org.apache.jena.datatypes.BaseDatatype;
import org.apache.jena.datatypes.DatatypeFormatException;
import org.apache.jena.graph.impl.LiteralLabel;
import systems.uom.ucum.internal.format.TokenException;
import tec.uom.se.quantity.Quantities;

/**
 *
 * @author maxime.lefrancois
 */
public class UCUMDatatype extends BaseDatatype {

    public static final String theTypeURI = "http://w3id.org/lindt/custom_datatypes#ucum";
    public static final UCUMDatatype theUCUMType = new UCUMDatatype();
    private static final UnitFormat unitFormat = ServiceProvider.current().getUnitFormatService().getUnitFormat("CI");

    /**
     * private constructor - single global instance
     */
    private UCUMDatatype() {
        super(theTypeURI);
    }

    /**
     * Convert a value of this datatype out to lexical form.
     */
    public String unparse(Object value) {
        if (!(value instanceof Quantity)) {
            throw new IllegalArgumentException("value must be an instance of Quantity");
        }
        Quantity q = (Quantity) value;
        return q.getValue() + " " + unitFormat.format(q.getUnit());
    }

    /**
     * Parse a lexical form of this datatype to a value
     *
     * @throws DatatypeFormatException if the lexical form is not legal
     */
    public Object parse(String lexicalForm) throws DatatypeFormatException {
        int index = lexicalForm.indexOf(" ");
        if (index == -1) {
            throw new DatatypeFormatException(lexicalForm, theUCUMType, "Lexical form must contain a space");
        }
        try {
            Unit<?> unit = unitFormat.parse(lexicalForm.substring(index + 1));
            float value = new Float(lexicalForm.substring(0, index));
            return Quantities.getQuantity(value, unit);
        } catch (TokenException e) {
            throw new DatatypeFormatException(lexicalForm, theUCUMType, "Unit is not valid: " + e.getMessage());
        } catch (ParserException e) {
            throw new DatatypeFormatException(lexicalForm, theUCUMType, "Unit is not valid: " + e.getMessage());
        } catch (NumberFormatException e) {
            throw new DatatypeFormatException(lexicalForm, theUCUMType, "Value is not a valid number: " + e.getMessage());
        }
    }

    /**
     * Compares two instances of values of the given datatype. This does not
     * allow rationals to be compared to other number formats, Lang tag is not
     * significant.
     */
    public boolean isEqual(LiteralLabel value1, LiteralLabel value2) {
        if (value1.getDatatype() != value2.getDatatype()) {
            return false;
        }
        try {
            Quantity q1 = (Quantity) parse(value1.getLexicalForm());
            Quantity q2 = (Quantity) parse(value2.getLexicalForm());
            Quantity q3 = q2.to(q1.getUnit());

            return Objects.equals(q1.getUnit(), q3.getUnit())
                    && q1.getValue().floatValue() == q3.getValue().floatValue();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     */
    public int compare(LiteralLabel value1, LiteralLabel value2) {
        if (value1.getDatatype() != value2.getDatatype()) {
            throw new IllegalArgumentException("The two literals must have the same datatype.");
        }
        try {
            Quantity q1 = (Quantity) parse(value1.getLexicalForm());
            Quantity q2 = (Quantity) parse(value2.getLexicalForm());
            Quantity q3 = q2.to(q1.getUnit());

            return (int) Math.signum(q1.getValue().floatValue() - q3.getValue().floatValue());
        } catch (Exception e) {
            throw new IllegalArgumentException("Exception while comparing UCUM literals " + value1.getLexicalForm() + " and " + value2.getLexicalForm());
        }
    }

    /**
     * Returns the java class which is used to represent value instances of this
     * datatype.
     */
    @Override
    public Class<?> getJavaClass() {
        return Quantity.class;
    }

}
