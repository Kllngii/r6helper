package kllngii.r6h.model;

import org.json.JSONObject;

public class Spieler {
	private static final String KEY_NAME = "name";
	private static final String KEY_ACE = "ace";
	private static final String KEY_HEADSHOT = "headshot";
	
	private final String name;
	private int hs;
	private int ace;

	/**
	 * Konstruktor für neue Spieler mit Startwerten für die Statistik.
	 */
	public Spieler(String name) {
		this(name, 0, 0);
	}
	
	/**
	 * Konstruktor für Spieler, die schon Stats haben (aus dem JSON kommend).
	 */
	private Spieler(String name, int headshot, int ace) {
		this.name = name;
		this.hs = headshot;
		this.ace = ace;
	}
	
	public static Spieler fromJson(JSONObject src) {
		return new Spieler(src.getString(KEY_NAME), src.optInt(KEY_HEADSHOT, 0), src.optInt(KEY_ACE, 0));
	}
	
	

	public String getName() {
		return name;
	}

	public int getHeadshots() {
		return hs;
	}

	public int getAce() {
		return ace;
	}
	
	
    public JSONObject toJson() {
        JSONObject trg = new JSONObject();
        trg.put(KEY_NAME, getName());
        trg.put(KEY_HEADSHOT, getHeadshots());
        trg.put(KEY_ACE, getAce());
        return trg;
    }
}
