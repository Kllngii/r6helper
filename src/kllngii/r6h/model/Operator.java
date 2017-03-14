package kllngii.r6h.model;

import java.util.Arrays;
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
	
	public Operator(OperatorTyp typ, String name, Waffe prim�rwaffe1,
			List<Waffe> sekund�rwaffen) {
		this(typ, name, Arrays.asList(prim�rwaffe1), sekund�rwaffen);
	}
	
	public Operator(OperatorTyp typ, String name, Waffe prim�rwaffe1, Waffe prim�rwaffe2,
			List<Waffe> sekund�rwaffen) {
		this(typ, name, Arrays.asList(prim�rwaffe1, prim�rwaffe2), sekund�rwaffen);
	}
	
	public Operator(OperatorTyp typ, String name, Waffe prim�rwaffe1, Waffe prim�rwaffe2, Waffe prim�rwaffe3,
			List<Waffe> sekund�rwaffen) {
		this(typ, name, Arrays.asList(prim�rwaffe1, prim�rwaffe2, prim�rwaffe3), sekund�rwaffen);
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
