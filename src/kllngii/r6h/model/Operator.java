package kllngii.r6h.model;

import java.util.Collections;
import java.util.List;

public class Operator {
    
    private final OperatorTyp typ;
    private final String name;
    
    private final List<Waffe> prim�rwaffen;
    private final List<Waffe> sekund�rwaffen;
    
	public Operator(OperatorTyp typ, String name, List<Waffe> prim�rwaffen,
			List<Waffe> sekund�rwaffen) {
		super();
		this.typ = typ;
		this.name = name;
		this.prim�rwaffen = Collections.unmodifiableList(prim�rwaffen);
		this.sekund�rwaffen = Collections.unmodifiableList(sekund�rwaffen);
		
	}

	public OperatorTyp getTyp() {
		return typ;
	}

	public String getName() {
		return name;
	}

	public List<Waffe> getPrim�rwaffen() {
		return prim�rwaffen;
	}

	public List<Waffe> getSekund�rwaffen() {
		return sekund�rwaffen;
	}
    
    
    
    
}
