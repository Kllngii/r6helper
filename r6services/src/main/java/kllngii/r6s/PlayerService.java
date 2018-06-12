package kllngii.r6s;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.Arrays;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Path("player")
public class PlayerService {
	private final String EMPTY_JSON = "{}";
	public static final String ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";
    public static final String ACCESS_CONTROL_ALLOW_ORIGIN_VALUE = "*";

    public static final String ACCESS_CONTROL_ALLOW_METHODS = "Access-Control-Allow-Methods";
    public static final String ACCESS_CONTROL_ALLOW_HEADERS = "Access-Control-Allow-Headers";
    public static final String ACCESS_CONTROL_MAX_AGE = "Access-Control-Max-Age";
    
    private final Logger log = LoggerFactory.getLogger(getClass());
    
    private void accessControlAllowOrigin(HttpServletResponse response) {
        response.setHeader(ACCESS_CONTROL_ALLOW_ORIGIN, ACCESS_CONTROL_ALLOW_ORIGIN_VALUE);
    }
    
    
    /**
     * Beispiel für die URL:
     * /player/mickymaus?verbose=true
     * 
     * @param playername
     * @param _verbose
     * @return
     */
    @GET
    @Path("{playername}")
    @Produces("application/json")
    public String getPlayer(@PathParam("playername") String playername, @Context HttpServletResponse response) {
    	String FILENAME = playername + ".json";
    	accessControlAllowOrigin(response);
        
        java.nio.file.Path infile = Paths.get(FILENAME);
        byte[] bytes = null;
        try {
            bytes = Files.readAllBytes(infile);
            log.info("Liefere Daten zu Spieler: " + playername);
        }
        catch (NoSuchFileException ex) {
            log.info("Datei {} existiert noch nicht, liefere leeres JSON.", FILENAME);
            return EMPTY_JSON;
        }
        catch (IOException ex) {
            throw new InternalServerErrorException(ex);
        }
        
        String json;
        if (bytes == null || bytes.length <= 0)
            json = EMPTY_JSON;
        else
            json = new String(bytes, StandardCharsets.UTF_8);
        
        return json;
    }
    
    @OPTIONS
    @Path("{playername}")
    @Produces("text/plain")
    public Response optionsPlayer(@PathParam("playername") String playername) {
        return Response.ok()
                .header(ACCESS_CONTROL_ALLOW_ORIGIN, ACCESS_CONTROL_ALLOW_ORIGIN_VALUE)
                .header(ACCESS_CONTROL_ALLOW_METHODS, "PUT, DELETE, OPTIONS")
                .header(ACCESS_CONTROL_ALLOW_HEADERS, "Content-Type")
                .header(ACCESS_CONTROL_MAX_AGE, 86400)
                .build();
    }
    
    @PUT
    @Path("{playername}")
    @Consumes("application/json")
    public void putAll(@PathParam("playername") String playername, String json, @Context HttpServletResponse response) {
        accessControlAllowOrigin(response);
        String FILENAME = playername + ".json";
        json = StringUtils.defaultIfBlank(json, EMPTY_JSON);
        log.info("Neue JSON-Daten: {}", json);
        
        try {
            java.nio.file.Path outfile = Paths.get(FILENAME);
            Files.write(outfile, Arrays.asList(json), StandardCharsets.UTF_8);
            log.info("Datei des Spielers: " + playername + " geändert.");
        }
        catch (IOException ex) {
            log.error("Fehler beim Beschreiben der Datei " + FILENAME, ex);
            throw new InternalServerErrorException(ex);
        }
    }
    
}
