package kllngii.r6s.konst;

public class StatsKonst {
	public static final String publicUbiServicesURL = "https://public-ubiservices.ubi.com/v1/spaces/";
	public static final String profileURL = "https://public-ubiservices.ubi.com/v3/profiles?platformType=";
	public static final String sessionURL = "https://public-ubiservices.ubi.com/v3/profiles/sessions";
	public static final String contentType = "application/json";
	public static final String contentTypeWithCharset = "application/json; charset=utf-8";
	public static final String ubiAppId = "39baebad-39e5-4552-8c25-2c9b919064e2";
	public static final int PLATFORM_PS4 = 0, PLATFORM_XBOX = 1, PLATFORM_PC = 2;
	public static final String NAMESPACE_PS4 = "05bfb3f7-6c21-4c42-be1f-97a33fb5cf66/sandboxes/OSBOR_PS4_LNCH_A",
			NAMESPACE_PC = "5172a557-50b5-4665-b7db-e3f2e8c5041d/sandboxes/OSBOR_PC_LNCH_A", 
			NAMESPACE_XBOX = "98a601e5-ca91-4440-b1c5-753f601a2c90/sandboxes/OSBOR_XBOXONE_LNCH_A";
	public static final String REGION_EMEA = "emea", REGION_NCSA = "ncsa", REGION_APAC = "apac";
	public static final String[] SEASONS = {
			"Black Ice",
			"Dust Line",
			"Skull Rain",
			"Red Crow",
			"Velvet Shell",
			"Operation Health",
			"Blood Orchid",
			"White Noise",
			"Operation Chimera",
			"Para Bellum",
			"Grim Sky",
			"Wind Bastion",
			"Burnt Horizon"
	};
	public static String statsTerroristHunt[] = {
			"allterrohuntcoop_hard_bestscore",
			"allterrohuntcoop_normal_bestscore",
			"allterrohuntcoop_realistic_bestscore",
			"allterrohuntsolo_hard_bestscore",
			"allterrohuntsolo_normal_bestscore",
			"allterrohuntsolo_realistic_bestscore",
	};
	public static String statsCasual[] = {
			"casualpvp_death",
			"casualpvp_kdratio",
			"casualpvp_kills",
			"casualpvp_matchlost",
			"casualpvp_matchplayed",
			"casualpvp_matchwlratio",
			"casualpvp_matchwon",
			"casualpvp_timeplayed"
	};
	public static String statsCustom[] = {
			"custompvp_timeplayed"
	};
	//TODO Entschlüsseln, welche Kombination welchem Gadget entspricht
	public static String statsGadgets[] = {
			"gadgetpve_chosen,gadgetpve_gadgetdestroy",
			"gadgetpve_kills",
			"gadgetpve_mostused",
			"gadgetpvp_chosen",
			"gadgetpvp_gadgetdestroy",
			"gadgetpvp_kills",
			"gadgetpvp_mostused"
	};
	//TODO Zweck unklar -> evtl. gamemode und operator ersetzen
	@Deprecated
	public static String statsGamemode[] = {
			"gamemodeoperatorpvp_matchlost",
			"gamemodeoperatorpvp_matchplayed",
			"gamemodeoperatorpvp_matchwlratio",
			"gamemodeoperatorpvp_matchwon"
	};
	public static String statsGeneralPve[] = {
			"generalpve_accuracy",
			"generalpve_barricadedeployed",
			"generalpve_blindkills",
			"generalpve_bulletfired",
			"generalpve_bullethit",
			"generalpve_dbno",
			"generalpve_dbnoassists",
			"generalpve_death",
			"generalpve_distancetravelled",
			"generalpve_gadgetdestroy",
			"generalpve_headshot",
			"generalpve_hostagedefense",
			"generalpve_hostagerescue",
			"generalpve_kdratio",
			"generalpve_killassists",
			"generalpve_kills",
			"generalpve_matchlost",
			"generalpve_matchplayed",
			"generalpve_matchwlratio",
			"generalpve_matchwon",
			"generalpve_meleekills",
			"generalpve_penetrationkills",
			"generalpve_rappelbreach",
			"generalpve_reinforcementdeploy",
			"generalpve_revive",
			"generalpve_revivedenied",
			"generalpve_serveraggression",
			"generalpve_serverdefender",
			"generalpve_servershacked",
			"generalpve_suicide",
			"generalpve_timeplayed",
			"generalpve_totalxp"
	};
	public static String statsGeneralPvp[] = {
			"generalpvp_accuracy",
			"generalpvp_barricadedeployed",
			"generalpvp_blindkills",
			"generalpvp_bulletfired",
			"generalpvp_bullethit",
			"generalpvp_dbno",
			"generalpvp_dbnoassists",
			"generalpvp_death",
			"generalpvp_distancetravelled",
			"generalpvp_gadgetdestroy",
			"generalpvp_headshot",
			"generalpvp_hostagedefense",
			"generalpvp_hostagerescue",
			"generalpvp_kdratio",
			"generalpvp_killassists",
			"generalpvp_kills",
			"generalpvp_matchlost",
			"generalpvp_matchplayed",
			"generalpvp_matchwlratio",
			"generalpvp_matchwon",
			"generalpvp_meleekills",
			"generalpvp_penetrationkills",
			"generalpvp_rappelbreach",
			"generalpvp_reinforcementdeploy",
			"generalpvp_revive",
			"generalpvp_revivedenied",
			"generalpvp_serveraggression",
			"generalpvp_serverdefender",
			"generalpvp_servershacked",
			"generalpvp_suicide",
			"generalpvp_timeplayed",
			"generalpvp_totalxp"
	};
	//TODO Braucht Update für die neueren Operator
	public static String statsOperatorPvp[] = {
			"operatorpvp_ash_bonfirekill",
			"operatorpvp_ash_bonfirewallbreached",
			"operatorpvp_bandit_batterykill",
			"operatorpvp_black_mirror_gadget_deployed",
			"operatorpvp_blackbeard_gunshieldblockdamage",
			"operatorpvp_blitz_flashedenemy",
			"operatorpvp_blitz_flashfollowupkills",
			"operatorpvp_blitz_flashshieldassist",
			"operatorpvp_buck_kill",
			"operatorpvp_capitao_lethaldartkills",
			"operatorpvp_capitao_smokedartslaunched",
			"operatorpvp_castle_kevlarbarricadedeployed",
			"operatorpvp_caveira_interrogations",
			"operatorpvp_cazador_assist_kill",
			"operatorpvp_dbno",
			"operatorpvp_death",
			"operatorpvp_doc_hostagerevive",
			"operatorpvp_doc_selfrevive",
			"operatorpvp_doc_teammaterevive",
			"operatorpvp_echo_enemy_sonicburst_affected",
			"operatorpvp_frost_dbno",
			"operatorpvp_fuze_clusterchargekill",
			"operatorpvp_glaz_sniperkill",
			"operatorpvp_glaz_sniperpenetrationkill",
			"operatorpvp_headshot",
			"operatorpvp_hibana_detonate_projectile",
			"operatorpvp_iq_gadgetspotbyef",
			"operatorpvp_jager_gadgetdestroybycatcher",
			"operatorpvp_kapkan_boobytrapdeployed",
			"operatorpvp_kapkan_boobytrapkill",
			"operatorpvp_kdratio",
			"operatorpvp_kills",
			"operatorpvp_meleekills",
			"operatorpvp_montagne_shieldblockdamage",
			"operatorpvp_mostused",
			"operatorpvp_mute_gadgetjammed",
			"operatorpvp_mute_jammerdeployed",
			"operatorpvp_pulse_heartbeatassist",
			"operatorpvp_pulse_heartbeatspot",
			"operatorpvp_rook_armorboxdeployed",
			"operatorpvp_rook_armortakenourself",
			"operatorpvp_rook_armortakenteammate",
			"operatorpvp_roundlost",
			"operatorpvp_roundplayed",
			"operatorpvp_roundwlratio",
			"operatorpvp_roundwon",
			"operatorpvp_sledge_hammerhole",
			"operatorpvp_sledge_hammerkill",
			"operatorpvp_smoke_poisongaskill",
			"operatorpvp_tachanka_turretdeployed",
			"operatorpvp_tachanka_turretkill",
			"operatorpvp_thatcher_gadgetdestroywithemp",
			"operatorpvp_thermite_chargedeployed",
			"operatorpvp_thermite_chargekill",
			"operatorpvp_thermite_reinforcementbreached",
			"operatorpvp_timeplayed",
			"operatorpvp_totalxp",
			"operatorpvp_twitch_gadgetdestroybyshockdrone",
			"operatorpvp_twitch_shockdronekill",
			"operatorpvp_valkyrie_camdeployed"
	};
	public static String statsGamemodeBomb[] = {
			"plantbombpvp_bestscore",
			"plantbombpvp_matchlost",
			"plantbombpvp_matchplayed",
			"plantbombpvp_matchwlratio",
			"plantbombpvp_matchwon",
			"plantbombpvp_timeplayed",
			"plantbombpvp_totalxp"
	};
	//TODO Ungenutzte Stats einordnen
	/*,karma_rank,";
	 * progression_level,progression_renown,progression_xp,"
	+  rankedpvp_death,rankedpvp_kdratio,"
	+ "rankedpvp_kills,rankedpvp_matchlost,rankedpvp_matchplayed,rankedpvp_matchwlratio,rankedpvp_matchwon,"
	+ "rankedpvp_timeplayed,rescuehostagepve_bestscore,rescuehostagepve_hostagedefense,rescuehostagepve_hostagerescue,"
	+ "rescuehostagepve_matchlost,rescuehostagepve_matchplayed,rescuehostagepve_matchwlratio,rescuehostagepve_matchwon,"
	+ "rescuehostagepve_timeplayed,rescuehostagepvp_bestscore,rescuehostagepvp_matchlost,rescuehostagepvp_matchplayed,"
	+ "rescuehostagepvp_matchwlratio,rescuehostagepvp_matchwon,rescuehostagepvp_totalxp,secureareapve_bestscore,"
	+ "secureareapve_matchlost,secureareapve_matchplayed,secureareapve_matchwlratio,secureareapve_matchwon,"
	+ "secureareapve_serveraggression,secureareapve_serverdefender,secureareapve_servershacked,secureareapve_timeplayed,"
	+ "secureareapvp_bestscore,secureareapvp_matchlost,secureareapvp_matchplayed,secureareapvp_matchwlratio,"
	+ "secureareapvp_matchwon,secureareapvp_totalxp,terrohuntclassicpve_bestscore,terrohuntclassicpve_matchlost,"
	+ "terrohuntclassicpve_matchplayed,terrohuntclassicpve_matchwlratio,terrohuntclassicpve_matchwon,"
	+ "terrohuntclassicpve_timeplayed,weaponpve_mostkills,weaponpve_mostused,weaponpvp_mostkills,weaponpvp_mostused,"
	+ "weapontypepve_accuracy,weapontypepve_bulletfired,weapontypepve_bullethit,weapontypepve_chosen,weapontypepve_dbno,"
	+ "weapontypepve_dbnoassists,weapontypepve_death,weapontypepve_deaths,weapontypepve_efficiency,weapontypepve_headshot,"
	+ "weapontypepve_headshotratio,weapontypepve_kdratio,weapontypepve_killassists,weapontypepve_kills,"
	+ "weapontypepve_mostkills,weapontypepve_power,weapontypepvp_accuracy,weapontypepvp_bulletfired,"
	+ "weapontypepvp_bullethit,weapontypepvp_chosen,weapontypepvp_dbno,weapontypepvp_dbnoassists,"
	+ "weapontypepvp_death,weapontypepvp_deaths,weapontypepvp_efficiency,weapontypepvp_headshot,"
	+ "weapontypepvp_headshotratio,weapontypepvp_kdratio,weapontypepvp_killassists,weapontypepvp_kills,"
	+ "weapontypepvp_mostkills,weapontypepvp_power";*/
}
