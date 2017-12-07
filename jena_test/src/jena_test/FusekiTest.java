package jena_test;

import java.util.UUID;

import org.apache.jena.query.*;
import org.apache.jena.rdf.model.*;
import org.apache.jena.update.*;

import input.UniversityModel;
 
/**
 * Example connection to Fuseki. For this to work, you need to start a local
 * Fuseki server like this: ./fuseki-server --update --mem /ds
 */
public class FusekiTest {
	
   private static String createTemplate(String subject, String predicate, String object) {
	   /** A template for creating a nice SPARUL query */
	    return
	            "PREFIX dc: <http://purl.org/dc/elements/1.1/>"
	            + "INSERT DATA"
	            + "{ <http://university/%s>    dc:title    \"A new book\" ;"
	            + "                         dc:creator  \"A.N.Other\" ." + "}   ";
   }
 
    public static void main(String[] args) {
    	
    	Model model = UniversityModel.getModel();
    	
    	String s = null;
    	String p = null;
    	String o = null;
    	
        //Add a new book to the collection
        String id = UUID.randomUUID().toString();
        System.out.println(String.format("Adding %s", id));
        
        UpdateProcessor upp = UpdateExecutionFactory.createRemote(
                UpdateFactory.create(String.format(createTemplate(s, p, o), id)), 
                "http://localhost:3030/ds/update");
        upp.execute();
        
        //Query the collection, dump output
        QueryExecution qe = QueryExecutionFactory.sparqlService(
                "http://localhost:3030/ds/query", "SELECT * WHERE {?subject ?predicate ?object}");
        ResultSet results = qe.execSelect();
        ResultSetFormatter.out(System.out, results);
        qe.close();
    }
 
}