package jena_test;

import java.util.LinkedList;
import java.util.UUID;

import org.apache.jena.query.*;
import org.apache.jena.rdf.model.*;
import org.apache.jena.update.*;

import input.UniversityModel;
import jena_test.Reader.RDFhelper;
 
/**
 * Example connection to Fuseki. For this to work, you need to start a local
 * Fuseki server like this: ./fuseki-server --update --mem /ds
 */
public class FusekiTest {
	
   private static String createTemplate(String subject, String predicate, String object) {
	    return
	            "PREFIX dc: <http://purl.org/dc/elements/1.1/>"
	            + "INSERT DATA"
	            + "{<"+subject+"> dc:"+predicate+"\""+object+"\"}";
   }
 
   // only for testing
    public static void main(String[] args) {
    	
    	Reader r = new Reader();
    	// read from file
    	r.readFile("C:\\Users\\Alexander\\Desktop\\Neues Textdokument.txt");
    	
    	LinkedList<RDFhelper> rdfList = r.getTriples();
    	// start update into DB 
    	rdfList.forEach(s ->{ 
    		
    		UpdateProcessor upp = UpdateExecutionFactory.createRemote(
                    UpdateFactory.create(String.format(createTemplate(s.getSubject(),s.getPredicate(),s.getObject()))), 
                    "http://localhost:3030/ds/update");
            upp.execute();
    	});
        
        //Query the collection, dump output
        QueryExecution qe = QueryExecutionFactory.sparqlService(
                "http://localhost:3030/ds/query", "SELECT * WHERE {?subject ?predicate ?object}");
        ResultSet results = qe.execSelect();
        ResultSetFormatter.out(System.out, results);
        qe.close();
    }
    
    public void insertTxT(String filePath, String tripleSetName) throws Exception {
    	Reader r = new Reader();
    	// read from file
    	r.readFile(filePath);
    	
    	LinkedList<RDFhelper> rdfList = r.getTriples();
    	// start update into DB 
    	rdfList.forEach(s ->{ 
    		
    		UpdateProcessor upp = UpdateExecutionFactory.createRemote(
                    UpdateFactory.create(String.format(createTemplate(s.getSubject(),s.getPredicate(),s.getObject()))), 
                    "http://localhost:3030/"+tripleSetName+"/update");
            upp.execute();
    	});
        
        //Query the collection, dump output
        QueryExecution qe = QueryExecutionFactory.sparqlService(
                "http://localhost:3030/ds/query", "SELECT * WHERE {?subject ?predicate ?object}");
        ResultSet results = qe.execSelect();
        ResultSetFormatter.out(System.out, results);
        qe.close();
    }
 
}