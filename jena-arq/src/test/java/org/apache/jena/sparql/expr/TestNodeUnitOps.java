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

public class TestNodeUnitOps extends BaseTest
{
    
    @Test public void nq_equals_01() {
        assertEquals(NodeValue.parse("'km/h'^^cdt:ucumunit"), NodeValue.parse("'km.h-1'^^cdt:ucumunit") ) ;
        assertEquals(NodeValue.parse("'km/h'^^cdt:ucumunit"), NodeValue.parse("'(1000.m)/(60.min)'^^cdt:ucumunit") ) ;
        assertNotEquals(NodeValue.parse("'km/h'^^cdt:ucumunit"), NodeValue.parse("'m/s'^^cdt:ucumunit") ) ;
        assertNotEquals(NodeValue.parse("'km/h'^^cdt:ucumunit"), NodeValue.parse("'A'^^cdt:ucumunit") ) ;
    }

}

