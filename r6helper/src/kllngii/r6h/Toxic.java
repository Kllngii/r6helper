package kllngii.r6h;

import org.json.JSONObject;

public class Toxic {
	private String name;
	private String grund;
	
	public Toxic(String n, String g) {
		this.setName(n);
		this.setGrund(g);
	}
	public String getGrund() {
		return grund;
	}
	public void setGrund(String grund) {
		this.grund = grund;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public static Toxic fromJSON(JSONObject src) {
		return new Toxic(src.getString("name"), src.getString("grund"));
	}
}
