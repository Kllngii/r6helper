package kllngii.r6h.toxic;

import java.util.Collections;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import com.jgoodies.forms.builder.FormBuilder;

import kllngii.r6h.KllngiiView;
import kllngii.r6h.Toxic;
import kllngii.r6h.model.R6HelperModel;

public class ToxiclisteView extends KllngiiView {
protected final Logger log = Logger.getLogger(getClass());
    
    private final boolean readWrite;
    private R6HelperModel model;
    private String newPlayer = null;
    private final ToxiclisteController controller;
    private List<Toxic> toxicteam;
    
    private JComponent root;
    
    public ToxiclisteView(final boolean readWrite, final R6HelperModel model, final ToxiclisteController controller) {
        this.readWrite = readWrite;
        this.controller = controller;
        setModel(model);
    }
    
    public void setModel(final R6HelperModel model) {
        this.model = model;
    }

    
    public JComponent getRoot() {
        if (root == null) {
            refresh();
        }
        return root;
    }
    /**
     * Baut das UI nach einer Änderung neu auf.
     */
    public void refresh() {
        if (root != null)
            root.removeAll();
        else
            root = new Box(BoxLayout.X_AXIS);
        
        // Pseudo-Tabelle - Layout festlegen:
        toxicteam = model.getToxic();
        final int numRows = toxicteam.size();
        final int numCols = 13;
        FormBuilder builder = FormBuilder.create().debug(false)
                .padding("6dlu, 12px, 6dlu, 12px");
        if (readWrite) {
            StringBuilder rowSpec = new StringBuilder("p, $pgap, ");  // Tabellenköpfe
            if (numRows > 0) {
                rowSpec.append(String.join(", $lgap, ", Collections.nCopies(numRows, "p")));  // Tabelle mit dem Team
                rowSpec.append(",");
            }
            rowSpec.append("30dlu");
            rowSpec.append(", p, $pgap");  // "Spieler in das Team aufnehmen"
            rowSpec.append(", p, $lgap, p");
            
            StringBuilder cols = new StringBuilder();
            cols.append("left:[pref, 100px], 6dlu");
            cols.append(", [60px,pref], 6dlu");
            cols.append(", [60px,pref], 6dlu");
            cols.append(", [60px, pref], 6dlu");
            cols.append(", [60px, pref], 6dlu");
            cols.append(", [60px, pref], 6dlu");
            cols.append(", [30px, pref]");

            builder.rows(rowSpec.toString())
                   .columns(cols.toString());
        }
        else {
            StringBuilder cols = new StringBuilder();
            cols.append("left:pref, 12dlu");
            cols.append(", pref, 12dlu");
            cols.append(", pref, 12dlu");
            
            builder.rows("p, $lgap, " + String.join(", $lgap, ", Collections.nCopies(numRows, "p")))
                   .columns(cols.toString());
        }
        
        int row = 1;
        builder.addTitle("Spieler").xy(1, row)
               .add(title("Grund")).xy(3, row)
               .addSeparator("").xyw(1, 2, numCols);
        
        for (final Toxic spieler : toxicteam) {
            row++;
            if (! readWrite)  // Trennlinien nur beim Lesen
                builder.addSeparator("").xyw(1, row, numCols);
            row++;
            
            // Name
            JLabel nameLabel = new JLabel(spieler.getName());
            JLabel grundLabel = new JLabel(spieler.getGrund());
            builder.add(nameLabel).xy(1, row);
            builder.add(grundLabel).xy(3, row);
        }
        if (numRows == 0)
            row++;
        
        // Spieler dem Team hinzufügen
        if (readWrite) {
            // Komplett neuen Spieler anlegen
            row +=2;
            JTextField newPlayerName = new JTextField();
            JTextField newPlayerGrund = new JTextField();
            newPlayerName.setColumns(25);
            newPlayerGrund.setColumns(25);
            JButton createPlayerButton = new JButton("Neu");
            createPlayerButton.addActionListener(e -> {
            		newPlayer = newPlayerName.getText();
            		String newGrund = newPlayerGrund.getText();
            		if(!newPlayer.isEmpty() && !newPlayer.isEmpty()) {
            			controller.erzeugeToxic(new Toxic(newPlayer, newGrund));
            		}
            });
            builder.add(newPlayerName).xy(1, row);
            builder.add(newPlayerGrund).xy(3, row);
            builder.add(createPlayerButton).xy(5, row);
        }
        root.add(builder.build());
    }

	public List<Toxic> getToxicteam() {
		return toxicteam;
	}

	public void setToxicteam(List<Toxic> toxicteam) {
		this.toxicteam = toxicteam;
	}
}
