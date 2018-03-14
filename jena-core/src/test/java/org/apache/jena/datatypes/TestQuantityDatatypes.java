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

import java.math.BigDecimal ;
import javax.measure.format.UnitFormat;
import javax.measure.spi.ServiceProvider;
import org.apache.jena.datatypes.cdt.CDT;
import org.apache.jena.datatypes.cdt.CDTDatatype;
import org.apache.jena.datatypes.cdt.quantity.CDTLength;
import org.apache.jena.datatypes.cdt.quantity.CDTSpeed;
import org.apache.jena.datatypes.cdt.quantity.CDTTime;
import org.apache.jena.datatypes.cdt.quantity.CDTUCUM;
import org.apache.jena.datatypes.cdt.quantity.QuantityDatatype;

import org.apache.jena.datatypes.xsd.XSDDatatype ;
import org.apache.jena.graph.Node ;
import org.apache.jena.graph.NodeFactory ;
import org.apache.jena.rdf.model.Resource ;
import org.junit.Test ;
import tec.uom.se.quantity.Quantities;

public class TestQuantityDatatypes {

    public QuantityDatatype cdtUCUM = CDTUCUM.theType ;
    public QuantityDatatype cdtLength = CDTLength.theType ;
    public QuantityDatatype cdtSpeed = CDTSpeed.theType ;
    public QuantityDatatype cdtTime = CDTTime.theType ;
    public UnitFormat unitFormat = ServiceProvider.current().getUnitFormatService().getUnitFormat("CI");

    @Test public void registration_01()   { checkRegistration1("length", CDT.length); }
    @Test public void registration_02()   { checkRegistration1("speed", CDT.speed); }
    @Test public void registration_03()   { checkRegistration1("time", CDT.time); }
  

    @Test public void length_01() {
        valid(cdtLength, "1 m") ;
        valid(cdtLength, "0.1 mm") ;
        invalid(cdtLength, "0.1mm") ;
        invalid(cdtLength, "z s") ;
        invalid(cdtLength, "1234 s") ;
        invalid(cdtLength, "1234 kA") ;
    }

    @Test public void time_01() {
        valid(cdtTime, "1 s") ;
        valid(cdtTime, "0.1 min") ;
        valid(cdtTime, "105 s.kg/g") ;
        invalid(cdtTime, "1234 m") ;
        invalid(cdtTime, "1234 kA") ;
    }

    @Test public void getTypeByValue() {
        TypeMapper tm = TypeMapper.getInstance();
        assertEquals(tm.getTypeByValue(Quantities.getQuantity(1, unitFormat.parse("km10"))), cdtUCUM) ;
        assertEquals(tm.getTypeByValue(Quantities.getQuantity(1, unitFormat.parse("km"))), cdtLength) ;
        assertEquals(tm.getTypeByValue(Quantities.getQuantity(1, unitFormat.parse("km/ms"))), cdtSpeed) ;
        assertEquals(tm.getTypeByValue(Quantities.getQuantity(1, unitFormat.parse("s.s/s"))), cdtTime) ;
    }

    @Test public void valueToLex_bigdecimal_01() {
        testValueToLex(new BigDecimal("0.004"), XSDDatatype.XSDdecimal) ;
    }
    

    private void testValueToLex(Object value, XSDDatatype datatype) {
        Node node = NodeFactory.createLiteralByValue(value, datatype) ;
        assertTrue("Not valid lexical form "+value+" -> "+node, datatype.isValid(node.getLiteralLexicalForm())) ;
    }

    private void valid(QuantityDatatype qdatatype, String string) {
        assertTrue("Expected valid: "+string, qdatatype.isValid(string)) ;
    }
    
    private void invalid(QuantityDatatype qdatatype, String string) {
        assertFalse("Expected invalid: "+string, qdatatype.isValid(string)) ;
    }

    private void checkRegistration1(String localName, Resource r) {
        QuantityDatatype _cdt            = (QuantityDatatype) NodeFactory.getType(CDTDatatype.CDT + localName) ;
        assertNotNull(_cdt) ;
        assertEquals(r.getURI(), _cdt.getURI()) ;
    }

}

