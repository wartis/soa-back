package resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/alive")
@Produces(MediaType.APPLICATION_XML)
@Consumes(MediaType.APPLICATION_XML)
public class AliveResource {

    @GET
    public Response getChaptersGroupedBySpacemarines() {
        return Response.status(Response.Status.OK).entity(
            "<hello>world</hello>"
        ).build();
    }

}
