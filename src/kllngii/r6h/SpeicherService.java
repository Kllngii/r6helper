package kllngii.r6h;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import com.sun.istack.internal.logging.Logger;

import kllngii.r6h.model.R6HelperModel;

/**
 * Service, um das Model {@link R6HelperModel} über den Lauf des Programmes hinaus
 * zu speichern und wieder zu laden.
 * 
 * @author Carsten Kelling
 */
public class SpeicherService {
    
    private final Logger log = Logger.getLogger(getClass());
    
    /**
     * Speichert das übergebene Model in den Java-Preferences, also 
     * auf diesem Computer und für diesen Benutzer eindeutig.
     * Es wird nur eine Version des Models gespeichert, die vorherige wird ohne Nachfrage
     * überschrieben.
     * 
     * @param model  Wird in den Preferences gespeichert.
     */
    public void speichereInPreferences(R6HelperModel model)
    throws IOException {
        log.info("Speichere das Model in den Preferences");
        
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        
        oos.writeObject(model);
        oos.close();
        
        byte[] bytes = bos.toByteArray();
        //TODO Speichern weiter implementieren
        log.info("Das Model hat " + bytes.length + " Bytes.");
    }
    
    /**
     * Gegenstück zu {@link #speichereInPreferences(R6HelperModel)}
     * @return  Das Model, das in den Preferences gespeichert war, oder {@code null}
     */
    public R6HelperModel ladeAusPreferences() {
        //TODO Laden implementieren
        R6HelperModel model = null;
        log.info("Model aus den Preferences geholt: " + model);
        return model;
    }

}
