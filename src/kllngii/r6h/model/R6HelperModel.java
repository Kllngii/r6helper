package kllngii.r6h.model;

import static kllngii.r6h.model.OperatorTyp.*;
import static kllngii.r6h.model.Waffe.*;
import static kllngii.r6h.model.Gadget.*;
import static kllngii.r6h.model.Faehigkeit.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class R6HelperModel implements Serializable {
	
    private static final long serialVersionUID = -2815739085224590803L;
    
    public static final int MAX_TEAMGRÖSSE = 5;
    
    private boolean gegnerteamAngreifer = true;

	private final List<Operator> operatoren;
	private final List<Operator> angreifer;
	private final List<Operator> verteidiger;
	private final List<Operator> selectedAngreifer;
	private final List<Operator> selectedVerteidiger;
	
	
	public R6HelperModel() {
	    gegnerteamAngreifer = true;
	    
		selectedAngreifer = new ArrayList<>();
		selectedVerteidiger = new ArrayList<>();
		
		
		List<Operator> _o = new ArrayList<>();
//		Angreifer
		_o.add(new Operator(ANGREIFER, "Sledge", L85A2, M590A1, Arrays.asList(SMG11, P2_26), Arrays.asList(SPL_GRANATE, STU_GRANATE), KEINE));
		_o.add(new Operator(ANGREIFER, "Thatcher", L85A2, AR33, M590A1, Arrays.asList(P2_26), Arrays.asList(SPRENGLADUNG, CLAYMORE), EMP));
		_o.add(new Operator(ANGREIFER, "Ash", G36C, R4C, Arrays.asList(_57USG, M45_MEUSOC),Arrays.asList(STU_GRANATE, SPRENGLADUNG), ASH));
		_o.add(new Operator(ANGREIFER, "Thermite", _556XI, M1014, Arrays.asList(_57USG, M45_MEUSOC), Arrays.asList(STU_GRANATE, CLAYMORE), EXO));
		_o.add(new Operator(ANGREIFER, "Twitch", F2, _417, SG_CQB, Arrays.asList(P9, LFP586),Arrays.asList(SPRENGLADUNG, CLAYMORE), TWI));
		
		_o.add(new Operator(ANGREIFER, "Montagne", AUSF_SCHILD, Arrays.asList(P9, LFP586), Arrays.asList(STU_GRANATE, RAU_GRANATE), KEINE));
		_o.add(new Operator(ANGREIFER, "Glaz", OTS03, Arrays.asList(GSH18, PMM), Arrays.asList(RAU_GRANATE, CLAYMORE), KEINE));
		_o.add(new Operator(ANGREIFER, "Fuze", AK12, BAL_SCHILD, Arrays.asList(GSH18, PMM),Arrays.asList(SPRENGLADUNG, RAU_GRANATE), FUZ));
		_o.add(new Operator(ANGREIFER, "Blitz", BLSCHILD, Arrays.asList(P12), Arrays.asList(SPRENGLADUNG, RAU_GRANATE), BLI));
		_o.add(new Operator(ANGREIFER, "IQ", AUGA2, _552C, Arrays.asList(P12), Arrays.asList(SPRENGLADUNG, SPL_GRANATE), KEINE));
		
		_o.add(new Operator(ANGREIFER, "Buck", C8SWF, CAMRS, Arrays.asList(MK19MM), Arrays.asList(SPL_GRANATE, STU_GRANATE), SKEL));
		_o.add(new Operator(ANGREIFER, "Blackbeard", MK17CQB, SR25, Arrays.asList(D50), Arrays.asList(STU_GRANATE, SPRENGLADUNG),BLC));
		_o.add(new Operator(ANGREIFER, "Capitao", PARA308, M249, Arrays.asList(PRB92), Arrays.asList(STU_GRANATE, SPRENGLADUNG),CAP));
		_o.add(new Operator(ANGREIFER, "Hibana", TYPE89, SUPERNOVA, Arrays.asList(P229, BEARING9), Arrays.asList(STU_GRANATE, CLAYMORE), HIB));
		_o.add(new Operator(ANGREIFER, "Jackal", C7E, PDW9, ITA12L, Arrays.asList(ITA12S, USP40), Arrays.asList(RAU_GRANATE, SPRENGLADUNG), KEINE));
		
		
		_o.add(new Rekrut(ANGREIFER, "Rekrut Blau",Arrays.asList(L85A2, M590A1, AR33, G36C, R4C, _556XI, M1014,F2, _417, SG_CQB, AK12, BAL_SCHILD, AUGA2, _552C),  Arrays.asList(SMG11, P2_26,_57USG, M45_MEUSOC, P9, LFP586, GSH18, PMM, P12), Rekrut.ANGREIFER_GADGETS));
		_o.add(new Rekrut(ANGREIFER, "Rekrut Gelb",Arrays.asList(L85A2, M590A1, AR33, G36C, R4C, _556XI, M1014,F2, _417, SG_CQB, AK12, BAL_SCHILD, AUGA2, _552C),  Arrays.asList(SMG11, P2_26,_57USG, M45_MEUSOC, P9, LFP586, GSH18, PMM, P12), Rekrut.ANGREIFER_GADGETS));
		_o.add(new Rekrut(ANGREIFER, "Rekrut Orange",Arrays.asList(L85A2, M590A1, AR33, G36C, R4C, _556XI, M1014,F2, _417, SG_CQB, AK12, BAL_SCHILD, AUGA2, _552C),  Arrays.asList(SMG11, P2_26,_57USG, M45_MEUSOC, P9, LFP586, GSH18, PMM, P12), Rekrut.ANGREIFER_GADGETS));
		_o.add(new Rekrut(ANGREIFER, "Rekrut Grün",Arrays.asList(L85A2, M590A1, AR33, G36C, R4C, _556XI, M1014,F2, _417, SG_CQB, AK12, BAL_SCHILD, AUGA2, _552C),  Arrays.asList(SMG11, P2_26,_57USG, M45_MEUSOC, P9, LFP586, GSH18, PMM, P12), Rekrut.ANGREIFER_GADGETS));
		_o.add(new Rekrut(ANGREIFER, "Rekrut Rot",Arrays.asList(L85A2, M590A1, AR33, G36C, R4C, _556XI, M1014,F2, _417, SG_CQB, AK12, BAL_SCHILD, AUGA2, _552C),  Arrays.asList(SMG11, P2_26,_57USG, M45_MEUSOC, P9, LFP586, GSH18, PMM, P12), Rekrut.ANGREIFER_GADGETS));
		
//		Verteidiger
		_o.add(new Operator(VERTEIDIGER, "Smoke", FMG9, M590A1, Arrays.asList(SMG11, P2_26), Arrays.asList(KON_GRANATE, STA_DRAHT), SMK));
		_o.add(new Operator(VERTEIDIGER, "Mute", MP5K, M590A1, Arrays.asList(P2_26), Arrays.asList(MOBI, C4), MTE));	
		_o.add(new Operator(VERTEIDIGER, "Castle", UMP45, M1014, Arrays.asList(M45_MEUSOC, _57USG), Arrays.asList(MOBI,STA_DRAHT), CSTL));
		_o.add(new Operator(VERTEIDIGER, "Pulse", UMP45, M1014, Arrays.asList(M45_MEUSOC, _57USG), Arrays.asList(STA_DRAHT, C4), KEINE));
		_o.add(new Operator(VERTEIDIGER, "Doc", P90, MP5, SG_CQB, Arrays.asList(LFP586, P9), Arrays.asList(MOBI, STA_DRAHT), DOC));
//		
		_o.add(new Operator(VERTEIDIGER, "Rook", P90, MP5, SG_CQB, Arrays.asList(LFP586, P9), Arrays.asList(KON_GRANATE, MOBI), ROOK));
		_o.add(new Operator(VERTEIDIGER, "Kapkan", SASG12, _9X19VSN, Arrays.asList(GSH18, PMM), Arrays.asList(C4, STA_DRAHT), KPKN));
		_o.add(new Operator(VERTEIDIGER, "Tachanka", SASG12, _9X19VSN, Arrays.asList(GSH18, PMM), Arrays.asList(STA_DRAHT, MOBI), TCHNK));
		_o.add(new Operator(VERTEIDIGER, "Bandit", MP7, M870, Arrays.asList(P12), Arrays.asList(STA_DRAHT, C4), BNDT));
		_o.add(new Operator(VERTEIDIGER, "Jäger", _416, M870, Arrays.asList(P12), Arrays.asList(MOBI, STA_DRAHT), JGR));
//		
		_o.add(new Operator(VERTEIDIGER, "Frost",_9mmC1, SUPER90, Arrays.asList(MK19MM), Arrays.asList(MOBI, STA_DRAHT), FRST));
		_o.add(new Operator(VERTEIDIGER, "Valkyrie",MPX, SPAS12, Arrays.asList(D50), Arrays.asList(C4, MOBI), BLCKEYE));
		_o.add(new Operator(VERTEIDIGER, "Caveira",M12, SPAS15, Arrays.asList(PRB92), Arrays.asList(KON_GRANATE, STA_DRAHT), KEINE));
		_o.add(new Operator(VERTEIDIGER, "Echo",MP5SD, SUPERNOVA, Arrays.asList(P229, BEARING9), Arrays.asList(MOBI, STA_DRAHT), YOKAI));
		_o.add(new Operator(VERTEIDIGER, "Mira",VECTOR45, ITA12L, Arrays.asList(USP40,ITA12S),Arrays.asList(C4, MOBI), BLCKMIRROR));
//	
		_o.add(new Rekrut(VERTEIDIGER, "Rekrut Blau", Arrays.asList(FMG9, M590A1, MP5K, UMP45, M1014, P90, MP5, SG_CQB, SASG12, _9X19VSN, MP7, M870, _416), Arrays.asList(SMG11,P2_26,M45_MEUSOC, _57USG, LFP586, P9, GSH18, PMM, P12), Arrays.asList(KON_GRANATE, MOBI, STA_DRAHT, C4)));
		_o.add(new Rekrut(VERTEIDIGER, "Rekrut Gelb", Arrays.asList(FMG9, M590A1, MP5K, UMP45, M1014, P90, MP5, SG_CQB, SASG12, _9X19VSN, MP7, M870, _416), Arrays.asList(SMG11,P2_26,M45_MEUSOC, _57USG, LFP586, P9, GSH18, PMM, P12), Arrays.asList(KON_GRANATE, MOBI, STA_DRAHT, C4)));
		_o.add(new Rekrut(VERTEIDIGER, "Rekrut Grün", Arrays.asList(FMG9, M590A1, MP5K, UMP45, M1014, P90, MP5, SG_CQB, SASG12, _9X19VSN, MP7, M870, _416), Arrays.asList(SMG11,P2_26,M45_MEUSOC, _57USG, LFP586, P9, GSH18, PMM, P12), Arrays.asList(KON_GRANATE, MOBI, STA_DRAHT, C4)));
		_o.add(new Rekrut(VERTEIDIGER, "Rekrut Orange", Arrays.asList(FMG9, M590A1, MP5K, UMP45, M1014, P90, MP5, SG_CQB, SASG12, _9X19VSN, MP7, M870, _416), Arrays.asList(SMG11,P2_26,M45_MEUSOC, _57USG, LFP586, P9, GSH18, PMM, P12), Arrays.asList(KON_GRANATE, MOBI, STA_DRAHT, C4)));
		_o.add(new Rekrut(VERTEIDIGER, "Rekrut Rot", Arrays.asList(FMG9, M590A1, MP5K, UMP45, M1014, P90, MP5, SG_CQB, SASG12, _9X19VSN, MP7, M870, _416), Arrays.asList(SMG11,P2_26,M45_MEUSOC, _57USG, LFP586, P9, GSH18, PMM, P12), Arrays.asList(KON_GRANATE, MOBI, STA_DRAHT, C4)));
		
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
	 */
	public void toggleSelected(Operator op) {
		if(op==null){
			return;
		}
		
		if (op.isAngreifer())
			toggleSelected(selectedAngreifer, op);
		else
			toggleSelected(selectedVerteidiger, op);
		
	}


	private void toggleSelected(List<Operator> selected, Operator op) {
		if(selected.contains(op)){
			selected.remove(op);
		}
		else{
			selected.add(op);
			
		}
	}


    public boolean isGegnerteamAngreifer() {
        return gegnerteamAngreifer;
    }


    public void setGegnerteamAngreifer(boolean gegnerteamAngreifer) {
        this.gegnerteamAngreifer = gegnerteamAngreifer;
    }


    public void reset() {
        for (Operator op : operatoren)
            op.reset();
        
        // Nur nötig für in den Preferences gespeicherte Models, wenn sich die Menge der Ops geändert hat:
        for (Operator op : selectedAngreifer)
            op.reset();
        for (Operator op : selectedVerteidiger)
            op.reset();
    }
}
