/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.apache.jena.sparql.expr.nodevalue;

import java.nio.charset.Charset;
import java.util.Iterator;
import org.apache.commons.io.IOUtils;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;

/**
 *
 * @author maxime.lefrancois
 */
public class Test {

    private static final String queryString = "PREFIX cdt: <http://w3id.org/lindt/custom_datatypes#>\n"
                + "SELECT ?y WHERE {\n"
                + "  ?x ?p ?y .\n"
                + "  FILTER( ?y = \"5.946937580195617 [LB_AV]/[FT_US]\"^^cdt:ucum ) \n"
                + "}";
    private static final String dataset = "@prefix cdt: <http://w3id.org/lindt/custom_datatypes#>.\n"
            + "<m1> <p> \"8.85 kg/m\"^^cdt:ucum . \n"
            + "<m2> <p> \"1mazdf\"^^cdt:ucum . \n";
    public static void main(String[] args) {
        System.out.println("sld");
            Model m = ModelFactory.createDefaultModel();
            RDFDataMgr.read(m, IOUtils.toInputStream(dataset, Charset.forName("utf-8")), Lang.TURTLE);
            Query query = QueryFactory.create(queryString);
            try (QueryExecution qexec = QueryExecutionFactory.create(query, m)) {
                ResultSet results = qexec.execSelect();
                for (; results.hasNext();) {
                    QuerySolution sol = results.nextSolution();
                    System.out.println("sol");
                    Iterator<String> varnames = sol.varNames();
                    while(varnames.hasNext()) {
                        String var = varnames.next();
                        System.out.println("name "+var+" -- "+sol.get(var));
                    }
                }
            }
    }



   
}
