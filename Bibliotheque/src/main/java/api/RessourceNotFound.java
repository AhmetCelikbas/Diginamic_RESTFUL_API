package api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/RessourceNotFound")
public class RessourceNotFound {

	
	@GET
    @Produces(MediaType.APPLICATION_JSON)
	public Response notFound() {
		return Response.status(Response.Status.NOT_FOUND).entity(utils.Api.RESSOURCE_NON_TROUVE).build();
	}
}
