package kllngii.r6s;

import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("test")
public class TestService {

    @GET
    public String ping() {
        return "Pong um " + new Date();
    }
}
