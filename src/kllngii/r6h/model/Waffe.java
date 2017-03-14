package kllngii.r6h.model;

public enum Waffe {
	_556XI("556 XI"),
	L85A2("L85A2"),
	AR33("AR33"),
	//TODO Name der rätselhaften Shotgun ermitteln
	M590A1("M590A1"),
	G36C("G36C"), 
	R4C("R4C"),
	
	FMG9("FMG9"),
	
	
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
