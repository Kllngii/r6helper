package kllngii.r6h.model;

public enum Waffe {
	_556XI("556 XI"),
	L85A2("L85A2")
	;
	
	
	private final String name;
	
	private Waffe(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
