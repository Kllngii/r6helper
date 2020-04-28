package kllngii.r6s.stats;

import java.io.IOException;
import java.text.DateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import kllngii.r6s.konst.StatsKonst;
import okhttp3.Authenticator;
import okhttp3.Credentials;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.Route;

@Stateless
public class StatsMarshaller {
	@EJB
	StatsVars statsVars = new StatsVars();
	StatsHelper helper = new StatsHelper();

	private Logger log = Logger.getLogger(getClass());
	private String auth;
	private String loginJson = null;

	public boolean isInitialized() {
		return statsVars.isInitalized();
	}
	public void setInitalized(boolean initalized) {
		statsVars.setInitalized(initalized);
	}
	public void inital() {
		logIn();
		log.info("LoginJSON: " + loginJson);
		JSONObject j = new JSONObject(loginJson);

		if(j.has("ticket") && j.getString("ticket") != null)
			statsVars.setToken(j.getString("ticket"));
		else
			log.warn("Token wurde in der JSON nicht gefunden! LoginJSON=" + loginJson);
		if(j.has("sessionId"))
			statsVars.setUbiSessionId(j.getString("sessionId"));
		else
			log.warn("SessionId wurde in der JSON nicht gefunden! LoginJSON=" + loginJson);
		if(j.has("sessionKey"))
			statsVars.setUbiSessionKey(j.getString("sessionKey"));
		else
			log.warn("SessionKey wurde in der JSON nicht gefunden! LoginJSON=" + loginJson);
		if(j.has("expiration") && j.has("serverTime")) {
			String expString = j.getString("expiration");
			String curString = j.getString("serverTime");
			Instant exp = Instant.parse(expString);
			Instant current = Instant.parse(curString);
			log.info("Ablaufdatum: " + exp.toString());
			log.info("Zeit des Servers: " + current.toString());
			//5min vor Ablauf des Tokens neuen Token anfordern.
			long time = System.currentTimeMillis() + exp.toEpochMilli()-current.toEpochMilli()-300000;
			Executors.newSingleThreadScheduledExecutor().schedule(() -> inital(), time, TimeUnit.MILLISECONDS);
			log.info("Neuer Token wird um " + Instant.ofEpochMilli(time).toString() + " angefordert.");
		}
		log.info(statsVars.getToken());
		auth = "Ubi_v1 t=" + statsVars.getToken();
	}
	public String getStats(int platform, String username, String region) {
		String id = getPlayerID(platform, username);
		return getStatsInternal(platform, id, region);
	}
	private String getStatsInternal(int platform, String id, String region) {
		JSONObject playerJson = new JSONObject();
		playerJson.put("level", new JSONObject(getPlayerLevel(platform, id)));
		JSONArray rankArray = new JSONArray();
		for(int i = 1; i <= StatsKonst.SEASONS.length; i++) {
			String s = getPlayerRank(platform, id, region, i);
			log.info("Season: " + i + s);
			rankArray.put(new JSONObject(s));
		}

		playerJson.put("rank", rankArray);
		playerJson.put("stats-operator-pvp", getListedStats(StatsKonst.statsOperatorPvp, platform, id));
		playerJson.put("stats-general-pvp", getListedStats(StatsKonst.statsGeneralPvp, platform, id));
		playerJson.put("stats-gamemode-bomb", getListedStats(StatsKonst.statsGamemodeBomb, platform, id));
		playerJson.put("stats-gadgets", getListedStats(StatsKonst.statsGadgets, platform, id));
		playerJson.put("time", new Date());
		return playerJson.toString(3);
	}
	private JSONArray getListedStats(String[] list, int platform, String id) {
		JSONArray statsArray = new JSONArray();
		for(int i = 0; i < list.length;) {
			String stat = "";
			if(i < (list.length -3)) {
				stat = String.join(",", list[i], list[i+1],
						list[i+2],
						list[i+3]);
				i += 4;
			}
			else {
				stat = list[i];
				i++;
			}
			statsArray.put(new JSONObject(getPlayerStats(platform, id, stat)));
		}
		return statsArray;
	}
	private void logIn() {
		Builder c = new OkHttpClient.Builder();
		c.authenticator(new Authenticator() {
			@Override
			public Request authenticate(Route route, Response response) throws IOException {
				if (responseCount(response) >= 3) {
					return null;
				}
				//	            String credential = Credentials.basic("user", "password");
				String credential = "Basic Zmlvbm5Aa2VsbGluZy5kZToxTGVvcG9sZA==";
				return response.request().newBuilder().header("Authorization", credential).build();
			}
		});
		c.connectTimeout(10, TimeUnit.SECONDS);
		c.writeTimeout(10, TimeUnit.SECONDS);
		c.readTimeout(30, TimeUnit.SECONDS);

		OkHttpClient cli = c.build();
		final MediaType JSON_TYPE = MediaType.parse(StatsKonst.contentTypeWithCharset);
		RequestBody body = RequestBody.create(JSON_TYPE, "");
		Request request = new Request.Builder()
				.addHeader("Ubi-AppId", StatsKonst.ubiAppId)
				.url(StatsKonst.sessionURL)
				.post(body)
				.build();

		try {
			Response re = cli.newCall(request).execute();
			loginJson = re.body().string();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private int responseCount(Response response) {
		int result = 1;
		while ((response = response.priorResponse()) != null) {
			result++;
		}
		return result;
	}

	private String getPlayerID(int platform, String spieler) {
		String fallback = "3a5c255c-5402-4d8a-b977-43cbfb09c6df";
		OkHttpClient cli = new OkHttpClient();
		Request rq = new Request.Builder()
				.addHeader("Content-Type", StatsKonst.contentType)
				.addHeader("Ubi-AppId", StatsKonst.ubiAppId)
				.addHeader("Ubi-SessionId", statsVars.getUbiSessionId())
				.addHeader("Authorization", auth)
				.url(helper.getIdUrl(platform, "nameOnPlatform", spieler))
				.build();
		try {
			Response rs = cli.newCall(rq).execute();
			JSONObject json = new JSONObject(rs.body().string());
			if(json.has("profiles"))
				return json.getJSONArray("profiles").getJSONObject(0).getString("profileId");
			else {
				log.warn("JSON enthält keinen profiles Array!");
				if(json.has("message"))
					log.warn(json.getString("message"));
				else
					log.warn("JSON: " + json.toString());
				//return Klln911gii's Profile
				return "3a5c255c-5402-4d8a-b977-43cbfb09c6df";
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fallback;
	}
	private String getPlayerLevel(int platform, String id) {
		String fallback = "{}";
		OkHttpClient cli = new OkHttpClient();
		Request rq = new Request.Builder()
				.addHeader("Content-Type", StatsKonst.contentType)
				.addHeader("Ubi-AppId", StatsKonst.ubiAppId)
				.addHeader("Ubi-SessionId", statsVars.getUbiSessionId())
				.addHeader("Authorization", auth)
				.url(helper.getLevelUrl(platform, id))
				.build();
		try {
			Response rs = cli.newCall(rq).execute();
			JSONObject json = new JSONObject(rs.body().string());
			if(json.has("player_profiles"))
				return json.getJSONArray("player_profiles").getJSONObject(0).toString();
			else {
				log.warn("JSON enthält keinen player_profiles Array!");
				if(json.has("message"))
					log.warn(json.getString("message"));
				else
					log.warn("JSON: " + json.toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fallback;
	}
	private String getPlayerStats(int platform, String id, String stat) {
		String fallback = "{}";
		OkHttpClient cli = new OkHttpClient();
		Request rq = new Request.Builder()
				.addHeader("Content-Type", StatsKonst.contentType)
				.addHeader("Ubi-AppId", StatsKonst.ubiAppId)
				.addHeader("Ubi-SessionId", statsVars.getUbiSessionId())
				.addHeader("Authorization", auth)
				.url(helper.getStatsUrl(platform, id, stat))
				.build();
		try {
			Response rs = cli.newCall(rq).execute();
			String s = rs.body().string();
			log.info(stat + ": " + s);
			JSONObject json = new JSONObject(s);
			if(json.has("results"))
				return json.getJSONObject("results").getJSONObject(id).toString();
			else {
				log.warn("JSON enthält kein results Objekt!");
				if(json.has("message"))
					log.warn(json.getString("message"));
				else
					log.warn("JSON: " + json.toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fallback;
	}
	private String getPlayerRank(int platform, String id, String region, int season) {
		String fallback = "{}";
		OkHttpClient cli = new OkHttpClient();
		Request rq = new Request.Builder()
				.addHeader("Content-Type", StatsKonst.contentType)
				.addHeader("Ubi-AppId", StatsKonst.ubiAppId)
				.addHeader("Ubi-SessionId", statsVars.getUbiSessionId())
				.addHeader("Authorization", auth)
				.url(helper.getRankUrl(platform, id, region, season))
				.build();
		try {
			Response rs = cli.newCall(rq).execute();
			JSONObject json = new JSONObject(rs.body().string());
			if(json.has("players"))
				return json.getJSONObject("players").getJSONObject(id).toString(0);
			else {
				log.warn("JSON enthält keinen players Objekt!");
				if(json.has("message"))
					log.warn(json.getString("message"));
				else
					log.warn("JSON: " + json.toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fallback;
	}

}
