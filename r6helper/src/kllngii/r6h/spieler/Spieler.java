package kllngii.r6h.spieler;

import org.json.JSONObject;

public class Spieler {
	private static final String KEY_NAME = "name";
	private static final String KEY_ACE = "ace";
	private static final String KEY_HEADSHOT = "headshot";
	private static final String KEY_KNIFE = "knife";
	private static final String KEY_POINTS = "points";
	private static final String KEY_KILLS = "kills";
	private static final String KEY_DEATHS = "deaths";
	private static final String KEY_KD = "kd";
	
	private final String name;
	private String dbname;
	public String getDbname() {
		return dbname;
	}

	public void setDbname(String dbname) {
		this.dbname = dbname;
	}

	private int hs;
	private int ace;
	private int k;
	private Integer p;
	private int kills;
	private int deaths;
	private Double kd;

	/**
	 * Konstruktor für neue Spieler mit Startwerten für die Statistik.
	 */
	public Spieler(String name) {
		this(name, 0, 0, 0, 0, 0, 1, 0.0);
	}
	
	/**
	 * Konstruktor für Spieler, die schon Stats haben (aus dem JSON kommend).
	 */
	private Spieler(String name, int headshot, int ace, int knife, int point, int kills, int deaths, double kd) {
		this.name = name;
		this.hs = headshot;
		this.ace = ace;
		this.k = knife;
		this.p = point;
		this.kills = kills;
		this.deaths = deaths;
		this.kd = kd;
	}
	
	public static Spieler fromJson(JSONObject src) {
		return new Spieler(src.getString(KEY_NAME), src.optInt(KEY_HEADSHOT, 0), src.optInt(KEY_ACE, 0), src.optInt(KEY_KNIFE, 0), src.optInt(KEY_POINTS, 0), src.optInt(KEY_KILLS, 0), src.optInt(KEY_DEATHS, 1), src.optDouble(KEY_KD, 0.0));
	}
	
	
	@Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Spieler other = (Spieler) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        }
        else if (!name.equals(other.name))
            return false;
        return true;
    }
    
    @Override
    public String toString() {
        return name;
    }
    

    public String getName() {
		return name;
	}

	public int getHeadshots() {
		return hs;
	}
	public void increaseHeadshots() {
	    hs++;
	}

	public int getAce() {
		return ace;
	}
	public void increaseAce() {
	    ace++;
	}
	public int getKnifeKills(){
		return k;
	}
	public void increaseKnifeKills() {
		k++;
	}
	
    public JSONObject toJson() {
        JSONObject trg = new JSONObject();
        trg.put(KEY_NAME, getName());
        trg.put(KEY_HEADSHOT, getHeadshots());
        trg.put(KEY_ACE, getAce());
        trg.put(KEY_KNIFE, getKnifeKills());
        trg.put(KEY_POINTS, getPoints());
        trg.put(KEY_KILLS, getKills());
        trg.put(KEY_DEATHS, getDeaths());
        trg.put(KEY_KD, getKD());
        return trg;
    }

	public int getPoints() {
		p = kills*100+ace*750-deaths*75;
		return p;
	}
	public int getKills() {
		return kills;
	}
	public int getDeaths() {
		return deaths;
	}
	public double getKD() {
		kd = 1.0*kills/deaths;
		return kd;
	}
	public void increaseKills() {
		kills++;
	}
	public void increaseDeaths() {
		deaths++;
	}

	public void increaseKills(int i) {
		kills = kills+i;
		
	}
}
