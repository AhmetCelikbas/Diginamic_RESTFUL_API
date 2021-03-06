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
    
    /**
     * @param idLivre
     * @return
     */
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
    		jsonResponse.put("nbExemplaire", livre.getNbExemplaire());
    		jsonResponse.put("nbDisponible", livre.getNbDisponible());
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
    
    
	/**
	 * @param titre
	 * @param datePublication
	 * @param description
	 * @param categorie
	 * @param disponible
	 * @param idAuteur
	 * @return
	 */
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createLivre(
			@FormParam("titre") final String titre,
			@FormParam("datePublication") final String datePublication,
			@FormParam("description") final String description,
			@FormParam("categorie") final String categorie,
			@FormParam("nbExemplaire") final String nbExemplaireString,
			@FormParam("nbDisponible") final String nbDisponibleString,
			@FormParam("idAuteur") final String idAuteurString
	){
		
		if(titre == null) { return Response.status(Response.Status.BAD_REQUEST).entity(utils.Api.LIVRE_ERREUR_VALEUR_TITRE).build(); }
		if(titre.isEmpty()) { return Response.status(Response.Status.BAD_REQUEST).entity(utils.Api.LIVRE_ERREUR_VALEUR_TITRE).build(); }
		
		if(datePublication == null) { return Response.status(Response.Status.BAD_REQUEST).entity(utils.Api.LIVRE_ERREUR_VALEUR_DATEPUBLICATION).build(); }
		if(datePublication.isEmpty()) { return Response.status(Response.Status.BAD_REQUEST).entity(utils.Api.LIVRE_ERREUR_VALEUR_DATEPUBLICATION).build(); }
		
		if(description == null) { return Response.status(Response.Status.BAD_REQUEST).entity(utils.Api.LIVRE_ERREUR_VALEUR_DESCRIPTION).build(); }
		if(description.isEmpty()) { return Response.status(Response.Status.BAD_REQUEST).entity(utils.Api.LIVRE_ERREUR_VALEUR_DESCRIPTION).build(); }

		if(categorie == null) { return Response.status(Response.Status.BAD_REQUEST).entity(utils.Api.LIVRE_ERREUR_VALEUR_CATEGORIE).build(); }
		if(categorie.isEmpty()) { return Response.status(Response.Status.BAD_REQUEST).entity(utils.Api.LIVRE_ERREUR_VALEUR_CATEGORIE).build(); }

		if(nbExemplaireString == null) { return Response.status(Response.Status.BAD_REQUEST).entity(utils.Api.LIVRE_ERREUR_VALEUR_NBEXEMPLAIRE).build(); }
		if(nbExemplaireString.isEmpty()) { return Response.status(Response.Status.BAD_REQUEST).entity(utils.Api.LIVRE_ERREUR_VALEUR_NBEXEMPLAIRE).build(); }

		if(nbDisponibleString == null) { return Response.status(Response.Status.BAD_REQUEST).entity(utils.Api.LIVRE_ERREUR_VALEUR_NBDISPONIBLE).build(); }
		if(nbDisponibleString.isEmpty()) { return Response.status(Response.Status.BAD_REQUEST).entity(utils.Api.LIVRE_ERREUR_VALEUR_NBDISPONIBLE).build(); }

		if(idAuteurString == null) { return Response.status(Response.Status.BAD_REQUEST).entity(utils.Api.LIVRE_ERREUR_VALEUR_IDAUTEUR).build(); }
		if(idAuteurString.isEmpty()) { return Response.status(Response.Status.BAD_REQUEST).entity(utils.Api.LIVRE_ERREUR_VALEUR_IDAUTEUR).build(); }
			
		try {
			
			
			int idAuteur = Integer.parseInt(idAuteurString);
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
					Integer.parseInt(nbExemplaireString),
					Integer.parseInt(nbDisponibleString),
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
			jsonResponse.put("nbExemplaire", livre.getNbExemplaire());
			jsonResponse.put("nbDisponible", livre.getNbDisponible());
			jsonResponse.put("auteur", livre.getAuteur().getIdAuteur());
			
			
	
			return Response.status(Response.Status.CREATED).entity(jsonMapper.writeValueAsString(jsonResponse)).build();
		} catch (java.lang.NumberFormatException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity(utils.Api.errorAsJSON(e.getMessage())).build();
		} catch (PersistenceException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(utils.Api.INTERNAL_SERVER_ERROR).build();
		} catch (JsonProcessingException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity(utils.Api.errorAsJSON(e.getMessage())).build();
		} 
	}
	
    /**
     * @param idLivre
     * @return
     */
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
    
    /**
     * @param idLivre
     * @param titre
     * @param datePublication
     * @param description
     * @param categorie
     * @param nbExemplaire
     * @param nbDisponible
     * @param idAuteur
     * @return
     */
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
    			@FormParam("nbExemplaire") final int nbExemplaire,
    			@FormParam("nbDisponible") final int nbDisponible,
    			@FormParam("idAuteur") final int idAuteur
    	){
    		
		if(titre == null) { return Response.status(Response.Status.BAD_REQUEST).entity(utils.Api.AUTEUR_ERREUR_VALEUR_NOM).build(); }
		if(titre.isEmpty()) { return Response.status(Response.Status.BAD_REQUEST).entity(utils.Api.AUTEUR_ERREUR_VALEUR_NOM).build(); }
		
		if(datePublication == null) { return Response.status(Response.Status.BAD_REQUEST).entity(utils.Api.AUTEUR_ERREUR_VALEUR_PRENOM).build(); }
		if(datePublication.isEmpty()) { return Response.status(Response.Status.BAD_REQUEST).entity(utils.Api.AUTEUR_ERREUR_VALEUR_PRENOM).build(); }
		
		if(description == null) { return Response.status(Response.Status.BAD_REQUEST).entity(utils.Api.AUTEUR_ERREUR_VALEUR_LANGUE).build(); }
		if(description.isEmpty()) { return Response.status(Response.Status.BAD_REQUEST).entity(utils.Api.AUTEUR_ERREUR_VALEUR_LANGUE).build(); }

		if(categorie == null) { return Response.status(Response.Status.BAD_REQUEST).entity(utils.Api.AUTEUR_ERREUR_VALEUR_LANGUE).build(); }
		if(categorie.isEmpty()) { return Response.status(Response.Status.BAD_REQUEST).entity(utils.Api.AUTEUR_ERREUR_VALEUR_LANGUE).build(); }

  		try {
	    		models.Livre livre = DatabaseService.find(models.Livre.class, idLivre);
	    		if(livre == null) { return Response.status(Response.Status.NOT_FOUND).entity(utils.Api.LIVRE_NON_TROUVE).build(); }
	    		
	    		models.Auteur auteur = DatabaseService.find(models.Auteur.class, idAuteur);
	    		if(auteur == null) { return Response.status(Response.Status.NOT_FOUND).entity(utils.Api.AUTEUR_NON_TROUVE).build(); }

	    		
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
    			livre.setNbDisponible(nbDisponible);
    			livre.setNbExemplaire(nbExemplaire);
    			livre.setAuteur(auteur);
    			
    			DatabaseService.update(livre);

    			return Response.status(Response.Status.OK).entity(utils.Api.LIVRE_MIS_A_JOUR).build();
    			
		} catch (java.lang.IllegalArgumentException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity(utils.Api.errorAsJSON(e.getMessage())).build();
		} catch (PersistenceException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(utils.Api.INTERNAL_SERVER_ERROR).build();
		}

    	}
    
    
}
