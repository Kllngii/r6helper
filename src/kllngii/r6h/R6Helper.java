package kllngii.r6h;

import java.awt.Color;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Vector;
import java.util.function.BooleanSupplier;
import java.util.function.Predicate;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.UIManager;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import kllngii.r6h.model.Gadget;
import kllngii.r6h.model.Operator;
import kllngii.r6h.model.R6HelperModel;
import kllngii.r6h.model.Rekrut;
import kllngii.r6h.model.Waffe;

//TODO View und Controller für Waffenart(wTyp)
public class R6Helper extends KllngiiApplication  {
    
	private static final File DATEI_OUTPUT = new File("/Users/kellingc/Dropbox/Texte von Lasse/r6helper.json");  //new File("/tmp/r6helper.json");
	private static final URI URI_INPUT = newURI("https://www.dropbox.com/s/qg32536wsqswir5/r6helper.json?dl=1");
	
	private static URI newURI(String url) {
	    try {
	        return new URI(url);
	    }
	    catch (Exception ex) {
	        throw new RuntimeException(ex);
	    }
	}
	
	
    private final Logger log = Logger.getLogger(getClass());
	
    private final boolean readWrite;
	private JFrame frame;
	
	private R6HelperModel model = new R6HelperModel();

	private JPanel panel_angriff;
	
	private JPanel panel_verteidigung;

	private Box panel_waffen;
	
	private JPanel panel_meldung;
	private Map<Operator, JCheckBox> angriffCheckboxen;
	private Map<Operator, JCheckBox> verteidigungCheckboxen;

	private JRadioButton rdbtnAngreifer;
	private JRadioButton rdbtnVerteidiger;
	private JButton btnWeb;
	
	private final int lücke = 12;
	private final int lückeKlein = lücke/2;

	private JLabel meldunglabel;
	
	private final SortedSet<String> errors = new TreeSet<>();
	
	private final SpeicherService speicherService = new SpeicherService();
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		// Nur-Lese-Modus kann über den ersten Parameter
		// angefordert werden
		boolean readWrite = true;
		if (args != null && args.length >= 1)
			readWrite = Boolean.valueOf(args[0]);
		
	    try {
	        // Set cross-platform Java L&F (also called "Metal")
	        UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
	        
//	        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    }
	    catch (Exception ex) {
	        // Aufgeben...
	        throw new RuntimeException(ex);
	    }
        
	    final boolean _readWrite = readWrite;
		EventQueue.invokeLater(new Runnable() {
			@SuppressWarnings("unused")
            @Override
            public void run() {
				new R6Helper(_readWrite);
			}
		});
	}

	/**
	 * Create the application.
	 */
	public R6Helper(boolean readWrite) {
		this.readWrite = readWrite;
		initialize();
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("R6 Helper");
		
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension size = new Dimension(1000, 600);
		frame.setSize(size.width, size.height);
		frame.setLocation((screensize.width-size.width)/2, (screensize.height-size.height)/2);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container root = frame.getContentPane();
		root.setLayout(new BoxLayout(root, BoxLayout.Y_AXIS));
		
		//// Ebene 0 ////
		root.add(Box.createVerticalStrut(lücke));
		
		JPanel panel_options = new JPanel();
		panel_options.setLayout(new BoxLayout(panel_options, BoxLayout.X_AXIS));
		root.add(panel_options);
		
		JLabel lblweb = new JLabel("Map:");
		panel_options.add(paddingRight(lblweb, lücke));
		
		btnWeb = new JButton("Öffnen");
		btnWeb.setEnabled(true);
		panel_options.add(btnWeb);
		btnWeb.addActionListener((ActionEvent evt) -> {
			URL url = null;
	        try {
	            url = new URL("http://www.r6maps.com/");
	            if (Desktop.isDesktopSupported()){  //FIXME Das vorher abfragen. Falls false, dann den Button gar nicht erst anzeigen, oder ihn ausgrauen
	                try {
	                    Desktop.getDesktop().browse(url.toURI());
	                } catch (IOException e) {
	                    e.printStackTrace();
	                } catch (URISyntaxException e) {
	                    e.printStackTrace();
	                }
	            }
	        } catch (MalformedURLException e) {
	            e.printStackTrace();
	        }
	    });
		//// Ebene 1 ////

		root.add(Box.createVerticalStrut(lücke));
		
		JPanel avPanel = new JPanel();
		avPanel.setLayout(new BoxLayout(avPanel, BoxLayout.X_AXIS));
		root.add(avPanel);
		
		JLabel lblArt = new JLabel("Gegnerteam:");
		avPanel.add(paddingRight(lblArt, lückeKlein));
		
		rdbtnAngreifer = new JRadioButton("Angreifer");
		avPanel.add(rdbtnAngreifer);
		rdbtnAngreifer.setSelected(true);
		rdbtnAngreifer.setEnabled(readWrite);
		
		rdbtnVerteidiger = new JRadioButton("Verteidiger");
		avPanel.add(rdbtnVerteidiger);
		rdbtnVerteidiger.setEnabled(readWrite);
		
		ButtonGroup art = new ButtonGroup();
	    art.add(rdbtnAngreifer);
	    art.add(rdbtnVerteidiger);
	    
	    //TODO Vorläufig: Buttons zum Laden und Speichern
	    JPanel speichernPanel = new JPanel();
	    speichernPanel.setLayout(new BoxLayout(speichernPanel, BoxLayout.X_AXIS));
	    avPanel.add( padding(speichernPanel, 0, lücke) );
	    
	    JButton ladenButton = new JButton("Laden");
	    ladenButton.addActionListener((ActionEvent evt) -> {
	        try {
	            R6HelperModel oldModel = speicherService.ladeAusPreferences();
	            if (oldModel != null) {
	                model = oldModel;
	                refreshView();
	            }
            }
            catch (IOException ex) {
                log.error("Fehler beim Laden des Models aus den Preferences!", ex);
            }
	    });
	    speichernPanel.add(ladenButton);
	    
	    if (readWrite) {
		    JButton speichernButton = new JButton("Speichern");
		    speichernButton.addActionListener((ActionEvent evt) -> {
		        try {
		            speicherService.speichereInPreferences(model);
		        }
		        catch (IOException ex) {
		            log.error("Fehler beim Speichern!", ex);
		        }
	        });
		    speichernPanel.add( paddingLeft(speichernButton, lückeKlein) );
	    
           JButton jsonLoadButton = new JButton("Json laden");
           jsonLoadButton.addActionListener((ActionEvent evt) -> {
                try {
                    SpeicherService.ModelWithErrors mwe = speicherService.ladeJson(URI_INPUT);
                    model = mwe.getModel();
                    errors.clear();
                    errors.addAll(mwe.getErrors());
                    
                    refreshView();
                }
                catch (Exception ex) {
                    log.error("Fehler beim Laden des JSON!", ex);
                    showError("Fehler beim Laden des JSON: " + StringUtils.defaultIfEmpty(ex.getMessage(), ex.toString()));
                }
            });
            speichernPanel.add( paddingLeft(jsonLoadButton, lückeKlein) );
            
	       JButton jsonSaveButton = new JButton("Json speichern");
	       jsonSaveButton.addActionListener((ActionEvent evt) -> {
	            try {
	                speicherService.speichereJson(model, errors, DATEI_OUTPUT);
	            }
	            catch (Exception ex) {
	                log.error("Fehler beim Speichern des JSON!", ex);
	                showError("Fehler beim Speichern des JSON: " + StringUtils.defaultIfEmpty(ex.getMessage(), ex.toString()));
	            }
	        });
	        speichernPanel.add( paddingLeft(jsonSaveButton, lückeKlein) );
	    }


	    
        //// Ebene 2 ////
        
        root.add(Box.createVerticalStrut(lücke));
	    
		panel_angriff = new JPanel();
//		panel_angriff.setBorder(new LineBorder(Color.BLACK));
		panel_angriff.setMaximumSize(new Dimension(999999, 120));
		panel_angriff.setPreferredSize(new Dimension(size.width, 120));
		root.add(panel_angriff);
		
		panel_verteidigung = new JPanel();
		panel_verteidigung.setMaximumSize(new Dimension(999999, 120));
		panel_verteidigung.setPreferredSize(new Dimension(size.width, 120));
		root.add(panel_verteidigung);
		
		panel_angriff.add(Box.createHorizontalStrut(lücke));
		panel_angriff.add(paddingRight(new JLabel("Operator:"), lückeKlein));
		
		panel_verteidigung.add(Box.createHorizontalStrut(lücke));
		panel_verteidigung.add(paddingRight(new JLabel("Operator:"), lückeKlein));
		
		angriffCheckboxen = new HashMap<>();
		for (Operator op : model.getAngreifer()) {
			JCheckBox checkBox = new JCheckBox(op.getName());
			checkBox.setEnabled(readWrite);
			angriffCheckboxen.put(op, checkBox);
			panel_angriff.add(checkBox);
			checkBox.addActionListener((ActionEvent evt)->{
				model.toggleSelected(op);
				fillPanelWaffen();
			});
		}
		verteidigungCheckboxen = new HashMap<>();
		for (Operator op : model.getVerteidiger()) {
			JCheckBox checkBox = new JCheckBox(op.getName());
			checkBox.setEnabled(readWrite);
			verteidigungCheckboxen.put(op, checkBox);
			panel_verteidigung.add(checkBox);
			checkBox.addActionListener((ActionEvent evt)->{
				model.toggleSelected(op);
				fillPanelWaffen();
			});
		}
		panel_verteidigung.setVisible(false);
		
		
		
		// Event-Handler für Auswahl Angriff/Verteidigung:
		rdbtnAngreifer.addActionListener((ActionEvent evt) -> {
		    model.setGegnerteamAngreifer(true);
			panel_angriff.setVisible(true);
			panel_verteidigung.setVisible(false);
			fillPanelWaffen();
		});
		rdbtnVerteidiger.addActionListener((ActionEvent evt) -> {
		    model.setGegnerteamAngreifer(false);
			panel_angriff.setVisible(false);
			panel_verteidigung.setVisible(true);
			fillPanelWaffen();
		});


        //// Ebene 3 ////
        
        root.add(Box.createVerticalStrut(lücke));

		panel_waffen = new Box(BoxLayout.Y_AXIS);
//		panel_waffen.setBorder(new LineBorder(Color.BLACK));
		root.add(panel_waffen);
		
		panel_meldung = new JPanel();
		root.add(panel_meldung);
		
		meldunglabel = new JLabel("");
		meldunglabel.setForeground(Color.RED);
		meldunglabel.setFont(new Font(Font.DIALOG, Font.BOLD, 20));
		panel_meldung.add(meldunglabel);
		panel_meldung.setVisible(false);
		
		root.add(Box.createVerticalGlue());
		
		frame.setVisible(true);
	}
	
	
	@SuppressWarnings("static-access")
	private void fillPanelWaffen() {
	    
	    clearErrors();
		
		List<Operator> selectedOps = (rdbtnAngreifer.isSelected()) ? model.getSelectedAngreifer() : model.getSelectedVerteidiger();
		showErrorIf(() -> selectedOps.size() > model.MAX_TEAMGRÖSSE,
		        "Aktiviere höchstens "+R6HelperModel.MAX_TEAMGRÖSSE+" Operator!");
		
		// Hat einer zu viele Gadgets?
		final Predicate<List<Operator>> einRekrutHatZuVieleGadgets = (_ops) -> _ops.stream().filter(o -> o instanceof Rekrut)
                .anyMatch(r -> r.getSelectedGadgets().size() > Rekrut.MAX_GADGETS);
        showErrorIf(() -> einRekrutHatZuVieleGadgets.test(selectedOps),
                "Ein Rekrut darf höchstens " + Rekrut.MAX_GADGETS + " Gadgets haben!");

		panel_waffen.removeAll();
		
		for (Operator op : selectedOps) {
            Dimension labelPreferredSize = new Dimension(100, 24);
            Dimension comboPreferredSize = new Dimension(150, 24);
            Dimension maxSize = new Dimension(250, 22);
		    
			Box panel = new Box(BoxLayout.X_AXIS);
//				panel.setBorder(new LineBorder(Color.RED));
			panel.setMaximumSize(new Dimension(999999, 22));
			
			panel.add(Box.createHorizontalStrut(lücke));
			
			JLabel label = new JLabel(op.getName());
			label.setPreferredSize(labelPreferredSize);
			label.setMaximumSize(maxSize);
			panel.add(label);
			
			if (readWrite) {
				JComboBox<Waffe> primW = new JComboBox<>(new Vector<Waffe>((op.getPrimärwaffen())));
				primW.setSelectedItem(op.getSelectedPrimärwaffe());
				primW.addActionListener((ActionEvent evt) -> {
				    op.setSelectedPrimärwaffe((Waffe)primW.getSelectedItem());
				    
				});
				primW.setPreferredSize(comboPreferredSize);
				primW.setMaximumSize(maxSize);
				panel.add(primW);
			}
			else {
				panel.add(new JLabel(op.getSelectedPrimärwaffe().getName()));
			}
			
			panel.add(Box.createHorizontalStrut(lücke));
			

			JComboBox<Waffe> secW = new JComboBox<>(new Vector<Waffe>(op.getSekundärwaffen()));
			secW.setSelectedItem(op.getSelectedSekundärwaffe());
			secW.setPreferredSize(comboPreferredSize);
			secW.setMaximumSize(maxSize);
			secW.addActionListener((ActionEvent evt) -> {
				op.setSelectedSekundärwaffe((Waffe)secW.getSelectedItem());
			});
			panel.add(secW);
			
			panel.add(Box.createHorizontalStrut(lücke));
			
			if (op instanceof Rekrut) {
				Rekrut rekrut = (Rekrut) op;
				// Checkboxen, um *2* Gadgets auszuwählen
				for (Gadget gadget : rekrut.getGadgets()) {
					JCheckBox cb = new JCheckBox(gadget.getName());
					cb.setSelected(rekrut.getSelectedGadgets().contains(gadget));
					cb.addActionListener((ActionEvent evt)-> {
						rekrut.toggleGadget(cb.getText());
						
					    List<Operator> _ops = (rdbtnAngreifer.isSelected()) ? model.getSelectedAngreifer() : model.getSelectedVerteidiger();
						showErrorIf(() -> einRekrutHatZuVieleGadgets.test(_ops),
						        "Ein Rekrut darf höchstens " + Rekrut.MAX_GADGETS + " Gadgets haben!");
					});
					
					panel.add(cb);
					
				}
			}
			else {
				// Combobox, um *1* Gadget auszuwählen
				JComboBox<Gadget> gad = new JComboBox<>(new Vector<Gadget>(op.getGadgets()));
				gad.setSelectedItem(op.getSelectedGadget());
				gad.setPreferredSize(comboPreferredSize);
				gad.setMaximumSize(maxSize);
				gad.addActionListener((ActionEvent evt) -> {
					op.setSelectedGadget((Gadget)gad.getSelectedItem());
				});
				panel.add(gad);
			}
			
			panel.add(Box.createHorizontalStrut(lücke));
			panel.add(Box.createHorizontalGlue());
			
			panel_waffen.add(panel);
			
			// Lücke zwischen den Operatoren
			panel_waffen.add(Box.createVerticalStrut(lückeKlein));
		}
		
		panel_waffen.add(Box.createVerticalGlue());
		
		frame.getContentPane().validate();
	}

	private void showError(String msg) {
	    errors.add(msg);
	    refreshErrors();
	}
	
	private void showErrorIf(BooleanSupplier condition, String msg) {
	    if (condition.getAsBoolean())
	        errors.add(msg);
	    else
	        errors.remove(msg);
	    
	    refreshErrors();
	}
	
	private void refreshErrors() {
	    if (errors.isEmpty())
	        panel_meldung.setVisible(false);
	    else {
	        meldunglabel.setText("<html>" + String.join("<br>", errors.toArray(new String[0])) + "</html>");
	        panel_meldung.setVisible(true);
	    }
	}
	
	
	private void clearErrors() {
	    errors.clear();
	    panel_meldung.setVisible(false);
	}
	
	
	/**
	 * (Ent)markiert die Angreifer- und Verteidiger-Checkboxen passend zum Model.
	 */
	private void setAVCheckboxes() {
	    // Erst alle entmarkieren:
	    for (JCheckBox cb : angriffCheckboxen.values())
	        cb.setSelected(false);
        for (JCheckBox cb : verteidigungCheckboxen.values())
            cb.setSelected(false);
        
        // Nun diejenigen markieren, die auch im Model ausgewählt sind:
        for (Operator op : model.getSelectedAngreifer()) {
            JCheckBox cb = angriffCheckboxen.get(op);
            if (cb != null)
                cb.setSelected(true);
        }
        for (Operator op : model.getSelectedVerteidiger()) {
            JCheckBox cb = verteidigungCheckboxen.get(op);
            if (cb != null)
                cb.setSelected(true);
        }
	}
	
	
	/**
	 * Aktualisiert den gesamten View; notwendig z.B. nach dem Laden des Models aus einer Datei.
	 */
	private void refreshView() {
        rdbtnAngreifer.setSelected(model.isGegnerteamAngreifer());
        panel_angriff.setVisible(model.isGegnerteamAngreifer());
        rdbtnVerteidiger.setSelected(!model.isGegnerteamAngreifer());
        panel_verteidigung.setVisible(!model.isGegnerteamAngreifer());
        setAVCheckboxes();  // Checkboxen passend zum Model setzen
        fillPanelWaffen();  // Änderungen sichtbar machen
        refreshErrors();
	}

	
}
