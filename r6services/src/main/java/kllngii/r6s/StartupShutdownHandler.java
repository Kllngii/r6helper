package kllngii.r6s;

import java.io.IOException;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.PostUpdate;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Startup
@Singleton
public class StartupShutdownHandler {
	Logger log = Logger.getLogger(getClass());
	@PostUpdate
	@PostConstruct
	public void onStartUp() {
		log.info("Starte R6Service!");
		ScheduledThreadPoolExecutor exec = new ScheduledThreadPoolExecutor(1);
		exec.scheduleAtFixedRate(() -> {
			//TODO HttpRequest an Server
			OkHttpClient cli = new OkHttpClient();
			Request r = new Request.Builder()
					.url("localhost:8080/r6/service/stats/initial")
					.addHeader("auth", "B%8zA+EAJ@D$3$A_VSR-_PX?5QUc&=NUc*6YHPH&9ns#7YqUhh")
					.build();
			try {
				Response resp = cli.newCall(r).execute();
				String s = resp.body().string();
				JSONObject json = new JSONObject(s);
				if(json.has("ERROR"))
					log.error("Fehler beim Erneuern des Passworts!");
			} catch (IOException e) {
				log.error("Fehler beim Erneuern des Passworts!");
				e.printStackTrace();
			}
		}, 0, 1, TimeUnit.HOURS);
	}
	@PreDestroy
	public void onShutdown() {
		log.debug("Stoppe R6Service!");
	}
}
