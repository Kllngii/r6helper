package kllngii.r6s;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("test")
public class TestService {

    @GET
    public String ping() {
        return "Pong!";
    }
    
    @DELETE
    public void deletePing(String body) {
        System.out.println("Ich lösche jetzt den " + body);
    }
}
