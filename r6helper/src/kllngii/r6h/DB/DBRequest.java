package kllngii.r6h.DB;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import javafx.scene.control.TextInputDialog;
import kllngii.r6h.model.Rang;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DBRequest {
	
	private final Logger log = Logger.getLogger(getClass());
	
	private DBValues setPlayerValues(String jsonString) {
		JSONObject json = new JSONObject(jsonString);
		DBValues player = new DBValues();
		
		player.setName(json.getString("name"));
		player.setId(json.getString("id"));
		player.setLevel(json.getInt("level"));
		
		List<Rank> seasons = new ArrayList<>();
		JSONArray ranks = json.getJSONArray("seasonRanks");
		//Alte Seasons hinzufügen
		for(Object obj : ranks) {
			Rank r = new Rank();
			JSONObject season = (JSONObject) obj;
			int i = season.getJSONObject("emea").getInt("rank");
			r.setRang(Rang.getRangWithInt(i));
			r.setSeason(season.getInt("season"));
			seasons.add(r);
		}
		//Aktuelle Season hinzufügen
		Rank r = new Rank();
		r.setSeason(json.getJSONObject("rank").getInt("season"));
		r.setRang(Rang.getRangWithInt(json.getJSONObject("rank").getJSONObject("emea").getInt("rank")));
		seasons.add(r);
		player.setSeasons(seasons);
		
		GeneralStats general = new GeneralStats();
		JSONObject stats = json.getJSONObject("stats").getJSONObject("general");
		
		general.setAssists(stats.getInt("assists"));
		//TODO BestScore aus dem der verschiedenen Spielmodi aussuchen
		general.setBestScore(0);
		general.setBulletsFired(stats.getInt("bulletsFired"));
		general.setBulletsHit(stats.getInt("bulletsHit"));
		general.setDbno(stats.getInt("dbno"));
		general.setDeaths(stats.getInt("deaths"));
		general.setGadgetsDestroyed(stats.getInt("gadgetsDestroyed"));
		general.setHeadshotKills(stats.getInt("headshot"));
		general.setKills(stats.getInt("kills"));
		general.setMeleeKills(stats.getInt("meleeKills"));
		general.setRoundsPlayed(stats.getInt("played"));
		general.setRoundsWon(stats.getInt("won"));
		general.setRoundsLost(general.getRoundsPlayed() - general.getRoundsWon());
		general.setSpielmodus("General");
		general.setSuicides(stats.getInt("suicides"));
		
		player.setGeneralStats(general);
		
		//Stats aus JSON holen
		JSONObject statsBomb = json.getJSONObject("stats").getJSONObject("bomb");
		JSONObject statsCasual = json.getJSONObject("stats").getJSONObject("casual");
		JSONObject statsHostage = json.getJSONObject("stats").getJSONObject("hostage");
		List<Stats> statsList = new ArrayList<>();
		statsList.add(getStatsJSON(statsBomb));
		statsList.add(getStatsJSON(statsCasual));
		statsList.add(getStatsJSON(statsHostage));
		player.setStats(statsList);
		return player;
	}

	private Stats getStatsJSON(JSONObject stats) {
		Stats s = new Stats();
		if(stats.has("bestScore"))
			s.setBestScore(stats.getInt("bestScore"));
		s.setRoundsLost(stats.getInt("lost"));
		s.setRoundsPlayed(stats.getInt("played"));
		s.setRoundsWon(stats.getInt("won"));
		//TODO Spielmodus aus der JSON holen
		s.setSpielmodus(null);
		return s;
	}
	/**
	 * 
	 * @param id Die ID des Spielers, siehe {@link getPlayer}
	 * @return Die JSON des Spielers für die {@link setValues} Methode
	 */
	private String getPlayerDataFromServer(String id) {
		String result = null;
		final String url = "https://r6db.com/api/v2/players/" + id + "?platform=PS4&update=false";
		
		OkHttpClient client = new OkHttpClient();
		
		Request request = new Request.Builder()
			      .url(url)
			      .get()
			      .addHeader("x-app-id", "5e23d930-edd3-4240-b9a9-723c673fb649")
			      .build();
		try (Response response = client.newCall(request).execute()) {
			if (! response.isSuccessful())
			    log.warn("Laden der Daten von URL war nicht erfolgreich. Status=" + response.code() + ", URL=" + url);
			
			result = response.body().string();
		}
		catch (IOException ex) {
		    log.warn("Laden der Daten von URL war nicht erfolgreich. Exception=" + ex + ", URL=" + url);
		}
		
		return result;
		
	}
	/**
	 * 
	 * @param name Name des Strings
	 * @return Das JSON für die {@link getPlayer} Methode
	 */
	private String getSearchDataFromServer(String name) {
		String result = null;
		// Per HTTP-GET Daten von einer URL holen:
		final String url = "https://r6db.com/api/v2/players?name=" + name + "&platform=PS4";
		
		OkHttpClient client = new OkHttpClient();

		Request request = new Request.Builder()
	      .url(url)
	      .get()
	      .addHeader("x-app-id", "5e23d930-edd3-4240-b9a9-723c673fb649")
	      .build();
		try (Response response = client.newCall(request).execute()) {
			if (! response.isSuccessful())
			    log.warn("Laden der Daten von URL war nicht erfolgreich. Status=" + response.code() + ", URL=" + url);
			
			result = response.body().string();
		}
		catch (IOException ex) {
		    log.warn("Laden der Daten von URL war nicht erfolgreich. Exception=" + ex + ", URL=" + url);
		}
		
		return result;
	}
	/**
	 * 
	 * @param playername Spielername
	 * @return Das JSON des Spielers
	 */
	public String getPlayerName(String playername) {
		JSONArray js = new JSONArray(getSearchDataFromServer(playername));
		JSONObject json = js.getJSONObject(0);
		String id = json.getString("id");
		if(id == null || id == "") {
			try {
				throw new DBError();
			} catch (DBError e) {
				try {
					throw new IOException();
				} catch (IOException e1) {
					//Aufgeben ist eh egal...
					e1.printStackTrace();
				}
			}
		}
		return getPlayerDataFromServer(id);
	}
	
	/**
	 * 
	 * @param db Die gesetzten DBValues, siehe {@link setValues}
	 */
	private void printSpieler(DBValues db) {
		log.info("--------------");
		log.info("Name: " + db.getName());
		log.info("Level: " + db.getLevel());
		log.info("ID: " + db.getId());
		GeneralStats gs = db.getGeneralStats();
		List<Stats> stats = db.getStats();
		log.info("Assists: " + gs.getAssists());
		log.info("Höchste Punktzahl: " + gs.getBestScore());
		log.info("Kugeln geschossen: " + gs.getBulletsFired());
		log.info("Kugeln getroffen: " + gs.getBulletsHit());
		log.info("DownButNotOut: " + gs.getDbno());
		log.info("Tode: " + gs.getDeaths());
		log.info("Gadgets zerstört: " + gs.getGadgetsDestroyed());
		log.info("Headshots: " + gs.getHeadshotKills());
		log.info("Kills: " + gs.getKills());
		log.info("Melee Kills: " + gs.getMeleeKills());
		log.info("Runden Verloren: " + gs.getRoundsLost());
		log.info("Runden Gewonnen: " + gs.getRoundsWon());
		log.info("Runden gespielt: " + gs.getRoundsPlayed());
		log.info("Spielmodus: " + gs.getSpielmodus());
		log.info("Selbstmorde: " + gs.getSuicides());
		
		for(Stats st : stats) {
			log.info("--------------");
			log.info("Spielmodus: " + st.getSpielmodus());
			log.info("Runden gewonnen: " + st.getRoundsWon());
			log.info("Runden gespielt: " + st.getRoundsPlayed());
			log.info("Runden verloren: " + st.getRoundsLost());
			log.info("Höchste Punktzahl: " + st.getBestScore());
		}
		
		List<Rank> seasons = db.getSeasons();
		for(Rank r : seasons) {
			log.info("--------------");
			log.info("Season: " + r.getSeason());
			log.info("Rang: " + r.getRang().getName());
		}
		
	}
	/**
	 *Methode um Spieler zu vergleichen
	 * @param player Eine Liste von Spielernamen
	 */
	public List<DBValues> comparePlayers(List<String> player, boolean write) {
		List<DBValues> team = new ArrayList<>();
		for(String p : player) {
			DBValues value = setPlayerValues(getPlayerName(p));
			team.add(value);
			if(write)
				printSpieler(value);
		}
		return team;
	}
	public DBValues getPlayerData(String player) {
		String id = getPlayerName(player);
		return setPlayerValues(id);
	}
}
