package kllngii.r6h.model;

public enum Waffe {
	_556XI("556 XI"),
	L85A2("L85A2"),
	AR33("AR33"),
	//TODO Name der rätselhaften Shotgun ermitteln
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
	AK12("Ak-12"),
	_6P41("6P41"),
	BLSCHILD("Blitz-Schild"),
	
	AUGA2("Aug A2"),
	_552C("552 Commando"),
	CAMRS("CamRS"),
	MK17CQB("MK17-CQB"),

	
	FMG9("FMG9"),
	
	
	P12("P12"),
	SMG11("SMG 11"),
	P2_26("P2.26"),
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
