package kllngii.r6h.model;

public class Spieler {
	private final String name;
	private final int hs;
	private final int ace;

	public Spieler(String name, int headshot, int ace) {
		this.name = name;
		this.hs = headshot;
		this.ace = ace;
	}

	public String getName() {
		return name;
	}

	public int getHeadshots() {
		return hs;
	}

	public int getAce() {
		return ace;
	}
}
