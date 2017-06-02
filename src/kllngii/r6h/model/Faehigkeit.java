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
	BLC(2),
	CAP(4),
	HIB(3),
	JAC(-1),
	
	
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
