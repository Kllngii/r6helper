package kllngii.r6s;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.Arrays;

import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.ServerEndpoint;
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
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Speichert und liefert den Inhalt
 * der Datei "r6helper.json".
 * 
 * @author Carsten Kelling
 */
@Path("data")
public class DataService {
    
    private static final String FILENAME = "r6helper.json";
    private static final String EMPTY_JSON = "{}";
    
    /** The HTTP {@code Access-Control-Allow-Origin} header field name. */
    public static final String ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";
    public static final String ACCESS_CONTROL_ALLOW_ORIGIN_VALUE = "*" /*"http://mine.kelling.de:8080"*/;

    public static final String ACCESS_CONTROL_ALLOW_METHODS = "Access-Control-Allow-Methods";
    public static final String ACCESS_CONTROL_ALLOW_HEADERS = "Access-Control-Allow-Headers";
    public static final String ACCESS_CONTROL_MAX_AGE = "Access-Control-Max-Age";

    
    private final Logger log = LoggerFactory.getLogger(getClass());
    
    
    private void accessControlAllowOrigin(HttpServletResponse response) {
        response.setHeader(ACCESS_CONTROL_ALLOW_ORIGIN, ACCESS_CONTROL_ALLOW_ORIGIN_VALUE);
    }
    
    
    /**
     * @return  Den gesamten Inhalt der Datei r6helper.json;
     *          falls diese nicht existiert ein leeres JSON.
     */
    @GET
    @Path("all")
    @Produces("application/json")
    public String getAll(@Context HttpServletResponse response) {
        return get(response);
    }
    
    
    @OPTIONS
    @Path("all")
    @Produces("text/plain")
    public Response optionsAll() {
        return Response.ok()
                .header(ACCESS_CONTROL_ALLOW_ORIGIN, ACCESS_CONTROL_ALLOW_ORIGIN_VALUE)
                .header(ACCESS_CONTROL_ALLOW_METHODS, "PUT, DELETE, OPTIONS")
                .header(ACCESS_CONTROL_ALLOW_HEADERS, "Content-Type")
                .header(ACCESS_CONTROL_MAX_AGE, 86400)
                .build();
    }
    
    /**
     * Überschreibt die Datei r6helper.json vollständig.
     */
    @PUT
    @Path("all")
    @Consumes("application/json")
    public void putAll(String json, @Context HttpServletResponse response) {
        accessControlAllowOrigin(response);
        
        json = StringUtils.defaultIfBlank(json, EMPTY_JSON);
        
        try {
            java.nio.file.Path outfile = Paths.get(FILENAME);
            Files.write(outfile, Arrays.asList(json), StandardCharsets.UTF_8);
        }
        catch (IOException ex) {
            log.error("Fehler beim Beschreiben der Datei " + FILENAME, ex);
            throw new InternalServerErrorException(ex);
        }
    }
    
    
    @GET
    @Path("repo")
    @Produces("application/json")
    public String getRepo(@Context HttpServletResponse response) {
        String json = get(response);
        
        JSONObject jObj = new JSONObject(json);
        JSONArray jTeam = jObj.getJSONArray("spielerrepo");
        return jTeam.toString(3);
    }
    
    
    private String get(@Context HttpServletResponse response) {
    	accessControlAllowOrigin(response);
        
        java.nio.file.Path infile = Paths.get(FILENAME);
        byte[] bytes = null;
        try {
            bytes = Files.readAllBytes(infile);
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
    
    
    @GET
    @Path("repo/{playername}")
    @Produces("application/json")
    public String getRepoPlayer(@PathParam("playername") String playername, @Context HttpServletResponse response) {
    	
    	String json = get(response);
    	JSONObject jObj = new JSONObject(json);
        JSONArray jTeam = jObj.getJSONArray("spielerrepo");
        
        int index = -1;
        for( int i=0; i < jTeam.length(); i++ ) {
            if( playername.equals( jTeam.getJSONObject(i).get("name") ) ) {
                index = i;
            }
        }
        if(index == -1) {
        	log.warn("Spieler wurde nicht gefunden! " + playername);
        	return "{\"error\":\"nicht gefunden\"}";
        }
        JSONObject jPlayer = jTeam.getJSONObject(index);
        return jPlayer.toString(3);
    }
}
