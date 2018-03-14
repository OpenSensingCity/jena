/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.jena.datatypes.cdt;

import org.apache.jena.datatypes.cdt.quantity.CDTAcceleration;
import org.apache.jena.datatypes.cdt.quantity.CDTAmountOfSubstance;
import org.apache.jena.datatypes.cdt.quantity.CDTAngle;
import org.apache.jena.datatypes.cdt.quantity.CDTArea;
import org.apache.jena.datatypes.cdt.quantity.CDTCatalyticActivity;
import org.apache.jena.datatypes.cdt.quantity.CDTDimensionless;
import org.apache.jena.datatypes.cdt.quantity.CDTElectricCapacitance;
import org.apache.jena.datatypes.cdt.quantity.CDTElectricCharge;
import org.apache.jena.datatypes.cdt.quantity.CDTElectricConductance;
import org.apache.jena.datatypes.cdt.quantity.CDTElectricCurrent;
import org.apache.jena.datatypes.cdt.quantity.CDTElectricInductance;
import org.apache.jena.datatypes.cdt.quantity.CDTElectricPotential;
import org.apache.jena.datatypes.cdt.quantity.CDTElectricResistance;
import org.apache.jena.datatypes.cdt.quantity.CDTEnergy;
import org.apache.jena.datatypes.cdt.quantity.CDTForce;
import org.apache.jena.datatypes.cdt.quantity.CDTFrequency;
import org.apache.jena.datatypes.cdt.quantity.CDTIlluminance;
import org.apache.jena.datatypes.cdt.quantity.CDTLength;
import org.apache.jena.datatypes.cdt.quantity.CDTLuminousFlux;
import org.apache.jena.datatypes.cdt.quantity.CDTLuminousIntensity;
import org.apache.jena.datatypes.cdt.quantity.CDTMagneticFlux;
import org.apache.jena.datatypes.cdt.quantity.CDTMagneticFluxDensity;
import org.apache.jena.datatypes.cdt.quantity.CDTMass;
import org.apache.jena.datatypes.cdt.quantity.CDTPower;
import org.apache.jena.datatypes.cdt.quantity.CDTPressure;
import org.apache.jena.datatypes.cdt.quantity.CDTRadiationDoseAbsorbed;
import org.apache.jena.datatypes.cdt.quantity.CDTRadiationDoseEffective;
import org.apache.jena.datatypes.cdt.quantity.CDTRadioactivity;
import org.apache.jena.datatypes.cdt.quantity.CDTSolidAngle;
import org.apache.jena.datatypes.cdt.quantity.CDTSpeed;
import org.apache.jena.datatypes.cdt.quantity.CDTTemperature;
import org.apache.jena.datatypes.cdt.quantity.CDTTime;
import org.apache.jena.datatypes.cdt.quantity.CDTUCUM;
import org.apache.jena.datatypes.cdt.quantity.CDTVolume ;
import org.apache.jena.rdf.model.Resource ;
import org.apache.jena.rdf.model.ResourceFactory ;

public class CDT {  
    /**
     * The namespace of the vocabulary as a string
     */
    public static final String NS = CDTDatatype.CDT;
    
    /**
     * The RDF-friendly version of the CDT namespace
     */
    public static String getURI() { return NS; }
    
    /** Resource URI for cdt:ucum */
    public static Resource ucum;
    
    /** Resource URI for cdt:acceleration */
    public static Resource acceleration;
    
    /** Resource URI for cdt:amountOfSubstance */
    public static Resource amountOfSubstance;
    
    /** Resource URI for cdt:angle */
    public static Resource angle;
    
    /** Resource URI for cdt:area */
    public static Resource area;
    
    /** Resource URI for cdt:catalyticActivity */
    public static Resource catalyticActivity;
    
    /** Resource URI for cdt:dimensionless */
    public static Resource dimensionless;
    
    /** Resource URI for cdt:electricCapacitance */
    public static Resource electricCapacitance;
    
    /** Resource URI for cdt:electricCharge */
    public static Resource electricCharge;
    
    /** Resource URI for cdt:electricConductance */
    public static Resource electricConductance;
    
    /** Resource URI for cdt:electricCurrent */
    public static Resource electricCurrent;
    
    /** Resource URI for cdt:electricInductance */
    public static Resource electricInductance;
    
    /** Resource URI for cdt:electricPotential */
    public static Resource electricPotential;
    
    /** Resource URI for cdt:electricResistance */
    public static Resource electricResistance;
    
    /** Resource URI for cdt:energy */
    public static Resource energy;
    
    /** Resource URI for cdt:force */
    public static Resource force;
    
    /** Resource URI for cdt:frequency */
    public static Resource frequency;
    
    /** Resource URI for cdt:illuminance */
    public static Resource illuminance;
    
    /** Resource URI for cdt:length */
    public static Resource length;
    
    /** Resource URI for cdt:luminousFlux */
    public static Resource luminousFlux;
    
    /** Resource URI for cdt:luminousIntensity */
    public static Resource luminousIntensity;
    
    /** Resource URI for cdt:magneticFlux */
    public static Resource magneticFlux;
    
    /** Resource URI for cdt:magneticFluxDensity */
    public static Resource magneticFluxDensity;
    
    /** Resource URI for cdt:mass */
    public static Resource mass;
    
    /** Resource URI for cdt:power */
    public static Resource power;
    
    /** Resource URI for cdt:pressure */
    public static Resource pressure;
    
    /** Resource URI for cdt:radiationDoseAbsorbed */
    public static Resource radiationDoseAbsorbed;
    
    /** Resource URI for cdt:radiationDoseEffective */
    public static Resource radiationDoseEffective;
    
    /** Resource URI for cdt:radioactivity */
    public static Resource radioactivity;
    
    /** Resource URI for cdt:solidAngle */
    public static Resource solidAngle;
    
    /** Resource URI for cdt:speed */
    public static Resource speed;
    
    /** Resource URI for cdt:temperature */
    public static Resource temperature;
    
    /** Resource URI for cdt:time */
    public static Resource time;
    
    /** Resource URI for cdt:volume */
    public static Resource volume;
    
    // Initializer
    static {
        ucum = ResourceFactory.createResource(CDTUCUM.theTypeURI);
        acceleration = ResourceFactory.createResource(CDTAcceleration.theTypeURI);
        amountOfSubstance = ResourceFactory.createResource(CDTAmountOfSubstance.theTypeURI);
        angle = ResourceFactory.createResource(CDTAngle.theTypeURI);
        area = ResourceFactory.createResource(CDTArea.theTypeURI);
        catalyticActivity = ResourceFactory.createResource(CDTCatalyticActivity.theTypeURI);
        dimensionless = ResourceFactory.createResource(CDTDimensionless.theTypeURI);
        electricCapacitance = ResourceFactory.createResource(CDTElectricCapacitance.theTypeURI);
        electricCharge = ResourceFactory.createResource(CDTElectricCharge.theTypeURI);
        electricConductance = ResourceFactory.createResource(CDTElectricConductance.theTypeURI);
        electricCurrent = ResourceFactory.createResource(CDTElectricCurrent.theTypeURI);
        electricInductance = ResourceFactory.createResource(CDTElectricInductance.theTypeURI);
        electricPotential = ResourceFactory.createResource(CDTElectricPotential.theTypeURI);
        electricResistance = ResourceFactory.createResource(CDTElectricResistance.theTypeURI);
        energy = ResourceFactory.createResource(CDTEnergy.theTypeURI);
        force = ResourceFactory.createResource(CDTForce.theTypeURI);
        frequency = ResourceFactory.createResource(CDTFrequency.theTypeURI);
        illuminance = ResourceFactory.createResource(CDTIlluminance.theTypeURI);
        length = ResourceFactory.createResource(CDTLength.theTypeURI);
        luminousFlux = ResourceFactory.createResource(CDTLuminousFlux.theTypeURI);
        luminousIntensity = ResourceFactory.createResource(CDTLuminousIntensity.theTypeURI);
        magneticFlux = ResourceFactory.createResource(CDTMagneticFlux.theTypeURI);
        magneticFluxDensity = ResourceFactory.createResource(CDTMagneticFluxDensity.theTypeURI);
        mass = ResourceFactory.createResource(CDTMass.theTypeURI);
        power = ResourceFactory.createResource(CDTPower.theTypeURI);
        pressure = ResourceFactory.createResource(CDTPressure.theTypeURI);
        radiationDoseAbsorbed = ResourceFactory.createResource(CDTRadiationDoseAbsorbed.theTypeURI);
        radiationDoseEffective = ResourceFactory.createResource(CDTRadiationDoseEffective.theTypeURI);
        radioactivity = ResourceFactory.createResource(CDTRadioactivity.theTypeURI);
        solidAngle = ResourceFactory.createResource(CDTSolidAngle.theTypeURI);
        speed = ResourceFactory.createResource(CDTSpeed.theTypeURI);
        temperature = ResourceFactory.createResource(CDTTemperature.theTypeURI);
        time = ResourceFactory.createResource(CDTTime.theTypeURI);
        volume = ResourceFactory.createResource(CDTVolume.theTypeURI);
    }
}
