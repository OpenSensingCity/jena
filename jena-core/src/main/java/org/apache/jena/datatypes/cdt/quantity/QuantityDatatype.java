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
 * NOTE: stored using Double -> 17 significant decimal digits.
 * 
 * @param <Q> the quantity kind for the datatype
 * @author maxime.lefrancois
 */
public abstract class QuantityDatatype<Q extends Quantity<Q>> extends CDTDatatype {

    protected static final UnitFormat unitFormat = ServiceProvider.current().getUnitFormatService().getUnitFormat("CI");
    
    private final Class<Q> clazz;

    public QuantityDatatype(String uri, Class<Q> clazz) {
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
            final Quantity<Q> q = (Quantity<Q>) value;
            return q.getValue() + " " + unitFormat.format(q.getUnit());
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
    public Quantity<Q> parse(String lexicalForm) throws DatatypeFormatException {
        int index = lexicalForm.indexOf(" ");
        if (index == -1) {
            throw new DatatypeFormatException(lexicalForm, this, "Lexical form must contain a space");
        }
        try {
            final Unit<Q> unit = (Unit<Q>) unitFormat.parse(lexicalForm.substring(index + 1));
            final double value = new Double(lexicalForm.substring(0, index));
            return Quantities.getQuantity(value, unit).asType(clazz);
        } catch (TokenException | ParserException e) {
            throw new DatatypeFormatException(lexicalForm, this, "Not a valid unit: " + e.getMessage());
        } catch (NumberFormatException e) {
            throw new DatatypeFormatException(lexicalForm, this, "Not a valid number: " + e.getMessage());
        } catch (ClassCastException e) {
            throw new DatatypeFormatException(lexicalForm, this, "Not a valid " + clazz.getSimpleName() + " unit: " + e.getMessage());
        }
    }

    /**
     * Compares two instances of quantity literals.
     */
    public boolean isEqual(LiteralLabel value1, LiteralLabel value2) {
        try {
            final Quantity<Q> q1 = parse(value1.getLexicalForm());
            final Quantity<Q> q2 = parse(value2.getLexicalForm());
            final Quantity<Q> q3 = q2.to(q1.getUnit());
            return Objects.equals(q1.getUnit(), q3.getUnit())
                    && q1.getValue().doubleValue()== q3.getValue().doubleValue();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     */
    public int compare(LiteralLabel value1, LiteralLabel value2) {
        try {
            final Quantity<Q> q1 = parse(value1.getLexicalForm());
            final Quantity<Q> q2 = parse(value2.getLexicalForm());
            final Quantity<Q> q3 = q2.to(q1.getUnit());
            return (int) Math.signum(q1.getValue().floatValue() - q3.getValue().floatValue());
        } catch (Exception e) {
            throw new IllegalArgumentException("Exception while comparing quantity literals " + value1.getLexicalForm() + " and " + value2.getLexicalForm());
        }
    }

    private static final Set<QuantityDatatype> quantityDT = new HashSet<>();
    
    public static void loadCDTTypes(TypeMapper tm) {
        tm.registerDatatype(CDTUCUM.theType);
        
        tm.registerDatatype(CDTAcceleration.theType);
        tm.registerDatatype(CDTAmountOfSubstance.theType);
        tm.registerDatatype(CDTAngle.theType);
        tm.registerDatatype(CDTArea.theType);
        tm.registerDatatype(CDTCatalyticActivity.theType);
        tm.registerDatatype(CDTDimensionless.theType);
        tm.registerDatatype(CDTElectricCapacitance.theType);
        tm.registerDatatype(CDTElectricCharge.theType);
        tm.registerDatatype(CDTElectricConductance.theType);
        tm.registerDatatype(CDTElectricCurrent.theType);
        tm.registerDatatype(CDTElectricInductance.theType);
        tm.registerDatatype(CDTElectricPotential.theType);
        tm.registerDatatype(CDTElectricResistance.theType);
        tm.registerDatatype(CDTEnergy.theType);
        tm.registerDatatype(CDTForce.theType);
        tm.registerDatatype(CDTFrequency.theType);
        tm.registerDatatype(CDTIlluminance.theType);
        tm.registerDatatype(CDTLength.theType);
        tm.registerDatatype(CDTLuminousFlux.theType);
        tm.registerDatatype(CDTLuminousIntensity.theType);
        tm.registerDatatype(CDTMagneticFlux.theType);
        tm.registerDatatype(CDTMagneticFluxDensity.theType);
        tm.registerDatatype(CDTMass.theType);
        tm.registerDatatype(CDTPower.theType);
        tm.registerDatatype(CDTPressure.theType);
        tm.registerDatatype(CDTRadiationDoseAbsorbed.theType);
        tm.registerDatatype(CDTRadiationDoseEffective.theType);
        tm.registerDatatype(CDTRadioactivity.theType);
        tm.registerDatatype(CDTSolidAngle.theType);
        tm.registerDatatype(CDTSpeed.theType);
        tm.registerDatatype(CDTTemperature.theType);
        tm.registerDatatype(CDTTime.theType);
        tm.registerDatatype(CDTVolume.theType);
        
        quantityDT.add(CDTAcceleration.theType);
        quantityDT.add(CDTAmountOfSubstance.theType);
        quantityDT.add(CDTAngle.theType);
        quantityDT.add(CDTArea.theType);
        quantityDT.add(CDTCatalyticActivity.theType);
        quantityDT.add(CDTDimensionless.theType);
        quantityDT.add(CDTElectricCapacitance.theType);
        quantityDT.add(CDTElectricCharge.theType);
        quantityDT.add(CDTElectricConductance.theType);
        quantityDT.add(CDTElectricCurrent.theType);
        quantityDT.add(CDTElectricInductance.theType);
        quantityDT.add(CDTElectricPotential.theType);
        quantityDT.add(CDTElectricResistance.theType);
        quantityDT.add(CDTEnergy.theType);
        quantityDT.add(CDTForce.theType);
        quantityDT.add(CDTFrequency.theType);
        quantityDT.add(CDTIlluminance.theType);
        quantityDT.add(CDTLength.theType);
        quantityDT.add(CDTLuminousFlux.theType);
        quantityDT.add(CDTLuminousIntensity.theType);
        quantityDT.add(CDTMagneticFlux.theType);
        quantityDT.add(CDTMagneticFluxDensity.theType);
        quantityDT.add(CDTMass.theType);
        quantityDT.add(CDTPower.theType);
        quantityDT.add(CDTPressure.theType);
        quantityDT.add(CDTRadiationDoseAbsorbed.theType);
        quantityDT.add(CDTRadiationDoseEffective.theType);
        quantityDT.add(CDTRadioactivity.theType);
        quantityDT.add(CDTSolidAngle.theType);
        quantityDT.add(CDTSpeed.theType);
        quantityDT.add(CDTTemperature.theType);
        quantityDT.add(CDTTime.theType);
        quantityDT.add(CDTVolume.theType);
    }
        
    public static QuantityDatatype getMostSuitableQuantityDatatype(Quantity quantity) {
        for(QuantityDatatype qdt : quantityDT) {
            try {
                quantity.asType(qdt.getJavaClass());
                return qdt;
            } catch(ClassCastException ex) {
                continue;
            }
        }
        return CDTUCUM.theType;
    }


}
