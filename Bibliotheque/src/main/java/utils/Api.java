package utils;

public class Api {
	
	
	/*
	 * JSON Success messages
	 */
		// Auteur
		public static final String AUTEUR_SUPPPRIME = "{\"status\":\"success\",\"description\":\"Auteur supprimé\"}";
		public static final String AUTEUR_MIS_A_JOUR = "{\"status\":\"success\",\"description\":\"Auteur mis à jour\"}";
		
		// Livre
		public static final String LIVRE_SUPPPRIME = "{\"status\":\"success\",\"description\":\"Livre supprimé\"}";
		public static final String LIVRE_MIS_A_JOUR = "{\"status\":\"success\",\"description\":\"Livre mis à jour\"}";
	
	/*
	 * JSON Error messages
	 */
		// API
		public static final String RESSOURCE_NON_TROUVE = "{\"status\":\"error\",\"description\":\"Ressource non trouvé\"}";
		public static final String INTERNAL_SERVER_ERROR = "{\"status\":\"error\",\"description\":\"Erreur interne au serveur\"}";
		
		// Auteur
		public static final String AUTEUR_NON_TROUVE = "{\"status\":\"error\",\"description\":\"Auteur non trouvé\"}";
		public static final String AUTEUR_ERREUR_VALEUR_NOM = "{\"status\":\"error\",\"description\":\"Nom manquant ou vide\"}";
		public static final String AUTEUR_ERREUR_VALEUR_PRENOM = "{\"status\":\"error\",\"description\":\"Prenom manquant ou vide\"}";
		public static final String AUTEUR_ERREUR_VALEUR_LANGUE = "{\"status\":\"error\",\"description\":\"Langue manquante,vide ou non prise en charge\"}";
		
		// Livre
		public static final String LIVRE_NON_TROUVE = "{\"status\":\"error\",\"description\":\"Livre non trouvé\"}";
		
		
		
	public static String errorAsJSON(String errorDescriptionMessage) {
		return "{\"status\":\"error\",\"description\":\""+ errorDescriptionMessage + "\"}";
	}
}
