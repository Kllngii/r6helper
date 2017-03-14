package kllngii.r6h.model;

import static kllngii.r6h.model.OperatorTyp.*;
import static kllngii.r6h.model.Waffe.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class R6HelperModel {

	private final List<Operator> operatoren;
	private final List<Operator> angreifer;
	private final List<Operator> verteidiger;
	
	
	public R6HelperModel() {
		//FIXME Alle Operatoren mit ihre Eigenschaften definieren
		List<Operator> _o = new ArrayList<>();
		
		_o.add(new Operator(ANGREIFER, "Sledge", L85A2, M590A1, Arrays.asList(SMG11, P2_26)));
		_o.add(new Operator(ANGREIFER, "Thatcher", L85A2, AR33, M590A1, Arrays.asList(P2_26)));
		_o.add(new Operator(ANGREIFER, "Ash", G36C, R4C, Arrays.asList(_57USG, M45_MEUSOC)));
		
//		_o.add(new Operator(ANGREIFER, "Thermite";
//			e = "Twitch";
//			
//			f = "Montagne";
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
	
}
