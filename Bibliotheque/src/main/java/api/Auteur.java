package api;

import javax.persistence.PersistenceException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.Langue;
import services.DatabaseService;



@Path("/Auteur")
public class Auteur {

    public Auteur() {
    }
    
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
	public Response getAuteur(@PathParam("id") final int idAuteur){
    	
    		models.Auteur auteur = DatabaseService.find(models.Auteur.class, idAuteur);
    		if(auteur == null) { return Response.status(Response.Status.NOT_FOUND).entity(utils.Api.AUTEUR_NON_TROUVE).build(); }
    		
    		
    		
    		ObjectMapper jsonMapper = new ObjectMapper();
    		ObjectNode jsonResponse = jsonMapper.createObjectNode();
    		jsonResponse.put("idAuteur", auteur.getIdAuteur());
    		jsonResponse.put("nom", auteur.getNom());
    		jsonResponse.put("prenom", auteur.getPrenom());
    		jsonResponse.putPOJO("langue", auteur.getLangue());
    		
    		ArrayNode livres = jsonMapper.createArrayNode();
    		for( models.Livre livre :  auteur.getLivre()) {
    			
        		ObjectNode livreObject = jsonMapper.createObjectNode();
        		livreObject.put("idLivre", livre.getIdLivre());
        		livreObject.put("titre", livre.getTitre());
        		livreObject.putPOJO("datePublication", livre.getDatePublication());
        		livreObject.put("description", livre.getDescription());
        		livreObject.putPOJO("categorie", livre.getCategorie());
        		livreObject.put("nbExemplaire", livre.getNbExemplaire());
        		livreObject.put("nbDisponible", livre.getNbDisponible());
        		
        		livres.add(livreObject);
    		}
    		jsonResponse.putPOJO("livres", livres);
    		

    		
    		
    		try {
    			return Response
    				.status(Response.Status.OK)
    				.entity(jsonMapper.writeValueAsString(jsonResponse))
    				.build();
    		} catch (JsonProcessingException e) {
    			
    			return Response
    				.status(Response.Status.BAD_REQUEST)
    				.entity(utils.Api.errorAsJSON(e.getMessage()))
    				.build();
    			
    		}
	}

    	@POST
    	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    	@Produces(MediaType.APPLICATION_JSON)
    	public Response createAuteur(
    			@FormParam("nom") final String nom,
    			@FormParam("prenom") final String prenom,
    			@FormParam("langue") final String langue
    	){
    		
    		if(nom == null) { return Response.status(Response.Status.BAD_REQUEST).entity(utils.Api.AUTEUR_ERREUR_VALEUR_NOM).build(); }
    		if(nom.isEmpty()) { return Response.status(Response.Status.BAD_REQUEST).entity(utils.Api.AUTEUR_ERREUR_VALEUR_NOM).build(); }
    		if(prenom == null) { return Response.status(Response.Status.BAD_REQUEST).entity(utils.Api.AUTEUR_ERREUR_VALEUR_PRENOM).build(); }
    		if(prenom.isEmpty()) { return Response.status(Response.Status.BAD_REQUEST).entity(utils.Api.AUTEUR_ERREUR_VALEUR_PRENOM).build(); }
    		if(langue == null) { return Response.status(Response.Status.BAD_REQUEST).entity(utils.Api.AUTEUR_ERREUR_VALEUR_LANGUE).build(); }
    		if(langue.isEmpty()) { return Response.status(Response.Status.BAD_REQUEST).entity(utils.Api.AUTEUR_ERREUR_VALEUR_LANGUE).build(); }
    		
    		try {
    			models.Auteur auteur = new models.Auteur(
    					nom, 
    					prenom, 
    					Langue.valueOf(langue)
    			);
    			
    			DatabaseService.presist(auteur);
    			
			return Response.status(Response.Status.CREATED).entity(new ObjectMapper().writeValueAsString(auteur)).build();
		} catch (java.lang.IllegalArgumentException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity(utils.Api.AUTEUR_ERREUR_VALEUR_LANGUE).build();
		} catch (PersistenceException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(utils.Api.INTERNAL_SERVER_ERROR).build();
		} catch (JsonProcessingException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity(utils.Api.errorAsJSON(e.getMessage())).build();
		} 
   
		

	}

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    	public Response removeAuteur(@PathParam("id") final int idAuteur){
    	
    		models.Auteur auteur = DatabaseService.find(models.Auteur.class, idAuteur);
    		if(auteur == null) { return Response.status(Response.Status.NOT_FOUND).entity(utils.Api.AUTEUR_NON_TROUVE).build(); }
    		
    		try {
    			DatabaseService.remove(auteur);
    			return Response.status(Response.Status.OK).entity(utils.Api.AUTEUR_SUPPPRIME).build();
    			
    		} catch (PersistenceException e) {
    			return Response.status(Response.Status.BAD_REQUEST).entity(utils.Api.errorAsJSON(e.getMessage())).build();
		} 

    	}
    	
    @PUT
    @Path("{id}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    	public Response updateAuteur(
        		@PathParam("id") final int idAuteur,
        		@FormParam("nom") final String nom,
        		@FormParam("prenom") final String prenom,
        		@FormParam("langue") final String langue
    	){
    	
		if(nom == null) { return Response.status(Response.Status.BAD_REQUEST).entity(utils.Api.AUTEUR_ERREUR_VALEUR_NOM).build(); }
		if(nom.isEmpty()) { return Response.status(Response.Status.BAD_REQUEST).entity(utils.Api.AUTEUR_ERREUR_VALEUR_NOM).build(); }
		if(prenom == null) { return Response.status(Response.Status.BAD_REQUEST).entity(utils.Api.AUTEUR_ERREUR_VALEUR_PRENOM).build(); }
		if(prenom.isEmpty()) { return Response.status(Response.Status.BAD_REQUEST).entity(utils.Api.AUTEUR_ERREUR_VALEUR_PRENOM).build(); }
		if(langue == null) { return Response.status(Response.Status.BAD_REQUEST).entity(utils.Api.AUTEUR_ERREUR_VALEUR_LANGUE).build(); }
		if(langue.isEmpty()) { return Response.status(Response.Status.BAD_REQUEST).entity(utils.Api.AUTEUR_ERREUR_VALEUR_LANGUE).build(); }
    	
    	
    		models.Auteur auteur = DatabaseService.find(models.Auteur.class, idAuteur);
    		if(auteur == null) { return Response.status(Response.Status.NOT_FOUND).entity(utils.Api.AUTEUR_NON_TROUVE).build(); }
    		
    		try {

    			auteur.setNom(nom);
    			auteur.setPrenom(prenom);
    			auteur.setLangue(Langue.valueOf(langue));
    			DatabaseService.update(auteur);
    			
    			return Response.status(Response.Status.OK).entity(utils.Api.AUTEUR_MIS_A_JOUR).build();
    		} catch (java.lang.IllegalArgumentException e) {
    			return Response.status(Response.Status.BAD_REQUEST).entity(utils.Api.AUTEUR_ERREUR_VALEUR_LANGUE).build();
    		} catch (PersistenceException e) {
    			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(utils.Api.INTERNAL_SERVER_ERROR).build();
    		} 
    	}
    
    
}
