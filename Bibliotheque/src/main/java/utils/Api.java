package utils;

public class Api {
	
	
	/*
	 * JSON Success messages
	 */
		// Auteur
		public static final String AUTEUR_SUPPPRIME = "{\"status\":\"error\",\"description\":\"Auteur supprimé\"}";
		public static final String AUTEUR_MIS_A_JOUR = "{\"status\":\"error\",\"description\":\"Auteur mis à jour\"}";
	
	
	
	/*
	 * JSON Error messages
	 */
		// Auteur
		public static final String AUTEUR_NON_TROUVE = "{\"status\":\"error\",\"description\":\"Auteur non trouvé\"}";
	
		
		
		
	public static String errorAsJSON(String errorDescriptionMessage) {
		return "{\"status\":\"error\",\"description\":\""+ errorDescriptionMessage + "\"}";
	}
}
