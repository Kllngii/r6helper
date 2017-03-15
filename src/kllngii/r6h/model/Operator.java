package kllngii.r6h.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Operator {
    
    private final OperatorTyp typ;
    private final String name;
    
    private final List<Waffe> primärwaffen;
    private final List<Waffe> sekundärwaffen;
    
    private Waffe selectedPrimärwaffe;
    private Waffe selectedSekundärwaffe;
    
    
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
	public Operator(OperatorTyp typ, String name, Waffe primärwaffe1, Waffe primärwaffe2, Waffe primärwaffe3,
			Waffe primärwaffe4, Waffe primärwaffe5,
			Waffe primärwaffe6, Waffe primärwaffe7, Waffe primärwaffe8, Waffe primärwaffe9, Waffe primärwaffe10,
			Waffe primärwaffe11, Waffe primärwaffe12, Waffe primärwaffe13, Waffe primärwaffe14,
			List<Waffe> sekundärwaffen) {
		this(typ, name, Arrays.asList(primärwaffe1, primärwaffe2, primärwaffe3, primärwaffe4, primärwaffe5,
				primärwaffe6, primärwaffe7, primärwaffe8, primärwaffe9, primärwaffe10,
				primärwaffe11, primärwaffe12, primärwaffe13, primärwaffe14), sekundärwaffen);
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

    public Waffe getSelectedPrimärwaffe() {
        return selectedPrimärwaffe;
    }

    public void setSelectedPrimärwaffe(Waffe selectedPrimärwaffe) {
        this.selectedPrimärwaffe = selectedPrimärwaffe;
    }

    public Waffe getSelectedSekundärwaffe() {
        return selectedSekundärwaffe;
    }

    public void setSelectedSekundärwaffe(Waffe selectedSekundärwaffe) {
        this.selectedSekundärwaffe = selectedSekundärwaffe;
    }
    
}
