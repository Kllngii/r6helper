package kllngii.r6h.model;

import static kllngii.r6h.model.Gadget.*;
import static kllngii.r6h.model.Waffe.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public class Rekrut extends Operator {
	
    private static final long serialVersionUID = -5375903106008752408L;

    public static final int MAX_GADGETS = 2;
    public static final String MAX_CTUS = "einer";
	
	private final List<Gadget> selectedGadgets = new ArrayList<>();
	
	public static final EnumSet<Gadget> ANGREIFER_GADGETS = 
			EnumSet.of(SPL_GRANATE, STU_GRANATE, RAU_GRANATE, SPRENGLADUNG, CLAYMORE);
	public static final Set<Gadget> VERTEIDIGER_GADGETS = 
			EnumSet.complementOf(ANGREIFER_GADGETS);
	
	@SuppressWarnings("unused")
	//int, da ein Constructor kein if / else haben darf
	public Rekrut(String name, int i) {
		super(OperatorTyp.ANGREIFER, name, Arrays.asList(L85A2, M590A1, AR33, G36C, R4C, _556XI, M1014,F2, _417, SG_CQB, AK12, BAL_SCHILD, AUGA2, _552C), Arrays.asList(SMG11, P2_26,_57USG, M45_MEUSOC, P9, LFP586, GSH18, PMM, P12), new ArrayList<>(Rekrut.ANGREIFER_GADGETS), Faehigkeit.KEINE, Ctu.CTU);
	}
	
	public Rekrut(String name) {
		super(OperatorTyp.VERTEIDIGER, name, Arrays.asList(FMG9, M590A1, MP5K, UMP45, M1014, P90, MP5, SG_CQB, SASG12, _9X19VSN, MP7, M870, _416), Arrays.asList(SMG11,P2_26,M45_MEUSOC, _57USG, LFP586, P9, GSH18, PMM, P12),  Arrays.asList(KON_GRANATE, MOBI, STA_DRAHT, C4), Faehigkeit.KEINE, Ctu.CTU);
	}
	
	public Rekrut(OperatorTyp typ, String name, List<Waffe> primärwaffen,
			List<Waffe> sekundärwaffen, Collection<Gadget> gadgets) {
		super(typ, name, primärwaffen, sekundärwaffen, new ArrayList<>(gadgets), Faehigkeit.KEINE, Ctu.CTU);
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
	@Override
	public boolean isRekrut() {
		return true;
	}

}
