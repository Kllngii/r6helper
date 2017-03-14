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
		
		//TODO Alle Operatoren mit ihre Eigenschaften definieren
		//TODO Rekrut hinzufügen
		List<Operator> _o = new ArrayList<>();
		_o.add(new Operator(ANGREIFER, "Sledge", L85A2, M590A1, Arrays.asList(SMG11, P2_26)));
		_o.add(new Operator(ANGREIFER, "Thatcher", L85A2, AR33, M590A1, Arrays.asList(P2_26)));
		_o.add(new Operator(ANGREIFER, "Ash", G36C, R4C, Arrays.asList(_57USG, M45_MEUSOC)));
		_o.add(new Operator(ANGREIFER, "Thermite", _556XI, M1014, Arrays.asList(_57USG, M45_MEUSOC)));
		_o.add(new Operator(ANGREIFER, "Twitch", F2, _417, SG_CQB, Arrays.asList(P9, LFP586)));
		
		_o.add(new Operator(ANGREIFER, "Montagne", AUSF_SCHILD, Arrays.asList(P9, LFP586)));
		
		
		
		_o.add(new Operator(VERTEIDIGER, "Smoke", FMG9, M590A1, Arrays.asList(SMG11, P2_26)));
					
//			g = "Glaz";
//			h = "Fuze";
//			i = "Blitz";
//			j = "IQ";
//			
//			k = "Buck";
//			l = "Capitao";
//			m = "Hibana";
//			n = "Jackal";
//			o = "Blackbeard";
		
		
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
	/**
	 * Fügt einen {@link Operator} der Liste der ausgewählten Operatoren
	 * hinzu oder entfernt ihn daraus.
	 * 
	 * @throws IllegalArgumentException  Wenn versucht wird, mehr als {@link MAX_TEAMGRÖSSE}
     *                                   Operators im Team zu haben   
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
		else if(selected.size() >= MAX_TEAMGRÖSSE){
			throw new IllegalArgumentException("Es dürfen höchstens "+MAX_TEAMGRÖSSE+" Operator gewählt werden.");
		}
		else{
			selected.add(op);
			
		}
	}
	
}
