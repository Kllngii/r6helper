package kllngii.r6h;

public enum WebTyp {
	R6MAP("R6Maps"),
	R6DB("R6DB"),
	R6HELPER("R6Helper"),
	;
	private String name;
	WebTyp(String name) {
		this.setName(name);
	}
	
	@Override
	public String toString() {
		return name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
