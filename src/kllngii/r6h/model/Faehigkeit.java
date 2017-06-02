package kllngii.r6h.model;

public enum Faehigkeit {
    /** Für Rekruten */
    KEINE(),
    
	EMP(3),
	ASH(2),
	EXO(2),
	TWI(2),
	FUZ(3),
	BLI(5),
	SKEL(21),
	BLC(2),
	CAP(4),
	HIB(3),
	
	SMK(3),
	MTE(4),
	CSTL(3),
	DOC(3),
	ROOK(5),
	KPKN(3),
	TCHNK(1),
	BNDT(4),
	JGR(3),
	FRST(3),
	BLCKEYE(3),
	YOKAI(1),
	BLCKMIRROR(2),
	
	
	
	
	;
	private final int anzahl;

	/**
	 * Konstruktor für eine Fähigkeit, die sich nicht verbraucht.
	 */
	private Faehigkeit() {
	    this(-1);
	}
	
	/**
	 * Konstruktor für eine Fähigkeit, die sich verbraucht.
	 * @param anzahl  Wie oft die Fähigkeit pro Runde benutzt werden kann
	 */
	private Faehigkeit(int anzahl){
		this.anzahl = anzahl;
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
