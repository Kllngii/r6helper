package kllngii.r6h.model;

public enum Rang {
	
	KUPFER_IV("Kupfer 4", 1),
	KUPFER_III("Kupfer 3", 2),
	KUPFER_II("Kupfer 2", 3),
	KUPFER_I("Kupfer 1", 4),	
	
	BRONZE_IV("Bronze 4", 5),
	BRONZE_III("Bronze 3", 6),
	BRONZE_II("Bronze 2", 7),
	BRONZE_I("Bronze 1", 8),
	
	SILBER_IV("Silber 4", 9),
	SILBER_III("Silber 3", 10),
	SILBER_II("Silber 2", 11),
	SILBER_I("Silber 1", 12),
	
	GOLD_IV("Gold 4", 13),
	GOLD_III("Gold 3", 14),
	GOLD_II("Gold 2", 15),
	GOLD_I("Gold 1", 16),
	
	PLATIN_III("Platin 3", 17),
	PLATIN_II("Platin 2", 18),
	PLATIN_I("Platin 1", 19),
	
	DIAMANT("Diamant", 20),
	
	NORANK("Kein Rang", 0)
	;
	
	private String name;
	private Integer rang;
	
	Rang(String name, Integer rang) {
		this.name = name;
		this.rang = rang;
	}
	public static Rang getRangWithInt(int i) {
		for(Rang r : Rang.values()) {
			if(r.getRang().equals(i)) {
				return r;
			}
		}
		return null;
	}
	
	public String getName() {
		return name;
	}
	public Integer getRang() {
		return rang;
	}
}
