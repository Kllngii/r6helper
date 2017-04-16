package kllngii.r6h;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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

    /**
     * Speichert die übergebenen Einstellungen in den Java-Preferences, also auf diesem Computer und für diesen Benutzer eindeutig. Es wird
     * nur eine Version des Models gespeichert, die vorherige wird ohne Nachfrage überschrieben.
     * 
     * @param e
     *            Wird in den Preferences gespeichert.
     */
    public void speichereInPreferences(Einstellungen e) throws IOException {
        log.info("Speichere die Einstellungen in den Preferences");
        
        //FIXME Stabiler gegen Änderungen des Models machen -> einzelne Keys speichern, statt eines serialisierten Objektes

        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(e);
            byte[] bytes = bos.toByteArray();

            getRoot().putByteArray(KEY, bytes);
            log.debug("Einstellungen mit " + bytes.length + " Bytes wurde gespeichert im Node " + getRoot().node(KEY));
        }
    }

    /**
     * Gegenstück zu {@link #speichereInPreferences(Einstellungen)}
     * 
     * @return Die Einstellungen, die in den Preferences gespeichert waren, oder {@code null}
     */
    public Einstellungen ladeAusPreferences() throws IOException {
        Einstellungen e = null;
        byte[] bytes = getRoot().getByteArray(KEY, null);
        if (bytes != null) {
            try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
                    ObjectInputStream ois = new ObjectInputStream(bis)) {
                e = (Einstellungen) ois.readObject();
                log.debug("Einstellungen wurden aus den Preferences geholt.");
            } catch (ClassNotFoundException ex) {
                // Sollte nicht vorkommen. Falls doch: Umkippen in IOException
                throw new IOException(ex);
            }
        } else
            log.info("Einstellungen enthalten noch kein gespeichertes Model");
        return e;
    }
    
 
}
