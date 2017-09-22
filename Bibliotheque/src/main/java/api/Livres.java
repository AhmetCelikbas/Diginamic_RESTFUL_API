package api;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import services.ConfigService;
import services.DatabaseService;
import utils.ListFilters;

@Path("/Livres")
public class Livres {
	
    public Livres() {
    }
    

    @GET
    @Produces(MediaType.APPLICATION_JSON)
	public Response getLivres(
			@QueryParam("page") int numPage,
			@QueryParam("sort") String sorting,
			@QueryParam("order") String order
	){	
    	
		if(sorting == null) {
			sorting = "order by titre";
		} else {
			if (sorting.equals("byTitre")) {
				sorting = "order by titre";
			} else if (sorting.equals("byCategorie")) {
				sorting = "order by categorie";
			} else if (sorting.equals("byDate")) {
				sorting = "order by datePublication";
			} else if (sorting.equals("byDisponible")) {
				sorting = "order by disponible";
			} else {
				sorting = "order by titre";
			}
		}
    		

    		try {
    			List<models.Livre> livres = DatabaseService.query(
    					"from Livre", ListFilters.numPage(numPage) * ConfigService.nbrResultatsParPages, 
    					ConfigService.nbrResultatsParPages, 
    					sorting,
    					order,
    					models.Livre.class);
		
        		ObjectMapper jsonMapper = new ObjectMapper();
        		ObjectNode jsonResponse = jsonMapper.createObjectNode();

        		jsonResponse.put("page", numPage);

        		ArrayNode livresNode = jsonMapper.createArrayNode();
        		for(models.Livre livre :  livres) {
            		ObjectNode livreObject = jsonMapper.createObjectNode();
            		livreObject.put("idLivre", livre.getIdLivre());
            		livreObject.put("titre", livre.getTitre());
            		livreObject.putPOJO("datePublication", livre.getDatePublication());
            		livreObject.put("description", livre.getDescription());
            		livreObject.putPOJO("categorie", livre.getCategorie());
            		livreObject.put("nbExemplaire", livre.getNbExemplaire());
            		livreObject.put("nbDisponible", livre.getNbDisponible());
            		livreObject.put("idAuteur", livre.getAuteur().getIdAuteur());
            		livresNode.add(livreObject);
        		}
        		jsonResponse.putPOJO("livres", livresNode);
        		
        		
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
    		
    		} catch (IllegalStateException e) {
			return Response
					.status(Response.Status.BAD_REQUEST)
					.entity(utils.Api.errorAsJSON(e.getMessage()))
					.build();
		} catch (IllegalArgumentException e) {
			return Response
					.status(Response.Status.BAD_REQUEST)
					.entity(utils.Api.errorAsJSON(e.getMessage()))
					.build();
		}
    	
	}
    
}
