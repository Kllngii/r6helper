package kllngii.r6h.model;

public enum Waffe {
	_556XI("556 XI"),
	L85A2("L85A2"),
	AR33("AR33"),
	M590A1("M590A1"),
	G36C("G36C"), 
	R4C("R4C"),
	M1014("M1014"),
	F2("Famas"),
	_417("417"),
	SG_CQB("SG CQB"),
	P9("P9"),
	LFP586("LFP 586"),
	AUSF_SCHILD("Ausfahrbarer Schild"),
	OTS03("OTS-03"),
	PMM("PMM"),
	GSH18("GSH-18"),
	C8SWF("C8-SWF"),
	MK19MM("MK1 9mm"),
	AK12("Ak-12"),
	_6P41("6P41"),
	BLSCHILD("Blitz-Schild"),
	SR25("SR-25"),
	AUGA2("Aug A2"),
	_552C("552 Commando"),
	CAMRS("CamRS"),
	MK17CQB("MK17-CQB"),
	D50("Desert Eagle"),
	PARA308("Para-308"),
	FMG9("FMG9"),
	M249("M249"),
	PRB92("Louison"),
	TYPE89("Type-89"),
	SUPERNOVA("SuperNova"),
	P229("P229"),
	BEARING9("Bearing 9"),
	C7E("C7E"),
	PDW9("PDW9"),
	ITA12L("Ita12 Long"),
	ITA12S("Ita12 Short"),
	USP40("UsP40"),
	MP5K("MP5k"),
	UMP45("UMP45"),
	MP5("MP5"),
	P90("P90"),
	BAL_SCHILD("Ballistischer Schild"),
	P12("P12"),
	SMG11("SMG 11"),
	P2_26("P226 MK 25"),
	_57USG("57USG"), 
	M45_MEUSOC("M45-MEUSOC")
	;
	
	
	private final String name;
	
	private Waffe(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
