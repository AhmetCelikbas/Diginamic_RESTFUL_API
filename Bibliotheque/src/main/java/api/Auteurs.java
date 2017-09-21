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
import com.fasterxml.jackson.databind.node.ObjectNode;
import services.ConfigService;
import services.DatabaseService;
import utils.ListFilters;

@Path("/Auteurs")
public class Auteurs {

    public Auteurs() {
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
	public Response getAuteurs(
			@QueryParam("page") int numPage,
			@QueryParam("sort") String sorting,
			@QueryParam("order") String order
	){	
    	
		if(sorting == null) {
			sorting = "order by nom";
		} else {
			if (sorting.equals("byNom")) {
				sorting = "order by nom";
			} else if (sorting.equals("byPrenom")) {
				sorting = "order by prenom";
			} else if (sorting.equals("byLangue")) {
				sorting = "order by langue";
			} else {
				sorting = "order by nom";
			}
		}
    		

    		try {
    			List<models.Auteur> auteurs = DatabaseService.query(
    					"from Auteur", ListFilters.numPage(numPage) * ConfigService.nbrResultatsParPages, 
    					ConfigService.nbrResultatsParPages, 
    					sorting,
    					order,
    					models.Auteur.class);
		
        		ObjectMapper jsonMapper = new ObjectMapper();
        		ObjectNode jsonResponse = jsonMapper.createObjectNode();

        		jsonResponse.put("page", numPage);
        		jsonResponse.putPOJO("authors", auteurs);
        		
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