package input;

import org.apache.jena.rdf.model.*;

/* Model of a university where students can be Participants in different courses
 * 
 */
public class UniversityModel {

	// URI declarations
	static final String universityUri = "http://university/";
	static final String relationshipUri = "http://purl.org/vocab/relationship/";
	
	// Create an empty Model
	static Model model = ModelFactory.createDefaultModel();
	
	private static void initModel() {
		
		// Create a Resource for each student identified by their URI
		Resource adam = model.createResource(universityUri + "adam");
		Resource beth = model.createResource(universityUri + "beth");
		Resource chris = model.createResource(universityUri + "chris");
		Resource deliah = model.createResource(universityUri + "deliah");
		Resource elsa = model.createResource(universityUri + "elsa");
		Resource felix = model.createResource(universityUri + "felix");
		
		// Create a Resource for each course identified by their URI
		Literal ewin = model.createLiteral(universityUri + "ewin");
		Literal se1_ue = model.createLiteral(universityUri + "se1_ue");
		Literal se1_vl = model.createLiteral(universityUri + "se1_vl");

		// Create properties for the different types of relationship to represent
		Property participantIn = model.createProperty(relationshipUri, "participantIn");
		Property colleagueOf = model.createProperty(relationshipUri, "colleagueOf");
		Property collaboratesWith = model.createProperty(relationshipUri, "collaboratesWith");

		// Add properties to the model
		adam.addProperty(participantIn, ewin);
		adam.addProperty(participantIn, se1_ue);
		adam.addProperty(participantIn, se1_vl);
		adam.addProperty(collaboratesWith, deliah);
		adam.addProperty(colleagueOf, elsa);
		
		// System.out.println(model);
	}
	
	public static Model getModel() {
		initModel();
		return model;
	}
}
