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

package org.apache.jena.sparql.expr;

import org.apache.jena.atlas.junit.BaseTest ;
import org.apache.jena.sparql.expr.nodevalue.NodeValueOps ;
import static org.junit.Assert.assertEquals;
import org.junit.Test ;

public class TestNodeQuantityOps extends BaseTest
{
    
    @Test public void nq_equals_01() {
        assertEquals(NodeValue.parse("'1.0 m'^^cdt:ucum"), NodeValue.parse("'1.0 m'^^cdt:ucum") ) ;
        assertEquals(NodeValue.parse("'1.0e0 m'^^cdt:ucum"), NodeValue.parse("'1.0 m'^^cdt:ucum") ) ;
        assertEquals(NodeValue.parse("'0.1e1 m'^^cdt:ucum"), NodeValue.parse("'1.0 m'^^cdt:ucum") ) ;
        assertEquals(NodeValue.parse("'1.0e-3 km'^^cdt:ucum"), NodeValue.parse("'1.0 m'^^cdt:ucum") ) ;
        assertEquals(NodeValue.parse("'2000000.001 Pa'^^cdt:ucum"), NodeValue.parse("'2000000001 mPa'^^cdt:ucum") ) ;
    }

    @Test public void nq_add_01() {
        testAdd("'1.0 Cel'^^cdt:ucum", "'0.1 K'^^cdt:ucum", "'-272.04999999999995 Cel'^^cdt:ucum" ) ;
        testAdd("'1.0 m'^^cdt:ucum", "'0.1 m'^^cdt:ucum", "'1.1 m'^^cdt:ucum" ) ;
        testAdd("'1.0123456789123456789 m'^^cdt:ucum", "'0.0 m'^^cdt:ucum", "'1.01234567891234571 m'^^cdt:ucum" ) ; // 17 significant decimal digits
        testAdd("'1.10 m'^^cdt:length", "'2 m'^^cdt:ucum", "'3.1 m'^^cdt:ucum" ) ;
        testAdd("'1 mPa'^^cdt:pressure", "'2 MPa'^^cdt:ucum", "'2000000.001 Pa'^^cdt:ucum" ) ;
    }

    @Test public void nq_add_02() {
        assertTrue(testAddError("'1.0 m'^^cdt:ucum", "'0.1 s'^^cdt:ucum")) ;
        assertTrue(testAddError("'1.0 m'^^cdt:ucum", "'0.1 s'^^cdt:length" )) ;
        assertTrue(testAddError("'1.0 /Cel'^^cdt:ucum", "'0.1 /K'^^cdt:ucum" )) ;
    }
    
    @Test public void nq_multi_01() {
        testMult("'1.0 m'^^cdt:length", "'2 m'^^cdt:length", "'2.0 m2'^^cdt:area") ;
        testMult("'1.0 m/s'^^cdt:speed", "'2 s'^^cdt:time", "'2.0 m'^^cdt:length") ;
        testMult("'1.0 m/s'^^cdt:ucum", "'2 s'^^cdt:ucum", "'2.0 m'^^cdt:ucum") ;
    }
    
    @Test public void nq_multi_02() {
        testMult("'1.0 m/s'^^cdt:ucum", "'2'^^xsd:int", "'2.0 m/s'^^cdt:ucum") ;
        testMult("'1.0 m/s'^^cdt:ucum", "'2'^^xsd:double", "'2.0 m/s'^^cdt:speed") ;
        testMult("'1.0 m/s'^^cdt:ucum", "'2'^^xsd:decimal", "'2.0 m/s'^^cdt:speed") ;
        testMult("'1.0 m/s'^^cdt:speed", "'2'^^xsd:float", "'2.0 m/s'^^cdt:ucum") ;
    }
    
    @Test public void nq_multi_03() {
        testMult("'2'^^xsd:int", "'1.0 m/s'^^cdt:ucum", "'2.0 m/s'^^cdt:ucum") ;
        testMult("'2'^^xsd:double", "'1.0 m/s'^^cdt:ucum", "'2.0 m/s'^^cdt:speed") ;
        testMult("'2'^^xsd:decimal", "'1.0 m/s'^^cdt:ucum", "'2.0 m/s'^^cdt:speed") ;
        testMult("'2'^^xsd:float", "'1.0 m/s'^^cdt:speed", "'2.0 m/s'^^cdt:ucum") ;
    }
    
    @Test public void nq_div_01() {
        testDiv("'1.0 m'^^cdt:ucum", "'2 s'^^cdt:time", "'0.5 m/s'^^cdt:speed") ;
        testDiv("'1.0 m'^^cdt:length", "'2 s'^^cdt:ucum", "'0.5 m/s'^^cdt:ucum") ;
        testDiv("'1.0 m'^^cdt:length", "'2 s'^^cdt:ucum", "'0.5 m/s'^^cdt:ucum") ;
        testDiv("'1.0 m'^^cdt:ucum", "'2 s'^^cdt:ucum", "'0.5 m/s'^^cdt:speed") ;
    }

    @Test public void nq_div_02() {
        testDiv("'1.0 m'^^cdt:length", "'2'^^xsd:int", "'0.5 m'^^cdt:length") ;
        testDiv("'1.0 m'^^cdt:length", "'2'^^xsd:double", "'0.5 m'^^cdt:ucum") ;
        testDiv("'1.0 m'^^cdt:length", "'2'^^xsd:int", "'0.5 m'^^cdt:length") ;
        testDiv("'1.0 m'^^cdt:length", "'2'^^xsd:decimal", "'0.5 m'^^cdt:length") ;
    }

    @Test public void nq_div_03() {
        testDiv("'2'^^xsd:int", "'0.5 m'^^cdt:length", "'4 /m'^^cdt:ucum") ;
        testDiv("'2'^^xsd:double", "'0.5 m'^^cdt:length", "'4.000000000000000000000001 /m'^^cdt:ucum") ;
        testDiv("'2'^^xsd:int", "'0.5 m'^^cdt:length", "'4.000 /m'^^cdt:ucum") ;
        testDiv("'2'^^xsd:decimal", "'0.50 m'^^cdt:length", "'4.00000000000000000 /m'^^cdt:ucum") ;
    }

    // == Workers
    
    static void testAdd(String s1, String s2, String s3)
    {
        NodeValue nv3 = NodeValue.parse(s3) ;
        NodeValue nv = testAdd(s1, s2) ;
        assertEquals(nv3, nv) ;
    }
        
    static NodeValue testAdd(String s1, String s2)
    {
        NodeValue nv1 = NodeValue.parse(s1) ;
        NodeValue nv2 = NodeValue.parse(s2) ;
        return NodeValueOps.additionNV(nv1, nv2) ;
    }
    
    static boolean testAddError(String s1, String s2)
    {
        try {
            testAdd(s1, s2) ;
            return false;
        } catch (ExprEvalTypeException ex) {
            return true;
        }
    }

    
    static void testSub(String s1, String s2, String s3)
    {
        NodeValue nv3 = NodeValue.parse(s3) ;
        NodeValue nv = testSub(s1, s2) ;
        assertEquals(nv3, nv) ;
    }
    
    static NodeValue testSub(String s1, String s2)
    {
        NodeValue nv1 = NodeValue.parse(s1) ;
        NodeValue nv2 = NodeValue.parse(s2) ;
        return NodeValueOps.subtractionNV(nv1, nv2) ;
    }

    static void testMult(String s1, String s2, String s3)
    {
        NodeValue nv3 = NodeValue.parse(s3) ;
        NodeValue nv = testMult(s1, s2) ;
        assertEquals(nv3, nv) ;
    }
    
    static NodeValue testMult(String s1, String s2)
    {
        NodeValue nv1 = NodeValue.parse(s1) ;
        NodeValue nv2 = NodeValue.parse(s2) ;
        return NodeValueOps.multiplicationNV(nv1, nv2) ;
    }

    static void testDiv(String s1, String s2, String s3)
    {
        NodeValue nv3 = NodeValue.parse(s3) ;
        NodeValue nv = testDiv(s1, s2) ;
        assertEquals(nv3, nv) ;
    }
    
    static NodeValue testDiv(String s1, String s2)
    {
        NodeValue nv1 = NodeValue.parse(s1) ;
        NodeValue nv2 = NodeValue.parse(s2) ;
        return NodeValueOps.divisionNV(nv1, nv2) ;
    }

}

