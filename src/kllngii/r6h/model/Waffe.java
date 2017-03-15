package kllngii.r6h.model;

public enum Waffe {
	
//	Sturmgewehre
	_556XI("556 XI"),
	L85A2("L85A2"),
	AR33("AR33"),
	G36C("G36C"), 
	R4C("R4C"),
	F2("Famas"),
	C8SWF("C8-SWF"),
	AK12("Ak-12"),
	AUGA2("Aug A2"),
	_552C("552 Commando"),
	MK17CQB("MK17-CQB"),
	PARA308("Para-308"),
	TYPE89("Type-89"),
	C7E("C7E"),
	PDW9("PDW9"),
	
//	Shotguns	
	M590A1("M590A1"),
	M1014("M1014"),
	SG_CQB("SG CQB"),
	ITA12L("Ita12 Long"),
	ITA12S("Ita12 Short"),
	SUPERNOVA("SuperNova"),
	SASG12("SASG12"),
	M870("M870"),
	SUPER90("Super90"),
	SPAS12("Spas-12"),
	SPAS15("Spas-15"),
	
//	DMRs	
	_417("417"),
	OTS03("OTS-03"),
	CAMRS("CamRS"),
	SR25("SR-25"),

//	Pistolen u. Revolver
	P9("P9"),
	LFP586("LFP 586"),
	PMM("PMM"),
	GSH18("GSH-18"),
	MK19MM("MK1 9mm"),
	PRB92("Louison"),
	P229("P229"),
	USP40("UsP40"),
	D50("Desert Eagle"),
	P12("P12"),
	P2_26("P226 MK 25"),
	_57USG("57USG"), 
	M45_MEUSOC("M45-MEUSOC"),

//	Reihenfeuerwaffen
	BEARING9("Bearing 9"),
	SMG11("SMG 11"),
	
//	Schilder
	AUSF_SCHILD("Ausfahrbarer Schild"),
	BLSCHILD("Blitz-Schild"),
	BAL_SCHILD("Ballistischer Schild"),
	
//	LMGs
	_6P41("6P41"),
	M249("M249"),
	G8A1("G8A1"),
	
//	MPs
	FMG9("FMG9"),
	MP5K("MP5k"),
	UMP45("UMP45"),
	MP5("MP5"),
	P90("P90"),
	_9X19VSN("9X19VSN"),
	MP7("MP-7"),
	MPX("MP-X"),
	_416("M-416"),
	_9mmC1("9mm-C1"),
	M12("M12"),
	MP5SD("MP5SD"),
	VECTOR45("Vector.45"),
	;
	
	
	private final String name;
	
	private Waffe(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
