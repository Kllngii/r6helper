package kllngii.r6h;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import org.apache.log4j.Logger;

import kllngii.r6h.model.Einstellungen;

/**
 * Service, um die Einstellungen {@link Einstellungen} über den Lauf des Programmes hinaus zu speichern und wieder zu laden.
 * 
 * @author Carsten Kelling
 */
public class EinstellungenService {
	
    private static final String KEY = "einstellungen";

    private final Logger log = Logger.getLogger(getClass());
    

    private Preferences getRoot() {
        return Preferences.userRoot().node(Konst.PREFERENCES_ROOT_KEY);
    }
    private Preferences getNode() {
        return getRoot().node(KEY);
    }

    /**
     * Speichert die übergebenen Einstellungen in den Java-Preferences, also auf diesem Computer und für diesen Benutzer eindeutig. Es wird
     * nur eine Version des Models gespeichert, die vorherige wird ohne Nachfrage überschrieben.
     * 
     * @param e
     *            Wird in den Preferences gespeichert.
     */
    public void speichereInPreferences(Einstellungen e) {
        log.info("Speichere die Einstellungen in den Preferences");
        
        // Stabiler gegen Änderungen des Models machen -> 
        // einzelne Keys speichern, statt eines serialisierten Objektes
        final Preferences node = getNode();
        
        // -- Lesen
        node.put("uriInput", e.getUriInput().toString());
        node.putBoolean("ftpInput", e.isFtpInput());
        node.putInt("refreshIntervalS", e.getRefreshIntervalS());
        
        // -- Schreiben
        node.put("dateiOutput", e.getUrlOutput());
        node.putBoolean("ftpOutput", e.isFtpOutput());
        node.put("ftpHost", e.getFtpHost());
        node.put("ftpUser", e.getFtpUser());
        node.put("ftpPwd", e.getFtpPwd());
    }

    /**
     * Gegenstück zu {@link #speichereInPreferences(Einstellungen)}
     * 
     * @param e  Wird mit den Einstellungen, die in den Preferences gespeichert waren, initialisiert
     */
    public void ladeAusPreferences(Einstellungen e) throws BackingStoreException {
        
        if (getRoot().nodeExists(KEY)) {
            log.info("Lade die Einstellungen aus den Preferences");
            final Preferences node = getNode();
            
            
            // -- Lesen
            final String fallback = "http://www.example.com";
            URI uriInput;
            try {
                uriInput = new URI(node.get("uriInput", fallback));
            }
            catch (URISyntaxException ex) {
                try {
                    uriInput = new URI(fallback);
                }
                catch (URISyntaxException ex2) {
                    // Kann jetzt nicht mehr auftreten
                    throw new RuntimeException(ex2);
                }
            }
            e.setUriInput(uriInput);
            e.setFtpInput(node.getBoolean("ftpInput", false));
            e.setRefreshIntervalS(node.getInt("refreshIntervalS", Einstellungen.DEFAULT_REFRESH_INTERVAL_S));
            
            
            // -- Schreiben
            e.setUrlOutput(node.get("dateiOutput", "r6helper.json"));
            e.setFtpOutput(node.getBoolean("ftpOutput", false));
            e.setFtpHost(node.get("ftpHost", ""));
            e.setFtpUser(node.get("ftpUser", ""));
            e.setFtpPwd(node.get("ftpPwd", ""));
            log.info(node.absolutePath());
        }
    }
    
 
}
