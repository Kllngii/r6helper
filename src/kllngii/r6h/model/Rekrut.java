package kllngii.r6h.model;

import static kllngii.r6h.model.Gadget.CLAYMORE;
import static kllngii.r6h.model.Gadget.RAU_GRANATE;
import static kllngii.r6h.model.Gadget.SPL_GRANATE;
import static kllngii.r6h.model.Gadget.SPRENGLADUNG;
import static kllngii.r6h.model.Gadget.STU_GRANATE;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public class Rekrut extends Operator {
	
    private static final long serialVersionUID = -5375903106008752408L;

    public static final int MAX_GADGETS = 2;
	
	private final List<Gadget> selectedGadgets = new ArrayList<>();
	
	public static final EnumSet<Gadget> ANGREIFER_GADGETS = 
			EnumSet.of(SPL_GRANATE, STU_GRANATE, RAU_GRANATE, SPRENGLADUNG, CLAYMORE);
	public static final Set<Gadget> VERTEIDIGER_GADGETS = 
			EnumSet.complementOf(ANGREIFER_GADGETS);

	public Rekrut(OperatorTyp typ, String name, List<Waffe> primärwaffen,
			List<Waffe> sekundärwaffen, Collection<Gadget> gadgets) {
		super(typ, name, primärwaffen, sekundärwaffen, new ArrayList<>(gadgets), Faehigkeit.KEINE);
	}
	
	

	/**
	 * Nicht unterstützt, benutze stattdessen {@link #toggleGadget(String)}
	 * und {@link #getSelectedGadgets()} (Plural!).
     */
 	@Override
	public Gadget getSelectedGadget() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Nicht unterstützt, benutze stattdessen {@link #toggleGadget(String)}
	 * und {@link #getSelectedGadgets()} (Plural!).
	 * 
	 * @param selectedGadget  Ungenutzt
	 */
	@Override
	public void setSelectedGadget(Gadget selectedGadget) {
		throw new UnsupportedOperationException();
	}



	@Override
	public List<Gadget> getSelectedGadgets() {
		return selectedGadgets;
	}
	
	@Override
	public void setSelectedGadgets(List<Gadget> gadgets) {
	    if (gadgets == null)
	        gadgets = new ArrayList<>();
	    selectedGadgets.clear();
	    selectedGadgets.addAll(gadgets);
	}

	public void toggleGadget(String text) {
		Gadget gadget = Gadget.findByName(text);
		if(selectedGadgets.contains(gadget)){
			selectedGadgets.remove(gadget);
		}
		else{
			selectedGadgets.add(gadget);
		}
	}

}
