package kllngii.r6h;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.prefs.Preferences;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import kllngii.r6h.model.Gadget;
import kllngii.r6h.model.Operator;
import kllngii.r6h.model.R6HelperModel;
import kllngii.r6h.model.Waffe;

/**
 * Service, um das Model {@link R6HelperModel} über den Lauf des Programmes hinaus zu speichern und wieder zu laden.
 * 
 * @author Carsten Kelling
 */
public class SpeicherService {

    private static final String MODEL_KEY = "model";

    private final Logger log = Logger.getLogger(getClass());
    
    
    public static class ModelWithErrors {
    	private final R6HelperModel model;
    	private final Collection<String> errors;
    	
		public ModelWithErrors(R6HelperModel model, Collection<String> errors) {
			super();
			this.model = model;
			this.errors = errors;
		}
		public R6HelperModel getModel() {
			return model;
		}
		public Collection<String> getErrors() {
			return errors;
		}
    }

    private Preferences getRoot() {
        return Preferences.userRoot().node(Konst.PREFERENCES_ROOT_KEY);
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
    
    
    /**
     * Wandelt ein {@link R6HelperModel} (den Zustand dieser Applikation) in einen JSON-String um.
     */
    public String createJson(R6HelperModel model, Collection<String> errors) {
        JSONObject json = new JSONObject();
        
        json.put("gegnerteamAngreifer", model.isGegnerteamAngreifer());
        final List<Operator> gegnerteam = model.isGegnerteamAngreifer() ? model.getSelectedAngreifer() : model.getSelectedVerteidiger();
        JSONArray geg = new JSONArray();
        for (Operator gegner : gegnerteam)
            geg.put(toJson(gegner));
        json.put("gegnerteam", geg);
        
        if (CollectionUtils.isNotEmpty(errors)) {
            JSONArray fehler = new JSONArray();
            for (String error : errors)
                fehler.put(error);
            json.put("fehler", fehler);
        }
        
        String result = json.toString(4);
        log.info("JSON:\n" + result);
        return result;
    }
    
    /**
     * Setzt JSON wieder in ein {@link R6HelperModel} um.
     */
    private R6HelperModel getModel(JSONObject json) {
        R6HelperModel model = new R6HelperModel();
        
        model.setGegnerteamAngreifer(json.getBoolean("gegnerteamAngreifer"));
        
        if (json.has("gegnerteam")) {
            for (Object gegnerObject : json.getJSONArray("gegnerteam")) {
                Operator gegner = operator(model, (JSONObject) gegnerObject);
                model.toggleSelected(gegner);
            }
        }
        
        return model;
    }
    
    private Operator operator(R6HelperModel model, JSONObject json) {
        String opName = json.getString("name");
        
        // Operator zum Namen finden:
        Operator op = null;
        for (Operator _o : model.getOperatoren()) {
            if (_o.getName().equals(opName)) {
                op = _o;
                break;
            }
        }
        if (op == null)
            throw new IllegalArgumentException("Unbekannter Operator '" + opName + "'");
        
        if (json.has("primärwaffe")) {
            String name = json.getJSONObject("primärwaffe").getString("name");
            Waffe waffe = Waffe.findByName(name);
            if (waffe == null)
                throw new IllegalArgumentException("Unbekannte Primärwaffe '" + name + "'");
            op.setSelectedPrimärwaffe(waffe);
        }
        if (json.has("sekundärwaffe")) {
            String name = json.getJSONObject("sekundärwaffe").getString("name");
            Waffe waffe = Waffe.findByName(name);
            if (waffe == null)
                throw new IllegalArgumentException("Unbekannte Sekundärwaffe '" + name + "'");
            op.setSelectedSekundärwaffe(waffe);
        }
        if (json.has("gadgets")) {
            JSONArray gadgetList = json.getJSONArray("gadgets");
            List<Gadget> selectedGadgets = new ArrayList<>();
            for (int i = 0; i < gadgetList.length(); i++) {
                String name = gadgetList.getString(i);
                Gadget gadget = Gadget.findByName(name);
                if (gadget == null)
                    throw new IllegalArgumentException("Unbekanntes Gadget '" + name + "'");
                selectedGadgets.add(gadget);
            }
            op.setSelectedGadgets(selectedGadgets);
        }
        
        return op;
    }
    
    private JSONObject toJson(Operator op) {
        JSONObject trg = new JSONObject();
        trg.put("name", op.getName());
        if (op.getSelectedPrimärwaffe() != null)
            trg.put("primärwaffe", toJson(op.getSelectedPrimärwaffe()));
        if (op.getSelectedSekundärwaffe() != null)
            trg.put("sekundärwaffe", toJson(op.getSelectedSekundärwaffe()));
        if (CollectionUtils.isNotEmpty(op.getSelectedGadgets()))
            trg.put("gadgets", new JSONArray(
                op.getSelectedGadgets().stream().map(g -> g.getName()).collect(Collectors.toList())));
        return trg;
    }
    
    private JSONObject toJson(Waffe w) {
        JSONObject trg = new JSONObject();
        trg.put("name", w.getName());
        trg.put("typ", w.getTyp());
        return trg;
    }
    

    
    private List<String> getErrors(JSONObject json) {
        List<String> errors = new ArrayList<>(3);
        if (json.has("fehler"))
            json.getJSONArray("fehler").forEach(it -> errors.add(it.toString()));
        return errors;
    }
    
    public void speichereJson(R6HelperModel model, Collection<String> errors, File datei) throws IOException{
    	String json = createJson(model, errors);
    	FileUtils.writeStringToFile(datei, json, StandardCharsets.UTF_8);
    }
    
    /**
     * Lädt ein gespeichertes Model sowie die Fehlermeldungen im JSON-Format aus einer Datei.
     * @throws IOException  Wenn die Datei nicht gelesen werden konnte
     * @throws IllegalArgumentException  Wenn etwas mit dem JSON nicht stimmt
     */
    public ModelWithErrors ladeJson(File datei) throws IOException, IllegalArgumentException {
    	String jsonString = FileUtils.readFileToString(datei, StandardCharsets.UTF_8);
    	return ladeJson(jsonString);
    }
    public ModelWithErrors ladeJson(URI uri) throws IOException, IllegalArgumentException {
        String jsonString = IOUtils.toString(uri, StandardCharsets.UTF_8);
        return ladeJson(jsonString);
    }
    
    public ModelWithErrors ladeJson(String jsonString) throws IllegalArgumentException {
    	if (StringUtils.isBlank(jsonString))
    		throw new IllegalArgumentException("Die Datei enthält kein gültiges JSON!");
    	JSONObject json = new JSONObject(jsonString);
    	
    	return new ModelWithErrors(getModel(json), getErrors(json));
    }
    
    
    
}
