package kllngii.r6h.model;

import static kllngii.r6h.model.OperatorTyp.*;
import static kllngii.r6h.model.Waffe.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class R6HelperModel {
	
	public static final int MAX_TEAMGRÖSSE = 5;

	private final List<Operator> operatoren;
	private final List<Operator> angreifer;
	private final List<Operator> verteidiger;
	private final List<Operator> selectedAngreifer;
	private final List<Operator> selectedVerteidiger;
	
	
	public R6HelperModel() {
		selectedAngreifer = new ArrayList<>();
		selectedVerteidiger = new ArrayList<>();
		//TODO Gadgets hinzufügen
		//TODO IQ LMG hinzufügen
		
		List<Operator> _o = new ArrayList<>();
//		Angreifer
		_o.add(new Operator(ANGREIFER, "Sledge", L85A2, M590A1, Arrays.asList(SMG11, P2_26)));
		_o.add(new Operator(ANGREIFER, "Thatcher", L85A2, AR33, M590A1, Arrays.asList(P2_26)));
		_o.add(new Operator(ANGREIFER, "Ash", G36C, R4C, Arrays.asList(_57USG, M45_MEUSOC)));
		_o.add(new Operator(ANGREIFER, "Thermite", _556XI, M1014, Arrays.asList(_57USG, M45_MEUSOC)));
		_o.add(new Operator(ANGREIFER, "Twitch", F2, _417, SG_CQB, Arrays.asList(P9, LFP586)));
		
		_o.add(new Operator(ANGREIFER, "Montagne", AUSF_SCHILD, Arrays.asList(P9, LFP586)));
		_o.add(new Operator(ANGREIFER, "Glaz", OTS03, Arrays.asList(GSH18, PMM)));
		_o.add(new Operator(ANGREIFER, "Fuze", AK12, BAL_SCHILD, Arrays.asList(GSH18, PMM)));
		_o.add(new Operator(ANGREIFER, "Blitz", BLSCHILD, Arrays.asList(P12)));
		_o.add(new Operator(ANGREIFER, "IQ", AUGA2, _552C, Arrays.asList(P12)));
		
		_o.add(new Operator(ANGREIFER, "Buck", C8SWF, CAMRS, Arrays.asList(MK19MM)));
		_o.add(new Operator(ANGREIFER, "Blackbeard", MK17CQB, SR25, Arrays.asList(D50)));
		_o.add(new Operator(ANGREIFER, "Capitao", PARA308, M249, Arrays.asList(PRB92)));
		_o.add(new Operator(ANGREIFER, "Hibana", TYPE89, SUPERNOVA, Arrays.asList(P229, BEARING9)));
		_o.add(new Operator(ANGREIFER, "Jackal", C7E, PDW9, ITA12L, Arrays.asList(ITA12S, USP40)));
		//TODO Alle Waffen für Rekruten
		_o.add(new Operator(ANGREIFER, "Rekrut Blau",Arrays.asList(L85A2, M590A1, AR33, G36C, R4C, _556XI, M1014,F2, _417, SG_CQB, AK12, BAL_SCHILD, AUGA2, _552C),  Arrays.asList(SMG11, P2_26)));
		_o.add(new Operator(ANGREIFER, "Rekrut Gelb",Arrays.asList(L85A2, M590A1, AR33, G36C, R4C, _556XI, M1014,F2, _417, SG_CQB, AK12, BAL_SCHILD, AUGA2, _552C), Arrays.asList(SMG11, P2_26)));
		_o.add(new Operator(ANGREIFER, "Rekrut Grün",Arrays.asList(L85A2, M590A1, AR33, G36C, R4C, _556XI, M1014,F2, _417, SG_CQB, AK12, BAL_SCHILD, AUGA2, _552C), Arrays.asList(SMG11, P2_26)));
		_o.add(new Operator(ANGREIFER, "Rekrut Rot", Arrays.asList(L85A2, M590A1, AR33, G36C, R4C, _556XI, M1014,F2, _417, SG_CQB, AK12, BAL_SCHILD, AUGA2, _552C), Arrays.asList(SMG11, P2_26)));
		_o.add(new Operator(ANGREIFER, "Rekrut Orange", Arrays.asList(L85A2, M590A1, AR33, G36C, R4C, _556XI, M1014,F2, _417, SG_CQB, AK12, BAL_SCHILD, AUGA2, _552C), Arrays.asList(SMG11, P2_26)));
		
//		Verteidiger
		_o.add(new Operator(VERTEIDIGER, "Smoke", FMG9, M590A1, Arrays.asList(SMG11, P2_26)));
		_o.add(new Operator(VERTEIDIGER, "Mute", MP5K, M590A1, Arrays.asList(P2_26)));	
		_o.add(new Operator(VERTEIDIGER, "Castle", UMP45, M1014, Arrays.asList(M45_MEUSOC, _57USG)));
		_o.add(new Operator(VERTEIDIGER, "Pulse", UMP45, M1014, Arrays.asList(M45_MEUSOC, _57USG)));
		_o.add(new Operator(VERTEIDIGER, "Doc", P90, MP5, SG_CQB, Arrays.asList(LFP586, P9)));
		
		_o.add(new Operator(VERTEIDIGER, "Rook", P90, MP5, SG_CQB, Arrays.asList(LFP586, P9)));
		_o.add(new Operator(VERTEIDIGER, "Kapkan", SASG12, _9X19VSN, Arrays.asList(GSH18, PMM)));
		_o.add(new Operator(VERTEIDIGER, "Tachanka", SASG12, _9X19VSN, Arrays.asList(GSH18, PMM)));
		_o.add(new Operator(VERTEIDIGER, "Bandit", MP7, M870, Arrays.asList(P12)));
		_o.add(new Operator(VERTEIDIGER, "Jäger", _416, M870, Arrays.asList(P12)));
		
		_o.add(new Operator(VERTEIDIGER, "Frost",_9mmC1, SUPER90, Arrays.asList(MK19MM)));
		_o.add(new Operator(VERTEIDIGER, "Valkyrie",MPX, SPAS12, Arrays.asList(D50)));
		_o.add(new Operator(VERTEIDIGER, "Caveira",M12, SPAS15, Arrays.asList(PRB92)));
		_o.add(new Operator(VERTEIDIGER, "Echo",MP5SD, SUPERNOVA, Arrays.asList(P229, BEARING9)));
		_o.add(new Operator(VERTEIDIGER, "Mira",VECTOR45, ITA12L, Arrays.asList(USP40,ITA12S)));
		
		
		
		operatoren = Collections.unmodifiableList(_o);
		
		angreifer = new ArrayList<>();
		verteidiger = new ArrayList<>();
		for(Operator op:operatoren){
			if(op.getTyp() == ANGREIFER){
				angreifer.add(op);
			}
			else{
				verteidiger.add(op);
			}
		}
		
	}


	public List<Operator> getOperatoren() {
		return operatoren;
	}


	public List<Operator> getAngreifer() {
		return angreifer;
	}

	public List<Operator> getVerteidiger() {
		return verteidiger;
	}
	
	
	
	public List<Operator> getSelectedAngreifer() {
		return selectedAngreifer;
	}


	public List<Operator> getSelectedVerteidiger() {
		return selectedVerteidiger;
	}


	/**
	 * Fügt einen {@link Operator} der Liste der ausgewählten Operatoren
	 * hinzu oder entfernt ihn daraus.
	 * 
	 * 
	 * 
	 */
	public void toggleSelected(Operator op) {
		if(op==null){
			return;
		}
		
		if (op.isAngreifer())
			toggleSelected(selectedAngreifer, op);
		else
			toggleSelected(selectedVerteidiger, op);
		
		System.out.println("Angreifer ausgewählt:    "+selectedAngreifer);
		System.out.println("Verteidigier ausgewählt: "+selectedVerteidiger);

	}


	private void toggleSelected(List<Operator> selected, Operator op) {
		if(selected.contains(op)){
			selected.remove(op);
		}
//		else if(selected.size() >= MAX_TEAMGRÖSSE){
//			throw new IllegalArgumentException("Es dürfen höchstens "+MAX_TEAMGRÖSSE+" Operator gewählt werden.");
//		}
		else{
			selected.add(op);
			
		}
	}
	
}
