package kllngii.r6h.model;

public enum Waffe {
	
//	Sturmgewehre
	_556XI("556 XI", "STURM"),
	L85A2("L85A2", "STURM"),
	AR33("AR33", "STURM"),
	G36C("G36C", "STURM"), 
	R4C("R4C", "STURM"),
	F2("Famas", "STURM"),
	C8SWF("C8-SWF", "STURM"),
	AK12("Ak-12", "STURM"),
	AUGA2("Aug A2", "STURM"),
	_552C("552 Commando", "STURM"),
	MK17CQB("MK17-CQB", "STURM"),
	PARA308("Para-308", "STURM"),
	TYPE89("Type-89", "STURM"),
	C7E("C7E", "STURM"),
	PDW9("PDW9", "STURM"),
	
//	Shotguns	
	M590A1("M590A1", "SHOTGUN"),
	M1014("M1014", "SHOTGUN"),
	SG_CQB("SG CQB", "SHOTGUN"),
	ITA12L("Ita12 Long", "SHOTGUN"),
	ITA12S("Ita12 Short", "SHOTGUN"),
	SUPERNOVA("SuperNova", "SHOTGUN"),
	SASG12("SASG12", "SHOTGUN"),
	M870("M870", "SHOTGUN"),
	SUPER90("Super90", "SHOTGUN"),
	SPAS12("Spas-12", "SHOTGUN"),
	SPAS15("Spas-15", "SHOTGUN"),
	
//	DMRs	
	_417("417", "DMR"),
	OTS03("OTS-03", "DMR"),
	CAMRS("CamRS", "DMR"),
	SR25("SR-25", "DMR"),

//	Pistolen u. Revolver
	P9("P9", "PISTOLE"),
	LFP586("LFP 586", "PISTOLE"),
	PMM("PMM", "PISTOLE"),
	GSH18("GSH-18", "PISTOLE"),
	MK19MM("MK1 9mm", "PISTOLE"),
	PRB92("Louison", "PISTOLE"),
	P229("P229", "PISTOLE"),
	USP40("UsP40", "PISTOLE"),
	D50("Desert Eagle", "PISTOLE"),
	P12("P12", "PISTOLE"),
	P2_26("P226 MK 25", "PISTOLE"),
	_57USG("57USG", "PISTOLE"), 
	M45_MEUSOC("M45-MEUSOC", "PISTOLE"),

//	Reihenfeuerwaffen
	BEARING9("Bearing 9", "REIHEN"),
	SMG11("SMG 11", "REIHEN"),
	
//	Schilder
	AUSF_SCHILD("Ausfahrbarer Schild", "SCHILD"),
	BLSCHILD("Blitz-Schild", "SCHILD"),
	BAL_SCHILD("Ballistischer Schild", "SCHILD"),
	
//	LMGs
	_6P41("6P41", "LMG"),
	M249("M249", "LMG"),
	G8A1("G8A1", "LMG"),
	
//	MPs
	FMG9("FMG9", "MP"),
	MP5K("MP5k", "MP"),
	UMP45("UMP45", "MP"),
	MP5("MP5", "MP"),
	P90("P90", "MP"),
	_9X19VSN("9X19VSN", "MP"),
	MP7("MP-7", "MP"),
	MPX("MP-X", "MP"),
	_416("M-416", "MP"),
	_9mmC1("9mm-C1", "MP"),
	M12("M12", "MP"),
	MP5SD("MP5SD", "MP"),
	VECTOR45("Vector.45", "MP"),
	;
	
	
	String wTyp;
	
	
	private final String name;
	
	
	private Waffe(String name, String typ) {
		this.name = name;
		this.wTyp = typ;
		
	}

	public String getName() {
		return name;
	}
	
	public String getwTyp() {
		return wTyp;
	}

	public void setwTyp(String wTyp) {
		this.wTyp = wTyp;
	}

	@Override
	public String toString() {
	    return name;
	}
	public static Waffe findByName(String name) {
	    if (name == null)
	        return null;
	    for (Waffe waffe : Waffe.values()) {
	        if (waffe.getName().equals(name))
	            return waffe;
	    }
	    return null;
	}

}
