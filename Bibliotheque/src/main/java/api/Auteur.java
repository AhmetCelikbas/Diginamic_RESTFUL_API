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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    		
    		try {
    			return Response
    				.status(Response.Status.OK)
    				.entity(new ObjectMapper().writeValueAsString(auteur))
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
		models.Auteur auteur = new models.Auteur(
				nom, 
				prenom, 
				Langue.valueOf(langue)
		);
		DatabaseService.presist(auteur);
		
		try {
			return Response
				.status(Response.Status.CREATED)
				.entity(new ObjectMapper().writeValueAsString(auteur))
				.build();
		} catch (JsonProcessingException e) {
			
			return Response
				.status(Response.Status.BAD_REQUEST)
				.entity(utils.Api.errorAsJSON(e.getMessage()))
				.build();
			
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
    			return Response
        				.status(Response.Status.OK)
        				.entity(utils.Api.AUTEUR_SUPPPRIME)
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
    	public Response updateAuteur(
        		@PathParam("id") final int idAuteur,
        		@FormParam("nom") final String nom,
        		@FormParam("prenom") final String prenom,
        		@FormParam("langue") final String langue
    	){
    		models.Auteur auteur = DatabaseService.find(models.Auteur.class, idAuteur);
    		if(auteur == null) { return Response.status(Response.Status.NOT_FOUND).entity(utils.Api.AUTEUR_NON_TROUVE).build(); }
    		
    		try {

    			DatabaseService.getEntity().getTransaction().begin();
    			auteur.setNom(nom);
    			auteur.setPrenom(prenom);
    			auteur.setLangue(Langue.valueOf(langue));
    			DatabaseService.getEntity().getTransaction().commit();
    			
    			return Response
        				.status(Response.Status.OK)
        				.entity(utils.Api.AUTEUR_MIS_A_JOUR)
        				.build();
    			
    		} catch (RollbackException e) {
    			return Response
        				.status(Response.Status.BAD_REQUEST)
        				.entity(utils.Api.errorAsJSON(e.getMessage()))
        				.build();
		}

    	}
    
    
}
