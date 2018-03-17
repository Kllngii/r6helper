package kllngii.r6s;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("test")
public class TestService {

    @GET
    @Path("ping")
    public String ping() {
        return "Pong!";
    }
}
