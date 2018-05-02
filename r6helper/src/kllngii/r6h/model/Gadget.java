package kllngii.r6h.model;

public enum Gadget {
	SPL_GRANATE("Splittergranate"),
	STU_GRANATE("Blendgranate"),
	RAU_GRANATE("Rauchgranate"),
	SPRENGLADUNG("Sprengladung"),
	CLAYMORE("Claymore"),
	
	KON_GRANATE("Kontaktgranate"),
	C4("Nitro-Handy"),
	STA_DRAHT("Stacheldraht"),
	MOBI("Mobiler Schild"),
	//Ab Y3.1.5 neue Kamera
	BP_CAM("Bulletproof Cam")
	;
	
	
	private final String name;
	
	private Gadget(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
	    return name;
	}
	
	public static Gadget findByName(String name) {
	    if (name == null)
	        return null;
	    for (Gadget gadget : Gadget.values()) {
	        if (gadget.getName().equals(name))
	            return gadget;
	    }
	    return null;
	}
}
