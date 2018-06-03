package kllngii.r6h.spieler;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import kllngii.r6h.model.R6HelperModel;

/**
 * Der Controller zum {@link SpielerlisteView}, um Spieler zu bearbeiten,
 * der Liste hinzuzufügen oder sie daraus zu entfernen.
 * 
 * @author Carsten Kelling
 */
public class SpielerlisteController {
    
    private final SpielerlisteView view;
    private R6HelperModel model;
    
    public SpielerlisteController(final boolean readWrite, final R6HelperModel model) {
        this.view = new SpielerlisteView(readWrite, model, this);
        setModel(model);
    }
   

	/**
     * Stellt ein neues Model bei Controller und View ein.
     */
    public void setModel(final R6HelperModel model) {
        this.model = model;
        view.setModel(model);
    }
    
    public SpielerlisteView getView() {
        return view;
    }

    /**
     * Erzeugt einen ganz neuen Spieler im Repository, fügt ihn aber noch nicht dem Team hinzu.
     * @param name
     */
    public void erzeugeSpieler(String name) {
        erzeugeSpieler(new Spieler(name));
    }
    
    public void erzeugeSpieler(Spieler sp) {
        if (!model.getSpielerRepo().contains(sp)) {
        		if (StringUtils.isNotBlank(sp.getName())) {
        			model.getSpielerRepo().add(sp);
        			view.refresh();
        		}
        }
    }
    
    public void loescheSpieler(Spieler s) {
    		if (model.getSpielerRepo().contains(s)) {
    			List<Spieler> team = model.getTeam();
    			if(team.contains(s)) {
    				team.remove(s);
    			}
    			model.getSpielerRepo().remove(s);
    			
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
    
    public boolean isSpielerImTeam(Spieler s) {
    		if(s != null && s.getName() != null) {
    			List<Spieler> team = model.getTeam();
    			if(team.contains(s))
    				return true;
    			else
    				return false;
    		}
    		else 
    			return false;
    		
    }
}
