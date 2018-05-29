package kllngii.r6h.DB;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import kllngii.r6h.model.Rang;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DBRequest {
	
	private final Logger log = Logger.getLogger(getClass());
	
	public static void main(String[] args) {
		DBRequest requ = new DBRequest();
		String json = requ.getPlayer("Klln911gii");
		requ.setValues(json);
	}
	
	private DBValues setValues(String jsonString) {
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
		
		for(Rank ra : player.getSeasons()) {
			System.out.println("Season: " + ra.getSeason() + " Rang: " + ra.getRang());
		}
		
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
		
		//TODO Die Stats aus JSON holen
		
		return player;
	}

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
			log.info(result);
		}
		catch (IOException ex) {
		    log.warn("Laden der Daten von URL war nicht erfolgreich. Exception=" + ex + ", URL=" + url);
		}
		
		return result;
		
	}
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
			log.info(result);
		}
		catch (IOException ex) {
		    log.warn("Laden der Daten von URL war nicht erfolgreich. Exception=" + ex + ", URL=" + url);
		}
		
		return result;
	}
	
	public String getPlayer(String Playername) {
		JSONArray js = new JSONArray(getSearchDataFromServer("Klln911gii"));
		JSONObject json = js.getJSONObject(0);
		String id = json.getString("id");
		return getPlayerDataFromServer(id);
	}
}
