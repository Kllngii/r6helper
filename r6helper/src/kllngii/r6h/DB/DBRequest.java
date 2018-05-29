package kllngii.r6h.DB;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DBRequest {
	
	private final Logger log = Logger.getLogger(getClass());
	
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
