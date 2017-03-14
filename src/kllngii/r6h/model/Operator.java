package kllngii.r6h.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Operator {
    
    private final OperatorTyp typ;
    private final String name;
    
    private final List<Waffe> primärwaffen;
    private final List<Waffe> sekundärwaffen;
    
	public Operator(OperatorTyp typ, String name, List<Waffe> primärwaffen,
			List<Waffe> sekundärwaffen) {
		super();
		this.typ = typ;
		this.name = name;
		this.primärwaffen = Collections.unmodifiableList(primärwaffen);
		this.sekundärwaffen = Collections.unmodifiableList(sekundärwaffen);
	}
	
	public Operator(OperatorTyp typ, String name, Waffe primärwaffe1,
			List<Waffe> sekundärwaffen) {
		this(typ, name, Arrays.asList(primärwaffe1), sekundärwaffen);
	}
	
	public Operator(OperatorTyp typ, String name, Waffe primärwaffe1, Waffe primärwaffe2,
			List<Waffe> sekundärwaffen) {
		this(typ, name, Arrays.asList(primärwaffe1, primärwaffe2), sekundärwaffen);
	}
	
	public Operator(OperatorTyp typ, String name, Waffe primärwaffe1, Waffe primärwaffe2, Waffe primärwaffe3,
			List<Waffe> sekundärwaffen) {
		this(typ, name, Arrays.asList(primärwaffe1, primärwaffe2, primärwaffe3), sekundärwaffen);
	}


	public OperatorTyp getTyp() {
		return typ;
	}
	
	public boolean isAngreifer() {
		return typ == OperatorTyp.ANGREIFER;
	}

	public String getName() {
		return name;
	}

	public List<Waffe> getPrimärwaffen() {
		return primärwaffen;
	}

	public List<Waffe> getSekundärwaffen() {
		return sekundärwaffen;
	}

	@Override
	public String toString() {
		return name;
	}
    
	
    
    
    
}
