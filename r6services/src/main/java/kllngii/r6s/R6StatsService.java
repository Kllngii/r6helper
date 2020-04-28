package kllngii.r6s;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.Arrays;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import kllngii.r6s.konst.StatsKonst;
import kllngii.r6s.stats.StatsMarshaller;
import kllngii.r6s.stats.StatsVars;

@Path("stats")
public class R6StatsService {
	Logger log = Logger.getLogger(getClass());
	
	@EJB
	StatsMarshaller s;
	
	@EJB
	StatsVars statsVars;
	
	@GET
	@Path("multiple")
	@Produces("application/json")
	public String getStatsOfMultiple(@QueryParam("names") String names) {
		String[] player = names.split(",");
		JSONObject json = new JSONObject();
		JSONArray arr = new JSONArray();
		for(String p : player)
			arr.put(getStats(p, "true"));
		json.put("players", arr);
		return json.toString();
	}
	@GET
	@Produces("application/json")
	public String getStats(@QueryParam("playername") String name, @QueryParam("forceUpdate") String fUpdate) { 
		
		log.info("StatsWebService=" + s);
		
		/* *** != Null Tests *** */
		if(name == null || name == "") {
			log.warn("Falscher Aufruf! playername=" + name);
			return "{\"ERROR\":\"Es wurde kein korreter QueryParameter angegeben!\"}";
		}
		//FIXME WTF
//		if(fUpdate != null && ( fUpdate.toLowerCase() != "true" || fUpdate.toLowerCase() != "false" )) {
//			log.warn("Falscher Aufruf! forceupdate=" + fUpdate);
//			log.info("true".equals(fUpdate));
//			return "{\"ERROR\":\"Es wurde kein korrekter QueryParameter angegeben!\"}";
//		}
		/* *** Initalisieren *** */
		if(!s.isInitialized()) {
			log.info("Initialisiere!");
			s.inital();
			s.setInitalized(true);
			log.debug("Initialisierung abgeschlossen! inialized=" + s.isInitialized());
		}
		if(!statsVars.getLast().containsKey(name) || Boolean.parseBoolean(fUpdate)) {
			statsVars.getLast().put(name, System.currentTimeMillis());

			String filename = name + ".json";
			//TODO Für alle Plattformen und Regionen Suche ermöglichen
			String json = s.getStats(StatsKonst.PLATFORM_PS4, name, StatsKonst.REGION_EMEA);
			saveToFile(filename, json);
			return json;
		}
		else if(statsVars.getLast().get(name) < (System.currentTimeMillis() + 1000*3600)){
			return readFromFile(name + ".json");
		}
		else {
			statsVars.getLast().put(name, System.currentTimeMillis());

			String filename = name + ".json";
			String json = s.getStats(StatsKonst.PLATFORM_PS4, name, StatsKonst.REGION_EMEA);
			saveToFile(filename, json);
			return json;
		}
	}
	
	private String readFromFile(String filename) {
		java.nio.file.Path infile = Paths.get(filename);
		byte[] bytes = null;
		try {
		    bytes = Files.readAllBytes(infile);
		    log.info("Liefere Daten " + filename);
		}
		catch (NoSuchFileException ex) {
		    log.info("Datei {} existiert noch nicht, liefere leeres JSON. " + filename);
		    return "{}";
		}
		catch (IOException ex) {
		    throw new InternalServerErrorException(ex);
		}
		
		String json;
		if (bytes == null || bytes.length <= 0) {
		    json = "{}";
		    log.error("Fehler! - Dies sollte eigentlich nicht passieren");
		}
		else
		    json = new String(bytes, StandardCharsets.UTF_8);
		
		return json;
	}
	
	private String saveToFile(String filename, String json) {
		try {
			java.nio.file.Path outfile = Paths.get(filename);
			Files.write(outfile, Arrays.asList(json), StandardCharsets.UTF_8);
			log.info("Datei " + filename + " geändert.");
		}
		catch (IOException ex) {
			log.error("Fehler beim Beschreiben der Datei " + filename, ex);
			throw new InternalServerErrorException(ex);
		}
		return json;
	}
	@Path("initial")
	@GET
	@Produces("application/json")
	public String initailize() {
		s.inital();
		s.setInitalized(true);
		log.info("Werde initialisiert!");
		return "{}";
		
	}
}
