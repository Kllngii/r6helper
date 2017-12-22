package kllngii.r6h.spieler;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import kllngii.r6h.model.R6HelperModel;
import kllngii.r6h.model.Spieler;

/**
 * Der Controller zum {@link SpielerlisteView}, um Spieler zu bearbeiten,
 * der Liste hinzuzufügen oder sie daraus zu entfernen.
 * 
 * @author Carsten Kelling
 */
public class SpielerlisteController {
    
    private final SpielerlisteView view;
    private final R6HelperModel model;
    
    
    public SpielerlisteController(final SpielerlisteView view, final R6HelperModel model) {
        this.view = view;
        this.model = model;
    }

    /**
     * Erzeugt einen ganz neuen Spieler im Repository, fügt ihn aber noch nicht dem Team hinzu.
     * @param name
     */
    public void erzeugeSpieler(String name) {
        //TODO Doppelte Spieler vermeiden?
        if (StringUtils.isNotBlank(name)) {
            model.getSpielerRepo().add(new Spieler(name));
            view.refresh();
        }
    }
    
    
    public void toggleSpielerImTeam(Spieler s) {
        if (s == null)
            return;
        
        List<Spieler> team = model.getTeam();
        if (team.contains(s))
            team.remove(s);
        else
            team.add(s);
        
        view.refresh();
    }
}
