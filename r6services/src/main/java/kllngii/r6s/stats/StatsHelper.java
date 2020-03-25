package kllngii.r6s.stats;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import kllngii.r6s.konst.StatsKonst;
/**
 * Eine Klasse, die viele Hilfsfunktionen für die Aufrufe enthält.
 * @author lasse_kelling
 *
 */
public class StatsHelper {
	
	Logger log = Logger.getLogger(getClass());
	/**
	 * 
	 * @param time Das, per JSON empfangene, Ablaufdatum des Keys.
	 * @return Das Ablaufdatum des Keys als {@link Date}
	 */
	public Date parseTimeCode(String time) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
		try {
			return dateFormat.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
			log.error("Fehler beim Umwandeln des Datums! - Fahre mit Standardwerten fort!");
			return new Date(System.currentTimeMillis()+(1000*3600)); // Aktuelles Datum + 1h
		}
	}
	/**
	 * 
	 * @param platform Die ausgewählte Plattform (siehe {@link kllngii.r6s.konst.StatsKonst#PLATFORM_PC PC}
	 * {@link kllngii.r6s.konst.StatsKonst#PLATFORM_PS4 PS4} {@link kllngii.r6s.konst.StatsKonst#PLATFORM_XBOX XBOX})
	 * @param key Entweder <code>nameOnPlatform</code> oder <code>idOnPlatform</code>
	 * @param val Der Name bzw. die ID des Spielers
	 * @return Die URL des Aufrufs
	 */
	public String getIdUrl(int platform, String key, String val) {
		String url = StatsKonst.profileURL;
		switch(platform) {
		case StatsKonst.PLATFORM_PS4:
			url += "psn&";
			break;
		case StatsKonst.PLATFORM_PC:
			url += "uplay&";
			break;
		case StatsKonst.PLATFORM_XBOX:
			url += "xbl&";
			break;
		}
		url = url + key + "=" + val;
		log.info(url);
		return url;
	}
	/**
	 * 
	 * @param platform Die ausgewählte Plattform (siehe {@link kllngii.r6s.konst.StatsKonst#PLATFORM_PC PC}
	 * {@link kllngii.r6s.konst.StatsKonst#PLATFORM_PS4 PS4} {@link kllngii.r6s.konst.StatsKonst#PLATFORM_XBOX XBOX})
	 * @param profileId Die <code>idOnPlatform</code> des Spielers
	 * @return Die URL des Aufrufs
	 */
	public String getLevelUrl(int platform, String profileId) {
		String url = StatsKonst.publicUbiServicesURL;
		switch(platform) {
		case StatsKonst.PLATFORM_PS4:
			url += StatsKonst.NAMESPACE_PS4;
			break;
		case StatsKonst.PLATFORM_PC:
			url += StatsKonst.NAMESPACE_PC;
			break;
		case StatsKonst.PLATFORM_XBOX:
			url += StatsKonst.NAMESPACE_XBOX;
			break;
		}
		url = url + "/r6playerprofile/playerprofile/progressions?profile_ids=" + profileId;
		log.info(url);
		return url;
	}
	/**
	 * 
	 * @param platform Die ausgewählte Plattform (siehe {@link kllngii.r6s.konst.StatsKonst#PLATFORM_PC PC}
	 * {@link kllngii.r6s.konst.StatsKonst#PLATFORM_PS4 PS4} {@link kllngii.r6s.konst.StatsKonst#PLATFORM_XBOX XBOX})
	 * @param profileId Die <code>idOnPlatform</code> des Spielers
	 * @param stat Eine <code>CommaSeperatedList</code> der aufzurufenden Stats
	 * @return Die URL des Aufrufs
	 */
	public String getStatsUrl(int platform, String profileId, String stat) {
		String url = StatsKonst.publicUbiServicesURL;
		switch(platform) {
		case StatsKonst.PLATFORM_PS4:
			url += StatsKonst.NAMESPACE_PS4;
			break;
		case StatsKonst.PLATFORM_PC:
			url += StatsKonst.NAMESPACE_PC;
			break;
		case StatsKonst.PLATFORM_XBOX:
			url += StatsKonst.NAMESPACE_XBOX;
			break;
		}
		url = url + "/playerstats2/statistics?populations=" + profileId + "&statistics=" + stat;
		log.info(url);
		return url;
	}
	/**
	 * 
	 * @param platform Die ausgewählte Plattform (siehe {@link kllngii.r6s.konst.StatsKonst#PLATFORM_PC PC}
	 * {@link kllngii.r6s.konst.StatsKonst#PLATFORM_PS4 PS4} {@link kllngii.r6s.konst.StatsKonst#PLATFORM_XBOX XBOX})
	 * @param profileId Die <code>idOnPlatform</code> des Spielers
	 * @param region Die Region des Spielers (siehe {@link kllngii.r6s.konst.StatsKonst#REGION_EMEA EMEA}
	 * {@link kllngii.r6s.konst.StatsKonst#REGION_NCSA NCSA} {@link kllngii.r6s.konst.StatsKonst#REGION_APAC APAC})
	 * @param season
	 * @return Die URL des Aufrufs
	 */
	public String getRankUrl(int platform, String profileId, String region, int season) {
		String url = StatsKonst.publicUbiServicesURL;
		switch(platform) {
		case StatsKonst.PLATFORM_PS4:
			url += StatsKonst.NAMESPACE_PS4;
			break;
		case StatsKonst.PLATFORM_PC:
			url += StatsKonst.NAMESPACE_PC;
			break;
		case StatsKonst.PLATFORM_XBOX:
			url += StatsKonst.NAMESPACE_XBOX;
			break;
		}
		url = url + "/r6karma/players?board_id=pvp_ranked&profile_ids=" + profileId + "&region_id=" + region + "&season_id=" + season;
		log.info(url);
		return url;
	}
}
