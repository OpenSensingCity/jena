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
package org.apache.jena.datatypes.cdt.quantity;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.measure.Quantity;
import javax.measure.Unit;
import javax.measure.format.ParserException;
import javax.measure.format.UnitFormat;
import javax.measure.spi.ServiceProvider;
import org.apache.jena.datatypes.DatatypeFormatException;
import org.apache.jena.datatypes.TypeMapper;
import org.apache.jena.datatypes.cdt.CDTDatatype;
import org.apache.jena.graph.impl.LiteralLabel;
import systems.uom.ucum.internal.format.TokenException;
import tec.uom.se.quantity.Quantities;

/**
 * 
 * @param <Q> the quantity kind for the datatype
 * @author maxime.lefrancois
 */
public abstract class UnitDatatype<Q extends Quantity<Q>> extends CDTDatatype {

    protected static final UnitFormat unitFormat = ServiceProvider.current().getUnitFormatService().getUnitFormat("CS");
    
    private final Class<Q> clazz;

    public UnitDatatype(String uri, Class<Q> clazz) {
        super(uri);
        this.clazz = clazz;
    }

    /**
     * Returns the java class which is used to represent value instances of this
     * datatype.
     */
    @Override
    public Class<?> getJavaClass() {
        return clazz;
    }

    /**
     * Convert a value of this datatype out to lexical form.
     * 
     * @throws IllegalArgumentException if the value is not an instance of Quantity of the specified dimension
     */
    @Override
    public String unparse(Object value) {
        try {
            final Unit<Q> q = (Unit<Q>) value;
            return unitFormat.format(q);
        } catch (ClassCastException ex) {
            throw new IllegalArgumentException("value must be an instance of Quantity of the specified dimension");
        }
    }

    /**
     * Parse a lexical form of this datatype to a value
     *
     * @throws DatatypeFormatException if the lexical form is not legal
     */
    @Override
    public Unit<Q> parse(String lexicalForm) throws DatatypeFormatException {
        try {
            final Unit<Q> unit = (Unit<Q>) unitFormat.parse(lexicalForm);
            return unit.asType(clazz);
        } catch (TokenException | ParserException e) {
            throw new DatatypeFormatException(lexicalForm, this, "Not a valid unit: " + e.getMessage());
        } catch (ClassCastException e) {
            throw new DatatypeFormatException(lexicalForm, this, "Not a valid " + clazz.getSimpleName() + " unit: " + e.getMessage());
        }
    }

    /**
     * Compares two instances of quantity literals.
     */
    public boolean isEqual(LiteralLabel value1, LiteralLabel value2) {
        try {
            final Unit<Q> u1 = parse(value1.getLexicalForm());
            final Quantity<Q> q1 = Quantities.getQuantity(1, u1);
            final Unit<Q> u2 = parse(value2.getLexicalForm());
            final Quantity<Q> q2 = Quantities.getQuantity(1, u2);
            final Quantity<Q> q3 = q2.to(q1.getUnit());
            return Objects.equals(q1.getUnit(), q3.getUnit())
                    && q1.getValue().doubleValue()== q3.getValue().doubleValue();
        } catch (Exception e) {
            return  false;
        }
    }

    /**
     */
    public int compare(LiteralLabel value1, LiteralLabel value2) {
        if(isEqual(value1, value2)) {
            return 0;
        }
        throw new IllegalArgumentException("Exception while comparing unit literals " + value1.getLexicalForm() + " and " + value2.getLexicalForm());
    }

}
