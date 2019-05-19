package kllngii.r6s.stats;

public enum Operator {
	DUMMY("DUMMY", ":I:D", "R6H-ID"),
	SMOKE("Smoke", "2:1", "SAS-SM"),
	MUTE("Mute", "3:1", "SAS-MU"),
	SLEDGE("Sledge", "4:1", "SAS-SL"),
	THATCHER("Thatcher", "5:1", "SAS-TH"),
	CASTLE("Castle", "2:2", "FBI-CA"),
	ASH("Ash", "3:2", "FBI-AS"),
	PULSE("Pulse", "4:2", "FBI-PU"),
	THERMITE("Thermite", "5:2", "FBI-TH"),
	DOC("Doc", "2:3", "GIGN-DO"),
	ROOK("Rook", "3:3", "GIGN-RO"),
	TWITCH("Twitch", "4:3", "GIGN-TW"),
	MONTAGNE("Montagne", "5:3", "GIGN-MO"),
	GLAZ("Glaz", "2:4", "SPETZNAS-GL"),
	;
	
	private final String name, id, r6hid;
	Operator(String name, String id, String r6hid) {
		this.name = name;
		this.id = id;
		this.r6hid = r6hid;
	}
	public Operator getOperatorById(String id) {
		if(id == null || id.trim() == "")
			return DUMMY;
		for(Operator op : values())
			if(op.id == id)
				return op;
		return DUMMY;
	}
	public String getName() {
		return name;
	}
	public String getId() {
		return id;
	}
	public String getR6hid() {
		return r6hid;
	}
	@Override
	public String toString() {
		return this.getName();
	}
	
}
