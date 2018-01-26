package jena_test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
 
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
 
public class ReadJsonExample {
 
    public static void main(String a[]){
         
        File jsonInputFile = new File("\\Users\\Alexander\\Desktop\\json.json");
        InputStream is;
        try {
            is = new FileInputStream(jsonInputFile);
            // Create JsonReader from Json.
            JsonReader reader = Json.createReader(is);
            // Get the JsonObject structure from JsonReader.
            JsonObject empObj = reader.readObject();
            reader.close();
            // read string data
            System.out.println("Name: " + empObj.getString("Name"));
            // read integer data
            System.out.println("Beziehung: " + empObj.getString("Beziehung"));
            System.out.println("Objekt: " + empObj.getString("Objekt"));
            // read inner json element
            //JsonObject addrObj = empObj.getJsonObject("address");
            //System.out.println("City: " + addrObj.getString("city"));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
