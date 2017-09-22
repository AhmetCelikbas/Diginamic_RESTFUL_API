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
		public static final String LIVRE_ERREUR_VALEUR_TITRE = "{\"status\":\"error\",\"description\":\"Titre manquant ou vide\"}";
		public static final String LIVRE_ERREUR_VALEUR_DATEPUBLICATION = "{\"status\":\"error\",\"description\":\"Date de publication manquant ou vide\"}";
		public static final String LIVRE_ERREUR_VALEUR_DESCRIPTION = "{\"status\":\"error\",\"description\":\"Description manquant ou vide\"}";
		public static final String LIVRE_ERREUR_VALEUR_CATEGORIE = "{\"status\":\"error\",\"description\":\"Catégorie manquant ou vide\"}";
		public static final String LIVRE_ERREUR_VALEUR_NBEXEMPLAIRE = "{\"status\":\"error\",\"description\":\"Nombre d'expemplaires manquant ou vide\"}";
		public static final String LIVRE_ERREUR_VALEUR_NBDISPONIBLE = "{\"status\":\"error\",\"description\":\"Nombre de livres disponibles manquant ou vide\"}";
		public static final String LIVRE_ERREUR_VALEUR_IDAUTEUR = "{\"status\":\"error\",\"description\":\"Id auteur manquant ou vide\"}";
		
		
	public static String errorAsJSON(String errorDescriptionMessage) {
		return "{\"status\":\"error\",\"description\":\""+ errorDescriptionMessage + "\"}";
	}
}
