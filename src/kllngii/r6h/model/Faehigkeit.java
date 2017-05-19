package kllngii.r6h.model;

public enum Faehigkeit {
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
	private final int Anzahl;
	
	private Faehigkeit(int Anzahl){
		this.Anzahl = Anzahl;
	}
	
	
	public int getAnzahl() {
		return Anzahl;
	}
}
