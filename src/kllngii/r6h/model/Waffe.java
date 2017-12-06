package kllngii.r6h.model;


public enum Waffe {
	
//	Sturmgewehre
	_556XI("556 XI", Waffentyp.STURM, Ctu.FBI),
	L85A2("L85A2", Waffentyp.STURM, Ctu.SAS),
	AR33("AR33", Waffentyp.STURM, Ctu.SAS),
	G36C("G36C", Waffentyp.STURM, Ctu.GSG9),
	R4C("R4C", Waffentyp.STURM, Ctu.FBI),
	F2("Famas", Waffentyp.STURM, Ctu.GIGN),
	C8SWF("C8-SWF", Waffentyp.STURM, Ctu.JTF2),
	AK12("Ak-12", Waffentyp.STURM, Ctu.SPETZNAS),
	AUGA2("Aug A2", Waffentyp.STURM, Ctu.GSG9),
	_552C("552 Commando", Waffentyp.STURM, Ctu.GSG9),
	MK17CQB("MK17-CQB", Waffentyp.STURM, Ctu.NAVYSEALS),
	PARA308("Para-308", Waffentyp.STURM, Ctu.BOPE),
	TYPE89("Type-89", Waffentyp.STURM, Ctu.SAT),
	C7E("C7E", Waffentyp.STURM, Ctu.GEO),
	PDW9("PDW9", Waffentyp.STURM, Ctu.GEO),
	MK14EBR("Mk-14EBR", Waffentyp.STURM, Ctu._707SBM),
	M762("M762", Waffentyp.STURM, Ctu.GROM),
//	Shotguns	
	M590A1("M590A1", Waffentyp.SHOTGUN, Ctu.SAS),
	M1014("M1014", Waffentyp.SHOTGUN, Ctu.FBI),
	SG_CQB("SG CQB", Waffentyp.SHOTGUN, Ctu.GIGN),
	ITA12L("Ita12 Long", Waffentyp.SHOTGUN, Ctu.GEO),
	ITA12S("Ita12 Short", Waffentyp.SHOTGUN, Ctu.GEO),
	SUPERNOVA("SuperNova", Waffentyp.SHOTGUN, Ctu.SAT),
	SASG12("SASG12", Waffentyp.SHOTGUN, Ctu.SPETZNAS),
	M870("M870", Waffentyp.SHOTGUN, Ctu.GSG9),
	SUPER90("Super90", Waffentyp.SHOTGUN, Ctu.JTF2),
	SPAS12("Spas-12", Waffentyp.SHOTGUN, Ctu.NAVYSEALS),
	SPAS15("Spas-15", Waffentyp.SHOTGUN, Ctu.BOPE),
	SIX12("Six12", Waffentyp.SHOTGUN, Ctu.SDU),
	SIX12SD("Six12SD", Waffentyp.SHOTGUN, Ctu.SDU),
	FO12("Fo-12", Waffentyp.SHOTGUN, Ctu.GROM),
	BOSG122("BOSG.12.2", Waffentyp.SHOTGUN, Ctu._707SBM),
	
//	DMRs	
	_417("417", Waffentyp.DMR, Ctu.GIGN),
	OTS03("OTS-03", Waffentyp.DMR, Ctu.SPETZNAS),
	CAMRS("CamRS", Waffentyp.DMR, Ctu.JTF2),
	SR25("SR-25", Waffentyp.DMR, Ctu.NAVYSEALS),

//	Pistolen u. Revolver
	P9("P9", Waffentyp.PISTOLE, Ctu.GIGN),
	LFP586("LFP 586", Waffentyp.PISTOLE, Ctu.GIGN),
	PMM("PMM", Waffentyp.PISTOLE, Ctu.SPETZNAS),
	GSH18("GSH-18", Waffentyp.PISTOLE, Ctu.SPETZNAS),
	MK19MM("MK1 9mm", Waffentyp.PISTOLE, Ctu.JTF2),
	PRB92("Louison", Waffentyp.PISTOLE, Ctu.BOPE),
	P229("P229", Waffentyp.PISTOLE, Ctu.SAT),
	USP40("UsP40", Waffentyp.PISTOLE, Ctu.GEO),
	D50("Desert Eagle", Waffentyp.PISTOLE, Ctu.NAVYSEALS),
	P12("P12", Waffentyp.PISTOLE, Ctu.GSG9),
	P2_26("P226 MK 25", Waffentyp.PISTOLE, Ctu.SAS),
	_57USG("57USG", Waffentyp.PISTOLE, Ctu.FBI),
	M45_MEUSOC("M45-MEUSOC", Waffentyp.PISTOLE, Ctu.FBI),
	Q929("Q-929", Waffentyp.PISTOLE, Ctu.SDU),
	RG15("RG15", Waffentyp.PISTOLE, Ctu.GROM),

//	Reihenfeuerwaffen
	BEARING9("Bearing 9", Waffentyp.REIHEN, Ctu.SAT),
	SMG11("SMG 11", Waffentyp.REIHEN, Ctu.SAS),
	C75("C75", Waffentyp.REIHEN, Ctu._707SBM),
	SMG12("SMG-12", Waffentyp.REIHEN, Ctu._707SBM),
	
//	Schilder
	AUSF_SCHILD("Ausfahrbarer Schild", Waffentyp.SCHILD, Ctu.GIGN),
	BLSCHILD("Blitz-Schild", Waffentyp.SCHILD, Ctu.GSG9),
	BAL_SCHILD("Ballistischer Schild", Waffentyp.SCHILD, Ctu.CTU),
	
//	LMGs
	_6P41("6P41", Waffentyp.LMG, Ctu.SPETZNAS),
	M249("M249", Waffentyp.LMG, Ctu.BOPE),
	G8A1("G8A1", Waffentyp.LMG, Ctu.GSG9),
	T95("T-95", Waffentyp.LMG, Ctu.SDU),
	LMGE("LMG-E", Waffentyp.LMG, Ctu.GROM),
	
//	MPs
	FMG9("FMG9", Waffentyp.MP, Ctu.SAS),
	MP5K("MP5k", Waffentyp.MP, Ctu.SAS),
	UMP45("UMP45", Waffentyp.MP, Ctu.FBI),
	MP5("MP5", Waffentyp.MP, Ctu.GIGN),
	P90("P90", Waffentyp.MP, Ctu.GIGN),
	_9X19VSN("9X19VSN", Waffentyp.MP, Ctu.SPETZNAS),
	MP7("MP-7", Waffentyp.MP, Ctu.GSG9),
	MPX("MP-X", Waffentyp.MP,Ctu.NAVYSEALS),
	_416("M-416C", Waffentyp.MP, Ctu.GSG9),
	_9mmC1("9mm-C1", Waffentyp.MP, Ctu.JTF2),
	M12("M12", Waffentyp.MP, Ctu.BOPE),
	MP5SD("MP5SD", Waffentyp.MP, Ctu.SAT),
	VECTOR45("Vector.45", Waffentyp.MP, Ctu.GEO),
	T5SMG("T-5 SMG", Waffentyp.MP, Ctu.SDU),
	SCORPION("Scorpion EVO 3 A1", Waffentyp.MP, Ctu.GROM),
	K1A("K1-A", Waffentyp.MP, Ctu._707SBM),
	;
	
	
    private final Waffentyp typ;
	private final String name;
	private final Ctu c;
	
	
	private Waffe(String name, Waffentyp typ, Ctu c) {
		this.name = name;
		this.typ = typ;
		this.c = c;
		
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

	/**
	 * @return c
	 */
	public Ctu getC() {
		return c;
	}

}
