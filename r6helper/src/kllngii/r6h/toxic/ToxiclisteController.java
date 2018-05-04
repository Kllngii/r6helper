package kllngii.r6h.toxic;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import kllngii.r6h.Toxic;
import kllngii.r6h.model.R6HelperModel;
import kllngii.r6h.model.Spieler;
import kllngii.r6h.spieler.SpielerlisteView;

public class ToxiclisteController {
	private final ToxiclisteView view;
    private R6HelperModel model;
    
    public ToxiclisteController(final boolean readWrite, final R6HelperModel model) {
        this.view = new ToxiclisteView(readWrite, model, this);
        setModel(model);
    }
    
    /**
     * Stellt ein neues Model bei Controller und View ein.
     */
    public void setModel(final R6HelperModel model) {
        this.model = model;
        view.setModel(model);
    }
    
    public ToxiclisteView getView() {
        return view;
    }
    
    public void erzeugeToxic(Toxic sp) {
        if (!model.getToxic().contains(sp)) {
        		if (StringUtils.isNotBlank(sp.getName())) {
        			model.getToxic().add(sp);
        			view.refresh();
        		}
        }
    }
    public void loescheToxic(Toxic sp) {
		if (model.getToxic().contains(sp)) {
			model.getToxic().remove(sp);
			view.refresh();
			
		}
}
    
}
