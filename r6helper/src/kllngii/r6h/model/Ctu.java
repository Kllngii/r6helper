package kllngii.r6h.model;

public enum Ctu {
	CTU(-1, -1, "Counter Terrorist Unit"),
	SAS(-1, -1, "Special Air Service"),
	FBI(-1, -1, "Federal Bureau of Investigation"),
	GIGN(-1, -1, "Groupe d’intervention de la gendarmerie nationale"),
	GSG9(-1, -1, "Grenzschutzgruppe 9"),
	SPETZNAS(-1, -1, "Voyska spetsialnovo naznacheniya"),
	
	JTF2(1, 1, "Joint Task Force 2"),
	NAVYSEALS(1, 2, "United States Navy SEALs"),
	BOPE(1, 3, "Batalhão de Operações Policiais Especiais"),
	SAT(1, 4, "Special Assault Team"),
	
	GEO(2, 1, "Grupo Especial de Operaciones"),
	GROM(2, 2, "Jednostka Wojskowa Grom"),
	SDU(2, 3, "Special Duties Unit"),
	_707SBM(2, 4, "707th Special Mission Battalion"),
	
	CBRN(3, 1, "Chemical biological radiological nuclear defense"),
	GIS(3, 2, "Gruppo di Intervento Speciale"),
	GSUTR(3, 3, "Grim Sky Urban Tactical Response Team"),
	GIGR(3, 4, "Groupe d'Intervention de La Gendarmerie Royale"),
	
	SASR(4, 1, "Special Air Service Regiment")
	;
	private int year;
	private int seasonID;
	private String season;
	Ctu(int y, int sid, String s) {
		this.year = y;
		this.seasonID = sid;
		this.season = s;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getSeasonID() {
		return seasonID;
	}
	public void setSeasonID(int seasonID) {
		this.seasonID = seasonID;
	}
	public String getSeason() {
		return season;
	}
	public void setSeason(String season) {
		this.season = season;
	}
	
	
}
