package kllngii.r6h.model;


public enum Waffe {
	
//	Sturmgewehre
	_556XI("556 XI", Waffentyp.STURM),
	L85A2("L85A2", Waffentyp.STURM),
	AR33("AR33", Waffentyp.STURM),
	G36C("G36C", Waffentyp.STURM),
	R4C("R4C", Waffentyp.STURM),
	F2("Famas", Waffentyp.STURM),
	C8SWF("C8-SWF", Waffentyp.STURM),
	AK12("Ak-12", Waffentyp.STURM),
	AUGA2("Aug A2", Waffentyp.STURM),
	_552C("552 Commando", Waffentyp.STURM),
	MK17CQB("MK17-CQB", Waffentyp.STURM),
	PARA308("Para-308", Waffentyp.STURM),
	TYPE89("Type-89", Waffentyp.STURM),
	C7E("C7E", Waffentyp.STURM),
	PDW9("PDW9", Waffentyp.STURM),
	
//	Shotguns	
	M590A1("M590A1", Waffentyp.SHOTGUN),
	M1014("M1014", Waffentyp.SHOTGUN),
	SG_CQB("SG CQB", Waffentyp.SHOTGUN),
	ITA12L("Ita12 Long", Waffentyp.SHOTGUN),
	ITA12S("Ita12 Short", Waffentyp.SHOTGUN),
	SUPERNOVA("SuperNova", Waffentyp.SHOTGUN),
	SASG12("SASG12", Waffentyp.SHOTGUN),
	M870("M870", Waffentyp.SHOTGUN),
	SUPER90("Super90", Waffentyp.SHOTGUN),
	SPAS12("Spas-12", Waffentyp.SHOTGUN),
	SPAS15("Spas-15", Waffentyp.SHOTGUN),
	
//	DMRs	
	_417("417", Waffentyp.DMR),
	OTS03("OTS-03", Waffentyp.DMR),
	CAMRS("CamRS", Waffentyp.DMR),
	SR25("SR-25", Waffentyp.DMR),

//	Pistolen u. Revolver
	P9("P9", Waffentyp.PISTOLE),
	LFP586("LFP 586", Waffentyp.PISTOLE),
	PMM("PMM", Waffentyp.PISTOLE),
	GSH18("GSH-18", Waffentyp.PISTOLE),
	MK19MM("MK1 9mm", Waffentyp.PISTOLE),
	PRB92("Louison", Waffentyp.PISTOLE),
	P229("P229", Waffentyp.PISTOLE),
	USP40("UsP40", Waffentyp.PISTOLE),
	D50("Desert Eagle", Waffentyp.PISTOLE),
	P12("P12", Waffentyp.PISTOLE),
	P2_26("P226 MK 25", Waffentyp.PISTOLE),
	_57USG("57USG", Waffentyp.PISTOLE),
	M45_MEUSOC("M45-MEUSOC", Waffentyp.PISTOLE),

//	Reihenfeuerwaffen
	BEARING9("Bearing 9", Waffentyp.REIHEN),
	SMG11("SMG 11", Waffentyp.REIHEN),
	
//	Schilder
	AUSF_SCHILD("Ausfahrbarer Schild", Waffentyp.SCHILD),
	BLSCHILD("Blitz-Schild", Waffentyp.SCHILD),
	BAL_SCHILD("Ballistischer Schild", Waffentyp.SCHILD),
	
//	LMGs
	_6P41("6P41", Waffentyp.LMG),
	M249("M249", Waffentyp.LMG),
	G8A1("G8A1", Waffentyp.LMG),
	
//	MPs
	FMG9("FMG9", Waffentyp.MP),
	MP5K("MP5k", Waffentyp.MP),
	UMP45("UMP45", Waffentyp.MP),
	MP5("MP5", Waffentyp.MP),
	P90("P90", Waffentyp.MP),
	_9X19VSN("9X19VSN", Waffentyp.MP),
	MP7("MP-7", Waffentyp.MP),
	MPX("MP-X", Waffentyp.MP),
	_416("M-416", Waffentyp.MP),
	_9mmC1("9mm-C1", Waffentyp.MP),
	M12("M12", Waffentyp.MP),
	MP5SD("MP5SD", Waffentyp.MP),
	VECTOR45("Vector.45", Waffentyp.MP)
	;
	
	
    private final Waffentyp typ;
	private final String name;
	
	
	private Waffe(String name, Waffentyp typ) {
		this.name = name;
		this.typ = typ;
		
	}

	public String getName() {
		return name;
	}
	
	
	
	public Waffentyp getTyp() {
        return typ;
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
