/**
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

package org.apache.jena.datatypes;

import static org.junit.Assert.assertEquals ;
import static org.junit.Assert.assertFalse ;
import static org.junit.Assert.assertNotNull ;
import static org.junit.Assert.assertTrue ;

import javax.measure.format.UnitFormat;
import javax.measure.spi.ServiceProvider;
import org.apache.jena.datatypes.cdt.CDTDatatype;
import org.apache.jena.datatypes.cdt.quantity.CDTUCUMUnit;
import org.apache.jena.datatypes.cdt.quantity.QuantityDatatype;
import org.apache.jena.datatypes.cdt.quantity.UnitDatatype;

import org.apache.jena.graph.NodeFactory ;
import org.apache.jena.graph.impl.LiteralLabel;
import org.apache.jena.graph.impl.LiteralLabelFactory;
import org.apache.jena.rdf.model.Resource ;
import org.junit.Test ;

public class TestUnitDatatypes {

    public UnitDatatype cdtUCUMUnit = CDTUCUMUnit.theType ;
    public UnitFormat unitFormat = ServiceProvider.current().getUnitFormatService().getUnitFormat("CS");

    @Test public void speed_01() {
        valid(cdtUCUMUnit, "km/h") ;
        valid(cdtUCUMUnit, "km.h-1") ;
        valid(cdtUCUMUnit, "(1000.m)/(60.min)") ;
        invalid(cdtUCUMUnit, "mfazaffa") ;
    }

    @Test public void speed_02() {
        equal("km/h", "km.h-1");
        equal("km/h", "(1000.m)/(60.min)");
        notequal("km/h", "m/s");
        notequal("km/h", "A");
    }

    private void equal(String u1, String u2) {
        LiteralLabel l1 = LiteralLabelFactory.create(u1, cdtUCUMUnit);
        LiteralLabel l2 = LiteralLabelFactory.create(u2, cdtUCUMUnit);
        assertTrue("Expected equal: "+u1 + " " + u2, cdtUCUMUnit.isEqual(l1, l2)) ;
    }

    private void notequal(String u1, String u2) {
        LiteralLabel l1 = LiteralLabelFactory.create(u1, cdtUCUMUnit);
        LiteralLabel l2 = LiteralLabelFactory.create(u2, cdtUCUMUnit);
        assertFalse("Expected equal: "+u1 + " " + u2, cdtUCUMUnit.isEqual(l1, l2)) ;
    }

    private void valid(UnitDatatype udatatype, String string) {
        assertTrue("Expected valid: "+string, udatatype.isValid(string)) ;
    }
    
    private void invalid(UnitDatatype udatatype, String string) {
        assertFalse("Expected invalid: "+string, udatatype.isValid(string)) ;
    }

    private void checkRegistration1(String localName, Resource r) {
        QuantityDatatype _cdt            = (QuantityDatatype) NodeFactory.getType(CDTDatatype.CDT + localName) ;
        assertNotNull(_cdt) ;
        assertEquals(r.getURI(), _cdt.getURI()) ;
    }

}

