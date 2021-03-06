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

import kllngii.r6h.spieler.Spieler;

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
		//FIXME Aktuelle Gadgets stimmen nicht! -> Alles nochmal prüfen
		
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
		_o.add(new Operator(ANGREIFER, "Ying", T95, SIX12, Arrays.asList(Q929), Arrays.asList(SPRENGLADUNG, CLAYMORE), YNG, Ctu.SDU));
		_o.add(new Operator(ANGREIFER, "Zofia", Arrays.asList(LMGE, M762), Arrays.asList(RG15), Arrays.asList(SPRENGLADUNG, CLAYMORE), Faehigkeit.ZFA, Ctu.GROM));
		_o.add(new Operator(ANGREIFER, "Dokkaebi", Arrays.asList(BOSG122, MK14EBR), Arrays.asList(C75, SMG12), Arrays.asList(RAU_GRANATE, CLAYMORE), Faehigkeit.DKB, Ctu._707SBM));
		_o.add(new Operator(ANGREIFER, "Lion", Arrays.asList(V308, _417, SG_CQB), Arrays.asList(LFP586, P9), Arrays.asList(STU_GRANATE, CLAYMORE), Faehigkeit.LION, Ctu.CBRN));
		_o.add(new Operator(ANGREIFER, "Finka", Arrays.asList(SPEAR, SASG12, _6P41), Arrays.asList(PMM, GSH18), Arrays.asList(SPRENGLADUNG, SPL_GRANATE), Faehigkeit.FNKA, Ctu.CBRN));
//		
		_o.add(new Operator(ANGREIFER, "Maverick", Arrays.asList(M4, AR1550), Arrays.asList(_1911TACOPS), Arrays.asList(STU_GRANATE, CLAYMORE), Faehigkeit.MVRCK, Ctu.GSUTR));
		_o.add(new Operator(ANGREIFER, "Nomad", Arrays.asList(AK74M, ARX200), Arrays.asList(_44MagSemiAuto), Arrays.asList(CLAYMORE, SPRENGLADUNG), Faehigkeit.NMD, Ctu.GIGR));
		_o.add(new Operator(ANGREIFER, "Gridlock", Arrays.asList(F90, M249), Arrays.asList(SUPERSHORTY, SDP9MM), Arrays.asList(RAU_GRANATE, SPRENGLADUNG), Faehigkeit.GRDLCK, Ctu.SASR));
//		
		_o.add(new Rekrut("Rekrut Blau", 1));
		_o.add(new Rekrut("Rekrut Rot", 1));
		_o.add(new Rekrut("Rekrut Gelb", 1));
		_o.add(new Rekrut("Rekrut Orange", 1));
		_o.add(new Rekrut("Rekrut Grün", 1));
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
		_o.add(new Operator(VERTEIDIGER, "Alibi", Arrays.asList(MX4, ACS12), Arrays.asList(BAILIFF410, KREATOS), Arrays.asList(KON_GRANATE, MOBI), Faehigkeit.PRISM, Ctu.GIS));
		_o.add(new Operator(VERTEIDIGER, "Maestro", Arrays.asList(ALDA556, ACS12), Arrays.asList(BAILIFF410, KREATOS), Arrays.asList(KON_GRANATE, STA_DRAHT), Faehigkeit.EVILEYE, Ctu.GIS));
//		
		_o.add(new Operator(VERTEIDIGER, "Clash", Arrays.asList(ELEK_SCHILD), Arrays.asList(P10C, SPSMG9), Arrays.asList(STA_DRAHT, KON_GRANATE), Faehigkeit.KEINE, Ctu.GSUTR));
		_o.add(new Operator(VERTEIDIGER, "Kaid", Arrays.asList(AUGA3, TCSG12), Arrays.asList(_44MagSemiAuto), Arrays.asList(STA_DRAHT, KON_GRANATE), Faehigkeit.KAID, Ctu.GIGR));
		_o.add(new Operator(VERTEIDIGER, "Mozzie", Arrays.asList(COMMANDO9, P10RONI), Arrays.asList(SUPERSHORTY, SDP9MM), Arrays.asList(STA_DRAHT, C4), Faehigkeit.MZZIE, Ctu.SASR));
		
		_o.add(new Rekrut("Rekrut Blau"));
		_o.add(new Rekrut("Rekrut Rot"));
		_o.add(new Rekrut("Rekrut Orange"));
		_o.add(new Rekrut("Rekrut Gelb"));
		_o.add(new Rekrut("Rekrut Grün"));
		
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
}
