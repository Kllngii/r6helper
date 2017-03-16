package kllngii.r6h;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.prefs.Preferences;

import com.sun.istack.internal.logging.Logger;

import kllngii.r6h.model.R6HelperModel;

/**
 * Service, um das Model {@link R6HelperModel} über den Lauf des Programmes hinaus zu speichern und wieder zu laden.
 * 
 * @author Carsten Kelling
 */
public class SpeicherService {

    private static final String _ROOT_KEY = "/kllngii/r6h";
    private static final String MODEL_KEY = "model";

    private final Logger log = Logger.getLogger(getClass());

    private Preferences getRoot() {
        return Preferences.userRoot().node(_ROOT_KEY);
    }

    /**
     * Speichert das übergebene Model in den Java-Preferences, also auf diesem Computer und für diesen Benutzer eindeutig. Es wird
     * nur eine Version des Models gespeichert, die vorherige wird ohne Nachfrage überschrieben.
     * 
     * @param model
     *            Wird in den Preferences gespeichert.
     */
    public void speichereInPreferences(R6HelperModel model) throws IOException {
        log.info("Speichere das Model in den Preferences");

        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(model);
            byte[] bytes = bos.toByteArray();

            getRoot().putByteArray(MODEL_KEY, bytes);
            log.info("Model mit " + bytes.length + " Bytes wurde gespeichert im Node " + getRoot().node(MODEL_KEY));
        }
    }

    /**
     * Gegenstück zu {@link #speichereInPreferences(R6HelperModel)}
     * 
     * @return Das Model, das in den Preferences gespeichert war, oder {@code null}
     */
    public R6HelperModel ladeAusPreferences() throws IOException {
        R6HelperModel model = null;
        byte[] bytes = getRoot().getByteArray(MODEL_KEY, null);
        if (bytes != null) {
            try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
                    ObjectInputStream ois = new ObjectInputStream(bis)) {
                model = (R6HelperModel) ois.readObject();
                log.info("Model wurde aus den Preferences geholt.\nselectedAngreifer: " + model.getSelectedAngreifer()
                        + "\nselectedVerteidiger: " + model.getSelectedVerteidiger());
            } catch (ClassNotFoundException ex) {
                // Sollte nicht vorkommen. Falls doch: Umkippen in IOException
                throw new IOException(ex);
            }
        } else
            log.info("Preferences enthalten noch kein gespeichertes Model");
        return model;
    }

}
