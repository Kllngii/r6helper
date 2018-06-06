package kllngii.r6h.model;

public enum Faehigkeit {
    /** Für Rekruten */
    KEINE(),
    
	EMP(3, "EMP"),
	ASH(2, "Ash-Rakete"),
	EXO(2, "Exotherme-Sprengladung"),
	TWI(2, "Schockdrohne"),
	FUZ(3, "Clustercharge"),
	BLI(4, "Blitzschild"),
	SKEL(21, "Skeletonshotgun"),
	BLC(2, "Waffenschild"),
	CAP(4, "Bolzen"),
	HIB(3, "X-Kairos"),
	ZFA(6, "Doppellauf-Granatwerfer"),
	YNG(3, "Candela"),
	DKB(2, "Handy-Hack"),
	LION(2, "EE-Eins-Drohne"),
	FNKA(3, "Adrenalinschub"),
	
	SMK(3, "Smoke"),
	MTE(4, "Störsender"),
	CSTL(3, "Castle Barrikade"),
	DOC(3, "Heilpistole"),
	ROOK(5, "Rüstung"),
	KPKN(5, "Sprengfalle"),
	TCHNK(1, "Montiertes LMG"),
	BNDT(4, "Schockdraht"),
	JGR(3, "AVS"),
	FRST(3, "Bärenfalle"),
	BLCKEYE(3, "Blackeye"),
	YOKAI(1, "Yokai"),
	BLCKMIRROR(2, "Blackmirror"),
	ELA(3, "GRZMOT"),
	LSN(7, "GU"),
	PRISM(3, "Prisma"),
	EVILEYE(1, "Böses Auge"),
	
	;
	private final int anzahl;
	private final String name;

	/**
	 * Konstruktor für eine Fähigkeit, die sich nicht verbraucht.
	 */
	private Faehigkeit() {
	    this(-1, null);
	}
	
	/**
	 * Konstruktor für eine Fähigkeit, die sich verbraucht.
	 * @param anzahl  Wie oft die Fähigkeit pro Runde benutzt werden kann
	 */
	private Faehigkeit(int anzahl, String name){
		this.anzahl = anzahl;
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	/**
	 * @return  Wie oft die Fähigkeit pro Runde benutzt werden kann, oder -1 bei
	 *          unlimitiertem Gebrauch.
	 */
	public int getAnzahl() {
		return anzahl;
	}
	
	public boolean isAnzahlLimitiert() {
	    return anzahl >= 0;
	}
}
