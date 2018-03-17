package kllngii.r6s;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.Arrays;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.commons.lang3.StringUtils;
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
    
    private final Logger log = LoggerFactory.getLogger(getClass());
    
    
    /**
     * @return  Den gesamten Inhalt der Datei r6helper.json;
     *          falls diese nicht existiert ein leeres JSON.
     */
    @GET
    @Path("all")
    @Produces("application/json")
    public String getAll() {
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
    
    /**
     * Überschreibt die Datei r6helper.json vollständig.
     */
    @PUT
    @Path("all")
    @Consumes("application/json")
    public void putAll(String json) {
        json = StringUtils.defaultIfBlank(json, EMPTY_JSON);
        log.info("Neue JSON-Daten: {}", json);
        
        try {
            java.nio.file.Path outfile = Paths.get(FILENAME);
            Files.write(outfile, Arrays.asList(json), StandardCharsets.UTF_8);
        }
        catch (IOException ex) {
            log.error("Fehler beim Beschreiben der Datei " + FILENAME, ex);
            throw new InternalServerErrorException(ex);
        }
    }
}
