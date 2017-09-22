package api;

import javax.persistence.PersistenceException;
import javax.persistence.RollbackException;
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

import org.joda.time.format.DateTimeFormat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import models.Categorie;
import services.DatabaseService;

@Path("/Livre")
public class Livre {
	
    public Livre() {
    	
    }
    
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
	public Response getLivre(@PathParam("id") final int idLivre){
    		models.Livre livre = DatabaseService.find(models.Livre.class, idLivre);
    		if(livre == null) { return Response.status(Response.Status.NOT_FOUND).entity(utils.Api.LIVRE_NON_TROUVE).build(); }
    		
    		
    		
    		ObjectMapper jsonMapper = new ObjectMapper();
    		ObjectNode jsonResponse = jsonMapper.createObjectNode();
    		jsonResponse.put("idLivre", livre.getIdLivre());
    		jsonResponse.put("titre", livre.getTitre());
    		jsonResponse.putPOJO("datePublication", livre.getDatePublication());
    		jsonResponse.put("description", livre.getDescription());
    		jsonResponse.putPOJO("categorie", livre.getCategorie());
    		jsonResponse.put("disponible", livre.isDisponible());
    		jsonResponse.put("auteur", livre.getAuteur().getIdAuteur());
    		
    		ObjectNode auteurObject = jsonMapper.createObjectNode();
    		auteurObject.put("idAuteur", livre.getAuteur().getIdAuteur());
    		auteurObject.put("nom", livre.getAuteur().getNom());
    		auteurObject.put("prenom", livre.getAuteur().getPrenom());
    		auteurObject.putPOJO("langue", livre.getAuteur().getLangue());
    		

    		jsonResponse.putPOJO("auteur", auteurObject);
    		
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
	public Response createLivre(
			@FormParam("titre") final String titre,
			@FormParam("datePublication") final String datePublication,
			@FormParam("description") final String description,
			@FormParam("categorie") final String categorie,
			@FormParam("disponible") final String disponible,
			@FormParam("idAuteur") final int idAuteur
	){
		
		models.Auteur auteur = DatabaseService.find(models.Auteur.class, idAuteur);
		if(auteur == null) { return Response.status(Response.Status.NOT_FOUND).entity(utils.Api.AUTEUR_NON_TROUVE).build(); }
			
		models.Livre livre = new models.Livre(
				titre, 
				new java.sql.Date(
						DateTimeFormat.forPattern("dd/MM/yyyy")
							.parseDateTime(datePublication)
							.toDate()
							.getTime()
				), 
				description, 
				Categorie.valueOf(categorie), 
				Boolean.valueOf(disponible),
				auteur
		);
		
		DatabaseService.presist(livre);

		ObjectMapper jsonMapper = new ObjectMapper();
		ObjectNode jsonResponse = jsonMapper.createObjectNode();
		jsonResponse.put("idLivre", livre.getIdLivre());
		jsonResponse.put("titre", livre.getTitre());
		jsonResponse.putPOJO("datePublication", livre.getDatePublication());
		jsonResponse.put("description", livre.getDescription());
		jsonResponse.putPOJO("categorie", livre.getCategorie());
		jsonResponse.put("disponible", livre.isDisponible());
		jsonResponse.put("auteur", livre.getAuteur().getIdAuteur());
		
		
		try {
			return Response
				.status(Response.Status.CREATED)
				.entity(jsonMapper.writeValueAsString(jsonResponse))
				.build();
		} catch (JsonProcessingException e) {
			
			e.printStackTrace();
			
			return Response
				.status(Response.Status.BAD_REQUEST)
				.entity(utils.Api.errorAsJSON(e.getMessage()))
				.build();
			
		}
	}
	
    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    	public Response removeLivre(@PathParam("id") final int idLivre){
    	
    		models.Livre livre = DatabaseService.find(models.Livre.class, idLivre);
    		if(livre == null) { return Response.status(Response.Status.NOT_FOUND).entity(utils.Api.LIVRE_NON_TROUVE).build(); }
    		
    		try {
    			DatabaseService.remove(livre);
    			return Response
        				.status(Response.Status.OK)
        				.entity(utils.Api.LIVRE_SUPPPRIME)
        				.build();
    			
    		} catch (PersistenceException e) {
    			return Response
        				.status(Response.Status.BAD_REQUEST)
        				.entity(utils.Api.errorAsJSON(e.getMessage()))
        				.build();
		} 

    	}
    
    @PUT
    @Path("{id}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    	public Response updateLivre(
        		@PathParam("id") final int idLivre,
    			@FormParam("titre") final String titre,
    			@FormParam("datePublication") final String datePublication,
    			@FormParam("description") final String description,
    			@FormParam("categorie") final String categorie,
    			@FormParam("disponible") final String disponible,
    			@FormParam("idAuteur") final int idAuteur
    	){
    		
    		/*
    		 * Vérifier params
    		 */
    	
    	
    		models.Livre livre = DatabaseService.find(models.Livre.class, idLivre);
    		if(livre == null) { return Response.status(Response.Status.NOT_FOUND).entity(utils.Api.LIVRE_NON_TROUVE).build(); }
    		
    		models.Auteur auteur = DatabaseService.find(models.Auteur.class, idAuteur);
    		if(auteur == null) { return Response.status(Response.Status.NOT_FOUND).entity(utils.Api.AUTEUR_NON_TROUVE).build(); }
    		
    		try {

    			livre.setTitre(titre);
    			livre.setDatePublication(
				new java.sql.Date(
					DateTimeFormat.forPattern("dd/MM/yyyy")
						.parseDateTime(datePublication)
						.toDate()
						.getTime()
				)
    			);
    			
    			livre.setDescription(description);
    			livre.setCategorie(Categorie.valueOf(categorie));
    			livre.setDisponible(Boolean.valueOf(disponible));
    			livre.setAuteur(auteur);
    			
    			DatabaseService.update(livre);
    			
    			return Response
        				.status(Response.Status.OK)
        				.entity(utils.Api.LIVRE_MIS_A_JOUR)
        				.build();
    			
    		} catch (RollbackException e) {
    			return Response
        				.status(Response.Status.BAD_REQUEST)
        				.entity(utils.Api.errorAsJSON(e.getMessage()))
        				.build();
		}

    	}
    
    
}
