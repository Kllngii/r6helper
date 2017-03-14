package kllngii.r6h.model;

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

	public OperatorTyp getTyp() {
		return typ;
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
    
    
    
    
}
