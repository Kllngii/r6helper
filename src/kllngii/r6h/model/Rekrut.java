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
	
	public static final EnumSet<Gadget> ANGREIFER_GADGETS = 
			EnumSet.of(SPL_GRANATE, STU_GRANATE, RAU_GRANATE, SPRENGLADUNG, CLAYMORE);
	public static final Set<Gadget> VERTEIDIGER_GADGETS = 
			EnumSet.complementOf(ANGREIFER_GADGETS);

	public Rekrut(OperatorTyp typ, String name, List<Waffe> primärwaffen,
			List<Waffe> sekundärwaffen, Collection<Gadget> gadgets) {
		super(typ, name, primärwaffen, sekundärwaffen, new ArrayList<>(gadgets));
	}

}
