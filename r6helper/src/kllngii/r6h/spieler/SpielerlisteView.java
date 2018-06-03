package kllngii.r6h.spieler;

import java.awt.event.ActionEvent;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import com.jgoodies.forms.builder.FormBuilder;

import kllngii.r6h.KllngiiView;
import kllngii.r6h.model.R6HelperModel;

/**
 * Erstellt das UI für das eigene Team (eine Liste von {@link Spieler}n.
 * Wo in welchem Container das UI angezeigt wird, weiß es nicht.
 * 
 * @author Carsten Kelling
 */
public class SpielerlisteView extends KllngiiView {
    
    protected final Logger log = Logger.getLogger(getClass());
    
    private final boolean readWrite;
    private R6HelperModel model;
    private String newPlayer = null;
    private final SpielerlisteController controller;
    private HashMap<Spieler, JLabel> kdlist = new HashMap<Spieler, JLabel>();
    private HashMap<Spieler, JButton> killlist = new HashMap<Spieler, JButton>();
    
    private JComponent root;

    
    public SpielerlisteView(final boolean readWrite, final R6HelperModel model, final SpielerlisteController controller) {
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
        List<Spieler> team = model.getTeam();
        final int numRows = team.size();
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
            cols.append(", pref, 12dlu");
            cols.append(", pref, 12dlu");
            cols.append(", pref, 12dlu");
            cols.append(", pref");
            
            builder.rows("p, $lgap, " + String.join(", $lgap, ", Collections.nCopies(numRows, "p")))
                   .columns(cols.toString());
        }
        
        int row = 1;
        builder.addTitle("Spieler").xy(1, row)
               .add(title("Headshots")).xy(3, row)
               .add(title("Aces")).xy(5, row)
               .add(title("Knifes")).xy(7, row)
               .add(title("Kills")).xy(9, row)
               .add(title("Deaths")).xy(11, row)
               .add(title("KD")).xy(13, row)
               .addSeparator("").xyw(1, 2, numCols);
        
        for (final Spieler spieler : team) {
            row++;
            if (! readWrite)  // Trennlinien nur beim Lesen
                builder.addSeparator("").xyw(1, row, numCols);
            row++;
            
            // Name
            JLabel nameLabel = new JLabel(spieler.getName());
            builder.add(nameLabel).xy(1, row);
            
            addHeadshot(spieler, row, builder);
            addAce(spieler, row, builder);
            addKnife(spieler, row, builder);
            addKills(spieler, row, builder);
            addDeaths(spieler, row, builder);
            addKD(spieler, row, builder);
        }
        if (numRows == 0)
            row++;
        
        // Spieler dem Team hinzufügen
        if (readWrite) {
            row += 2;
            builder.addTitle("Spieler in das Team aufnehmen").xyw(1, row, numCols);
            
            // Auswahl aus Spielerrepo
            row += 2;
            
            JComboBox<Spieler> repoList = new JComboBox<>(new Vector<>(model.getSpielerRepo()));
            repoList.addActionListener(e ->{
            		Spieler sp = (Spieler) repoList.getSelectedItem();
        			controller.toggleSpielerImTeam(sp); 		
            });
            builder.add(repoList).xy(1, row);
            
            // Komplett neuen Spieler anlegen
            row +=2;
            JTextField newPlayerName = new JTextField();
            newPlayerName.setColumns(25);
            JButton createPlayerButton = new JButton("Neu");
            createPlayerButton.addActionListener(e -> {
            		newPlayer = newPlayerName.getText();
            		if(!newPlayer.isEmpty()) {
            			controller.erzeugeSpieler(new Spieler(newPlayer));
            		}
            });
            JButton createAndAddPlayerButton = new JButton("Neu und in's Team");
            createAndAddPlayerButton.addActionListener(e -> {
            		newPlayer = newPlayerName.getText();
            		if(!newPlayer.isEmpty()) {
            			Spieler spieler = new Spieler(newPlayer);
            			controller.erzeugeSpieler(spieler);
            			controller.toggleSpielerImTeam(spieler);
            		}
            });
            JButton clearButton = new JButton("Team leeren");
            clearButton.addActionListener(e -> {
            		team.clear();
            		refresh();
            });
            builder.add(newPlayerName).xy(1, row);
            builder.add(createPlayerButton).xy(3, row);
            builder.add(createAndAddPlayerButton).xy(5, row);
            builder.add(clearButton).xy(7, row);
        }        
        
        root.add(builder.build());
    }
    private void addHeadshot(Spieler spieler, int row, FormBuilder builder) {
    		if (readWrite) {
            // Statistik heraufzählen können über Button
            final JButton countHSUpBtn = new JButton(String.valueOf(spieler.getHeadshots()));
            countHSUpBtn.setToolTipText("Headshot hinzufügen");
            countHSUpBtn.addActionListener((ActionEvent evt) -> {
                spieler.increaseHeadshots();
                countHSUpBtn.setText(String.valueOf(spieler.getHeadshots()));
                spieler.increaseKills();
                refreshKills(spieler);
                refreshKD(spieler);
            });
            builder.add(countHSUpBtn).xy(3, row);
            
        }
        else {
            builder.addLabel(String.valueOf(spieler.getHeadshots())).xy(3, row);
        }
    }
    private void addAce(Spieler spieler, int row, FormBuilder builder) {
		if (readWrite) {
        // Statistik heraufzählen können über Button
        final JButton countAceUpBtn = new JButton(String.valueOf(spieler.getAce()));
        countAceUpBtn.setToolTipText("Ace hinzufügen");
        countAceUpBtn.addActionListener((ActionEvent evt) -> {
            spieler.increaseAce();
            spieler.increaseKills(5);
            countAceUpBtn.setText(String.valueOf(spieler.getAce()));
            refreshKills(spieler);
            refreshKD(spieler);
        });
        builder.add(countAceUpBtn).xy(5, row);
        
    }
    else {
        builder.addLabel(String.valueOf(spieler.getAce())).xy(5, row);
    }
}
    private void addKnife(Spieler spieler, int row, FormBuilder builder) {
    		if (readWrite) {
    			final JButton countKnifeUpBtn = new JButton(String.valueOf(spieler.getKnifeKills()));
    	        countKnifeUpBtn.setToolTipText("KnifeKill hinzufügen");
    	        countKnifeUpBtn.addActionListener((ActionEvent evt) -> {
    	            spieler.increaseKnifeKills();
    	            spieler.increaseKills();
    	            countKnifeUpBtn.setText(String.valueOf(spieler.getKnifeKills()));
    	            refreshKills(spieler);
    	            refreshKD(spieler);
    	        });
    	        builder.add(countKnifeUpBtn).xy(7, row);
    	        
    		}
    }
    private void addKills(Spieler spieler, int row, FormBuilder builder) {
		if (readWrite) {
			final JButton countKillsUpBtn = new JButton(String.valueOf(spieler.getKills()));
	        countKillsUpBtn.setToolTipText("Kill hinzufügen");
	        countKillsUpBtn.addActionListener((ActionEvent evt) -> {
	            spieler.increaseKills();
	            countKillsUpBtn.setText(String.valueOf(spieler.getKills()));
	            refreshKD(spieler);
	        });
	        builder.add(countKillsUpBtn).xy(9, row);
	        killlist.put(spieler, countKillsUpBtn);
	        
		}
    }
    private void addDeaths(Spieler spieler, int row, FormBuilder builder) {
		if (readWrite) {
			final JButton countDeathUpBtn = new JButton(String.valueOf(spieler.getDeaths()));
	        countDeathUpBtn.setToolTipText("Kill hinzufügen");
	        countDeathUpBtn.addActionListener((ActionEvent evt) -> {
	            spieler.increaseDeaths();
	            countDeathUpBtn.setText(String.valueOf(spieler.getDeaths()));
	            refreshKD(spieler);
	        });
	        builder.add(countDeathUpBtn).xy(11, row);
	        
		}
    }
    private void addKD(Spieler spieler, int row, FormBuilder builder) {
		if (readWrite) {
		    JLabel kd = new JLabel();
			kd.setText(String.valueOf(spieler.getKD()));
	        builder.add(kd).xy(13, row);
	        
	        kdlist.put(spieler, kd);
		}
    }
    private void refreshKD(Spieler spieler) {
    	kdlist.get(spieler).setText(String.valueOf(spieler.getKD()));
    }
    private void refreshKills(Spieler spieler) {
    	killlist.get(spieler).setText(String.valueOf(spieler.getKills()));
    }
}
