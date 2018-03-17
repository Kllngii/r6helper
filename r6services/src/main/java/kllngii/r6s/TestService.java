package kllngii.r6s;

import java.util.Date;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("test")
public class TestService {

    @GET
    public String ping() {
        return "Pong um " + new Date();
    }
    
    @POST
    @Path("upload/{filename}")
    public void deletePing(@PathParam("filename") String filename, String content) {
        //FIXME Keine gute Idee, der Aufrufer kann so beliebig viele Dateien auf unserem Server anlegen. Nur ein Beispiel für @PathParam und @POST.
        System.out.println("Ich erzeuge jetzt die Datei " + filename + " mit Inhalt " + content);
    }
    
    @DELETE
    public void deletePing(String body) {
        System.out.println("Ich lösche jetzt den " + body);
    }
}
