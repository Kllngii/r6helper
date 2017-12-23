package kllngii.r6h.spieler;

import java.awt.event.ActionEvent;
import java.util.Collections;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;

import com.jgoodies.forms.builder.FormBuilder;

import kllngii.r6h.KllngiiView;
import kllngii.r6h.model.R6HelperModel;
import kllngii.r6h.model.Spieler;

/**
 * Erstellt das UI für das eigene Team (eine Liste von {@link Spieler}n.
 * Wo in welchem Container das UI angezeigt wird, weiß es nicht.
 * 
 * @author Carsten Kelling
 */
public class SpielerlisteView extends KllngiiView {
    
    private final boolean readWrite;
    private R6HelperModel model;
    
    private JComponent root;

    
    public SpielerlisteView(final boolean readWrite, final R6HelperModel model) {
        this.readWrite = readWrite;
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
        List<Spieler> team = model.getTeam();
        final int numRows = team.size();
        final int numCols = 5;
        FormBuilder builder = FormBuilder.create()
                .padding("6dlu, 12px, 6dlu, 12px");
        if (readWrite) {
            builder.rows("p, $pgap, " + String.join(", $pgap, ", Collections.nCopies(numRows, "p")))
                   .columns("left:pref, 6dlu, [60px,pref], 6dlu, [60px,pref]");
        }
        else {
            builder.rows("p, $lgap, " + String.join(", $lgap, ", Collections.nCopies(numRows, "p")))
                   .columns("left:pref, 12dlu, pref, 12dlu, pref");
        }
        
        int row = 1;
        builder.addTitle("Spieler").xy(1, row)
               .add(title("Headshots")).xy(3, row)
               .add(title("Aces")).xy(5, row)
               .addSeparator("").xyw(1, 2, numCols);
        
        for (final Spieler spieler : team) {
            row++;
            if (! readWrite)  // Trennlinien nur beim Lesen
                builder.addSeparator("").xyw(1, row, numCols);
            row++;
            
            // Name
            JLabel nameLabel = new JLabel(spieler.getName());
            builder.add(nameLabel).xy(1, row);
            
            if (readWrite) {
                // Statistik heraufzählen können über Button
                final JButton countUpBtn = new JButton(String.valueOf(spieler.getHeadshots()));
                countUpBtn.setToolTipText("Headshot hinzufügen");
                countUpBtn.addActionListener((ActionEvent evt) -> {
                    spieler.increaseHeadshots();
                    countUpBtn.setText(String.valueOf(spieler.getHeadshots()));
                });
                builder.add(countUpBtn).xy(3, row);
            }
            else {
                builder.addLabel(String.valueOf(spieler.getHeadshots())).xy(3, row);
            }
            
            //FIXME Das gleiche UI nochmal für die Aces -> Auslagern in eigene Komponente oder mindestens Methode
        }

        root.add(builder.build());
    }
    
}
