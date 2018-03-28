package kllngii.r6h;

import kllngii.r6h.model.R6HelperModel;
import kllngii.r6h.model.Rekrut;

/**
 * Allgemeine Konstanten für das Projekt R6Helper
 * 
 * @author Carsten Kelling
 */
public abstract class Konst {

    /**
     * Für das Lesen/Schreiben aus den/in die Preferences:
     * Unter diesem Key liegen alle Einstellungen des R6Helper.
     */
    public static final String PREFERENCES_ROOT_KEY = "/kllngii/r6h";
    
    public static final String ERROR_AKTIVIERE_HÖCHSTENS_N_OPERATOR = "Aktiviere höchstens " + R6HelperModel.MAX_TEAMGRÖSSE + " Operator!";
	public static final String ERROR_REKRUT_HAT_ZU_VIELE_GADGETS = "Ein Rekrut darf höchstens " + Rekrut.MAX_GADGETS + " Gadgets haben!";
	public static final String ERROR_REKRUT_PRIWA_SEKWA_CTU = "Ein Rekrut darf nicht die Waffen von mehr als " + Rekrut.MAX_CTUS + " CTUs haben!";
	
}
