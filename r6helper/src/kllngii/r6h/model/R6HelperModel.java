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

import kllngii.r6h.Toxic;

public class R6HelperModel implements Serializable {
	
    private static final long serialVersionUID = -2815739085224590803L;
    
    public static final int MAX_TEAMGRÖSSE = 5;
    
    private boolean gegnerteamAngreifer = true;
    
    private Integer kamerasZerstört = 0;
    
	private final List<Operator> operatoren;
	private final List<Operator> angreifer;
	private final List<Operator> verteidiger;
	private final List<Operator> selectedAngreifer;
	private final List<Operator> selectedVerteidiger;
	private List<Toxic> spielerToxic;
	
	/** 
	 * Alle Spieler, die mindestens einmal mit uns R6 gespielt haben;
	 * um dauerhaft ihre Namen und Stats aufzubewahren. 
	 */
	private List<Spieler> spielerRepo = new ArrayList<>();
	
	/**
	 * Die momentan aktiv in unserem Team spielenden Spieler.
	 */
	private List<Spieler> team = new ArrayList<>();
	
	
	public R6HelperModel() {
	    gegnerteamAngreifer = true;
	    
		selectedAngreifer = new ArrayList<>();
		selectedVerteidiger = new ArrayList<>();
		spielerToxic = new ArrayList<>();
		
		
		List<Operator> _o = new ArrayList<>();
		//_o.add(new Operator(A/V, "NAME", Arrays.asList(PRIWAs), Arrays.asList(SEKWAs), Arrays.asList(GADGETs), FAEHIGKEIT, Ctu.CTU))
//		Angreifer
		_o.add(new Operator(ANGREIFER, "Sledge", L85A2, M590A1, Arrays.asList(SMG11, P2_26), Arrays.asList(SPL_GRANATE, STU_GRANATE), KEINE, Ctu.SAS));
		_o.add(new Operator(ANGREIFER, "Thatcher", L85A2, AR33, M590A1, Arrays.asList(P2_26), Arrays.asList(SPRENGLADUNG, CLAYMORE), EMP, Ctu.SAS));
		_o.add(new Operator(ANGREIFER, "Ash", G36C, R4C, Arrays.asList(_57USG, M45_MEUSOC),Arrays.asList(STU_GRANATE, SPRENGLADUNG), ASH, Ctu.FBI));
		_o.add(new Operator(ANGREIFER, "Thermite", _556XI, M1014, Arrays.asList(_57USG, M45_MEUSOC), Arrays.asList(STU_GRANATE, CLAYMORE), EXO, Ctu.FBI));
		_o.add(new Operator(ANGREIFER, "Twitch", F2, _417, SG_CQB, Arrays.asList(P9, LFP586),Arrays.asList(SPRENGLADUNG, CLAYMORE), TWI, Ctu.GIGN));
//		
		_o.add(new Operator(ANGREIFER, "Montagne", AUSF_SCHILD, Arrays.asList(P9, LFP586), Arrays.asList(STU_GRANATE, RAU_GRANATE), KEINE, Ctu.GIGN));
		_o.add(new Operator(ANGREIFER, "Glaz", OTS03, Arrays.asList(GSH18, PMM), Arrays.asList(RAU_GRANATE, CLAYMORE), KEINE, Ctu.SPETZNAS));
		_o.add(new Operator(ANGREIFER, "Fuze", AK12, BAL_SCHILD, Arrays.asList(GSH18, PMM),Arrays.asList(SPRENGLADUNG, RAU_GRANATE), FUZ, Ctu.SPETZNAS));
		_o.add(new Operator(ANGREIFER, "Blitz", BLSCHILD, Arrays.asList(P12), Arrays.asList(SPRENGLADUNG, RAU_GRANATE), BLI, Ctu.GSG9));
		_o.add(new Operator(ANGREIFER, "IQ", AUGA2, _552C, Arrays.asList(P12), Arrays.asList(SPRENGLADUNG, SPL_GRANATE), KEINE, Ctu.GSG9));
//		
		_o.add(new Operator(ANGREIFER, "Buck", C8SWF, CAMRS, Arrays.asList(MK19MM), Arrays.asList(SPL_GRANATE, STU_GRANATE), SKEL, Ctu.JTF2));
		_o.add(new Operator(ANGREIFER, "Blackbeard", MK17CQB, SR25, Arrays.asList(D50), Arrays.asList(STU_GRANATE, SPRENGLADUNG),BLC, Ctu.NAVYSEALS));
		_o.add(new Operator(ANGREIFER, "Capitao", PARA308, M249, Arrays.asList(PRB92), Arrays.asList(STU_GRANATE, SPRENGLADUNG),CAP, Ctu.BOPE));
		_o.add(new Operator(ANGREIFER, "Hibana", TYPE89, SUPERNOVA, Arrays.asList(P229, BEARING9), Arrays.asList(STU_GRANATE, SPRENGLADUNG), Faehigkeit.HIB, Ctu.SAT));
		_o.add(new Operator(ANGREIFER, "Jackal", C7E, PDW9, ITA12L, Arrays.asList(ITA12S, USP40), Arrays.asList(RAU_GRANATE, SPRENGLADUNG), KEINE, Ctu.GEO));
//		
		_o.add(new Operator(ANGREIFER, "Ying", T95, SIX12, Arrays.asList(Q929), Arrays.asList(RAU_GRANATE, CLAYMORE), YNG, Ctu.SDU));
		_o.add(new Operator(ANGREIFER, "Zofia", Arrays.asList(LMGE, M762), Arrays.asList(RG15), Arrays.asList(SPRENGLADUNG, CLAYMORE), Faehigkeit.ZFA, Ctu.GROM));
		_o.add(new Operator(ANGREIFER, "Dokkaebi", Arrays.asList(BOSG122, MK14EBR), Arrays.asList(C75, SMG12), Arrays.asList(RAU_GRANATE, CLAYMORE), Faehigkeit.DKB, Ctu._707SBM));
		_o.add(new Operator(ANGREIFER, "Lion", Arrays.asList(V308, _417, SG_CQB), Arrays.asList(LFP586, P9), Arrays.asList(), Faehigkeit.LION, Ctu.CBRN));
		_o.add(new Operator(ANGREIFER, "Finka", Arrays.asList(SPEAR, SASG12, _6P41), Arrays.asList(PMM, GSH18), Arrays.asList(), Faehigkeit.FNKA, Ctu.CBRN));
//		
		_o.add(new Rekrut(ANGREIFER, "Rekrut Blau",Arrays.asList(L85A2, M590A1, AR33, G36C, R4C, _556XI, M1014,F2, _417, SG_CQB, AK12, BAL_SCHILD, AUGA2, _552C),  Arrays.asList(SMG11, P2_26,_57USG, M45_MEUSOC, P9, LFP586, GSH18, PMM, P12), Rekrut.ANGREIFER_GADGETS));
		_o.add(new Rekrut(ANGREIFER, "Rekrut Gelb",Arrays.asList(L85A2, M590A1, AR33, G36C, R4C, _556XI, M1014,F2, _417, SG_CQB, AK12, BAL_SCHILD, AUGA2, _552C),  Arrays.asList(SMG11, P2_26,_57USG, M45_MEUSOC, P9, LFP586, GSH18, PMM, P12), Rekrut.ANGREIFER_GADGETS));
		_o.add(new Rekrut(ANGREIFER, "Rekrut Orange",Arrays.asList(L85A2, M590A1, AR33, G36C, R4C, _556XI, M1014,F2, _417, SG_CQB, AK12, BAL_SCHILD, AUGA2, _552C),  Arrays.asList(SMG11, P2_26,_57USG, M45_MEUSOC, P9, LFP586, GSH18, PMM, P12), Rekrut.ANGREIFER_GADGETS));
		_o.add(new Rekrut(ANGREIFER, "Rekrut Grün",Arrays.asList(L85A2, M590A1, AR33, G36C, R4C, _556XI, M1014,F2, _417, SG_CQB, AK12, BAL_SCHILD, AUGA2, _552C),  Arrays.asList(SMG11, P2_26,_57USG, M45_MEUSOC, P9, LFP586, GSH18, PMM, P12), Rekrut.ANGREIFER_GADGETS));
		_o.add(new Rekrut(ANGREIFER, "Rekrut Rot",Arrays.asList(L85A2, M590A1, AR33, G36C, R4C, _556XI, M1014,F2, _417, SG_CQB, AK12, BAL_SCHILD, AUGA2, _552C),  Arrays.asList(SMG11, P2_26,_57USG, M45_MEUSOC, P9, LFP586, GSH18, PMM, P12), Rekrut.ANGREIFER_GADGETS));
		
//		Verteidiger
		//Doc, Mute, Castle, Vigil, Caveira bekommen BP_Cam
		_o.add(new Operator(VERTEIDIGER, "Smoke", FMG9, M590A1, Arrays.asList(SMG11, P2_26), Arrays.asList(KON_GRANATE, STA_DRAHT), SMK, Ctu.SAS));
		_o.add(new Operator(VERTEIDIGER, "Mute", MP5K, M590A1, Arrays.asList(P2_26), Arrays.asList(MOBI, C4), MTE, Ctu.SAS));	
		_o.add(new Operator(VERTEIDIGER, "Castle", UMP45, M1014, Arrays.asList(M45_MEUSOC, _57USG), Arrays.asList(MOBI,STA_DRAHT), CSTL, Ctu.FBI));
		_o.add(new Operator(VERTEIDIGER, "Pulse", UMP45, M1014, Arrays.asList(M45_MEUSOC, _57USG), Arrays.asList(STA_DRAHT, C4), KEINE, Ctu.FBI));
		_o.add(new Operator(VERTEIDIGER, "Doc", P90, MP5, SG_CQB, Arrays.asList(LFP586, P9), Arrays.asList(MOBI, STA_DRAHT), DOC, Ctu.GIGN));
//		
		_o.add(new Operator(VERTEIDIGER, "Rook", P90, MP5, SG_CQB, Arrays.asList(LFP586, P9), Arrays.asList(KON_GRANATE, MOBI), ROOK, Ctu.GIGN));
		_o.add(new Operator(VERTEIDIGER, "Kapkan", SASG12, _9X19VSN, Arrays.asList(GSH18, PMM), Arrays.asList(C4, STA_DRAHT), KPKN, Ctu.SPETZNAS));
		_o.add(new Operator(VERTEIDIGER, "Tachanka", SASG12, _9X19VSN, Arrays.asList(GSH18, PMM), Arrays.asList(STA_DRAHT, MOBI), TCHNK, Ctu.SPETZNAS));
		_o.add(new Operator(VERTEIDIGER, "Bandit", MP7, M870, Arrays.asList(P12), Arrays.asList(STA_DRAHT, C4), BNDT, Ctu.GSG9));
		_o.add(new Operator(VERTEIDIGER, "Jäger", _416, M870, Arrays.asList(P12), Arrays.asList(MOBI, STA_DRAHT), JGR, Ctu.GSG9));
//		
		_o.add(new Operator(VERTEIDIGER, "Frost",_9mmC1, SUPER90, Arrays.asList(MK19MM), Arrays.asList(MOBI, STA_DRAHT), FRST, Ctu.JTF2));
		_o.add(new Operator(VERTEIDIGER, "Valkyrie",MPX, SPAS12, Arrays.asList(D50), Arrays.asList(C4, MOBI), BLCKEYE, Ctu.NAVYSEALS));
		_o.add(new Operator(VERTEIDIGER, "Caveira",M12, SPAS15, Arrays.asList(PRB92), Arrays.asList(KON_GRANATE, STA_DRAHT), KEINE, Ctu.BOPE));
		_o.add(new Operator(VERTEIDIGER, "Echo",MP5SD, SUPERNOVA, Arrays.asList(P229, BEARING9), Arrays.asList(MOBI, STA_DRAHT), YOKAI, Ctu.SAT));
		_o.add(new Operator(VERTEIDIGER, "Mira",VECTOR45, ITA12L, Arrays.asList(USP40,ITA12S),Arrays.asList(C4, MOBI), BLCKMIRROR, Ctu.GEO));
//		
		_o.add(new Operator(VERTEIDIGER, "Lesion", T5SMG, SIX12SD, Arrays.asList(Q929), Arrays.asList(KON_GRANATE, STA_DRAHT), LSN, Ctu.SDU));
		_o.add(new Operator(VERTEIDIGER, "Ela", SCORPION, FO12, Arrays.asList(RG15), Arrays.asList(KON_GRANATE, STA_DRAHT), ELA, Ctu.GROM));
		_o.add(new Operator(VERTEIDIGER, "Vigil", Arrays.asList(BOSG122, K1A), Arrays.asList(SMG12, C75), Arrays.asList(KON_GRANATE, STA_DRAHT), Faehigkeit.KEINE, Ctu._707SBM));
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
	
	public List<Spieler> getSpielerRepo() {
        return spielerRepo;
    }
    public List<Spieler> getTeam() {
        return team;
    }
    public void setSpielerRepo(List<Spieler> spielerRepo) {
		this.spielerRepo = spielerRepo;
	}

	public void setTeam(List<Spieler> team) {
		this.team = team;
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
        setKamerasZerstört(0);
    }

	public Integer getKamerasZerstört() {
		return kamerasZerstört;
	}

	public void setKamerasZerstört(Integer kamerasZerstört) {
		this.kamerasZerstört = kamerasZerstört;
	}
	public void increaseKamerasZerstört() {
		kamerasZerstört++;
	}

	public List<Toxic> getToxic() {
		return spielerToxic;
	}

	public void setToxic(List<Toxic> spielerToxic) {
		this.spielerToxic = spielerToxic;
	}
}
