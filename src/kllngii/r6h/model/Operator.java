package kllngii.r6h.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

public class Operator implements Serializable {

    private static final long serialVersionUID = 6997857670655689520L;
    private static final int DEFAULT_LIFEPOINTS = 100;

    private final OperatorTyp typ;
    private final String name;

    private final List<Waffe> primärwaffen;
    private final List<Waffe> sekundärwaffen;
    private final List<Gadget> gadgets;
    private final Faehigkeit fähigkeit;

    private Waffe selectedPrimärwaffe;
    private Waffe selectedSekundärwaffe;
    private Gadget selectedGadget;
    private int lifepoints = DEFAULT_LIFEPOINTS;
    private int fähigkeitAnzahlÜbrig;
    

    public Operator(OperatorTyp typ, String name, List<Waffe> primärwaffen, List<Waffe> sekundärwaffen,
            List<Gadget> gadgets, Faehigkeit fähigkeit) {
        super();
        this.typ = typ;
        this.name = name;
        this.primärwaffen = Collections.unmodifiableList(primärwaffen);
        this.sekundärwaffen = Collections.unmodifiableList(sekundärwaffen);
        this.gadgets = Collections.unmodifiableList(gadgets);
        
        this.fähigkeit = fähigkeit;
        this.fähigkeitAnzahlÜbrig = fähigkeit.getAnzahl();
    }

    public Operator(OperatorTyp typ, String name, Waffe primärwaffe1, List<Waffe> sekundärwaffen,
            List<Gadget> gadgets, Faehigkeit fähigkeit) {
        this(typ, name, Arrays.asList(primärwaffe1), sekundärwaffen, gadgets, fähigkeit);
    }

    public Operator(OperatorTyp typ, String name, Waffe primärwaffe1, Waffe primärwaffe2, List<Waffe> sekundärwaffen,
            List<Gadget> gadgets, Faehigkeit fähigkeit) {
        this(typ, name, Arrays.asList(primärwaffe1, primärwaffe2), sekundärwaffen, gadgets, fähigkeit);
    }

    public Operator(OperatorTyp typ, String name, Waffe primärwaffe1, Waffe primärwaffe2, Waffe primärwaffe3,
            List<Waffe> sekundärwaffen, List<Gadget> gadgets, Faehigkeit fähigkeit) {
        this(typ, name, Arrays.asList(primärwaffe1, primärwaffe2, primärwaffe3), sekundärwaffen, gadgets, fähigkeit);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((typ == null) ? 0 : typ.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Operator other = (Operator) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (typ != other.typ)
            return false;
        return true;
    }

    public List<Gadget> getGadgets() {
        return gadgets;
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

    public Gadget getSelectedGadget() {
        return selectedGadget;
    }

    public void setSelectedGadget(Gadget selectedGadget) {
        this.selectedGadget = selectedGadget;
    }

    public List<Gadget> getSelectedGadgets() {
        if (selectedGadget == null)
            return Collections.emptyList();
        else
            return Arrays.asList(selectedGadget);
    }

    public void setSelectedGadgets(List<Gadget> gadgets) {
        if (CollectionUtils.isEmpty(gadgets))
            selectedGadget = null;
        else if (gadgets.size() > 1)
            throw new IllegalArgumentException("Operators dürfen nur ein Gadget ausgewählt haben!");
        else
            selectedGadget = gadgets.get(0);
    }

	public int getLifepoints() {
		return lifepoints;
	}

	public void setLifepoints(int lifepoints) {
		this.lifepoints = lifepoints;
	}

	public int getMaxLifepoints() {
		return isAngreifer() ? DEFAULT_LIFEPOINTS : DEFAULT_LIFEPOINTS + 40;
	}
	
	
	
	public Faehigkeit getFähigkeit() {
        return fähigkeit;
    }
	
    public int getFähigkeitAnzahlÜbrig() {
        return fähigkeitAnzahlÜbrig;
    }
    public boolean isFähigkeitÜbrig() {
        return fähigkeitAnzahlÜbrig > 0;
    }
    public void verbraucheFähigkeit() {
        if (isFähigkeitÜbrig())
            fähigkeitAnzahlÜbrig--;
    }

    public void reset() {
	    lifepoints = DEFAULT_LIFEPOINTS;
	    fähigkeitAnzahlÜbrig = fähigkeit.getAnzahl();
	}
}
