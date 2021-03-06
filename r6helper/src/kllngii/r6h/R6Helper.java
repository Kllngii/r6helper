package kllngii.r6h;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Vector;
import java.util.function.BooleanSupplier;
import java.util.function.Predicate;
import java.util.prefs.BackingStoreException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.jgoodies.forms.builder.FormBuilder;
import com.jgoodies.looks.plastic.PlasticLookAndFeel;
import com.jgoodies.looks.plastic.theme.ExperienceRoyale;
import com.jgoodies.validation.view.ValidationResultViewFactory;

import kllngii.r6h.model.Einstellungen;
import kllngii.r6h.model.EinstellungenService;
import kllngii.r6h.model.Faehigkeit;
import kllngii.r6h.model.Gadget;
import kllngii.r6h.model.Operator;
import kllngii.r6h.model.R6HelperModel;
import kllngii.r6h.model.R6Map;
import kllngii.r6h.model.Rekrut;
import kllngii.r6h.model.SpeicherService;
import kllngii.r6h.model.Waffe;
import kllngii.r6h.model.Waffentyp;
import kllngii.r6h.model.WebTyp;
import kllngii.r6h.spieler.Spieler;
import kllngii.r6h.spieler.SpielerlisteController;

public class R6Helper extends KllngiiView {
	
	//Sonstiges:
	//TODO Mehrere Channel einführen, um PW geschützt getrennt Programm zu nutzen
	//TODO Optional: Fenster auch auf Java FX umstellbar -> Branch feature/JavaFX
	//FIXME Wenn der R6Helper den Server nicht findet startet der Server nicht
	
	//R6helper-Gegenerteam:
	//TODO Drohnen/Kamera Counter -> BulletProof, BlackEye, Schockdrohne, Böses Auge und Yokai beachten
	//TODO Rangrechner, der Team-Durchschnitt ausrechnet
	
	//Einstellungen:
	//TODO FTP implementieren
	
	//R6helper-Team:
	//FIXME RO Modus nicht voll funktionsfähig
	//TODO Win Counter
	//TODO Top3 anzeigen
	//TODO Top3 sortierbar nach Aces/Wins/Headshots/etc.
	
	//R6helper-Toxic:
	//FIXME Toxicspieler werden nicht in das Model gespeichert.
	
	//R6Helper-DB:
	//TODO R6DB nutzen, um den Spieler um eine R6Values zu ergänzen
	
    private final Logger log = Logger.getLogger(getClass());
    
    private final boolean readWrite;
    private JFrame frame;

    private final Einstellungen einstellungen = new Einstellungen();
    private EinstellungsFrame einstellungsFrame = null; // wird bei Bedarf erzeugt

    private R6HelperModel model = new R6HelperModel();
    
    private SpielerlisteController spielerlisteController;
    
    private JPanel panel_angriff;
    private JPanel panel_verteidigung;
    
    private final JLabel angriffOpLabel = compFactory.createTitle("Operator");
    private final JLabel verteidigungOpLabel = compFactory.createTitle("Operator");

    private JComponent panel_waffen;

    private JPanel panel_meldung;
    private Map<Operator, JCheckBox> angriffCheckboxen;
    private Map<Operator, JCheckBox> verteidigungCheckboxen;

    private JRadioButton rdbtnAngreifer;
    private JRadioButton rdbtnVerteidiger;

    private final int lücke = 12;

    private JLabel meldunglabel;
    private final SortedSet<String> errors = new TreeSet<>();

    private final Map<Waffentyp, WaffenTypLabel> waffenTypMap = new LinkedHashMap<>();

    private final SpeicherService speicherService = new SpeicherService();
    
    private static class WaffenTypLabel {
        private final String text;
        private final JLabel label;

        public WaffenTypLabel(JLabel label) {
            this.text = label.getText();
            this.label = label;
        }

        public String getText() {
            return text;
        }

        public JLabel getLabel() {
            return label;
        }

    }

    /**
     * Startpunkt der Anwendung
     */
    public static void main(String[] args) {
        // Nur-Lese-Modus kann über den ersten Parameter
        // angefordert werden
        boolean readWrite = true;
        if (args != null && args.length >= 1)
            readWrite = Boolean.valueOf(args[0]);

        PlasticLookAndFeel.setPlasticTheme(new ExperienceRoyale());  // ganz gut, farbig
        
//        try {
//        	//TODO neue LookAndFeels testen
//            UIManager.setLookAndFeel(new PlasticXPLookAndFeel());
//        } catch (Exception ex) {
//            // Aufgeben...
//            throw new RuntimeException(ex);
//        }

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
     * Erstelle Application
     */
    public R6Helper(boolean readWrite) {
        this.readWrite = readWrite;

        // Einstellungen laden:
        try {
            new EinstellungenService().ladeAusPreferences(einstellungen);
        } catch (BackingStoreException e) {
            log.warn("Alte Einstellungen können nicht aus den Preferences geladen werden!", e);
        }

        initialize();
    }

    /**
     * Initialisiere den Frame
     */
	private void initialize() {
        spielerlisteController = new SpielerlisteController(readWrite, model);

        frame = new JFrame();
        frame.setTitle("R6 Helper");
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension size = new Dimension(1080, 630);
        frame.setSize(size.width, size.height);
        frame.setLocation((screensize.width - size.width) / 2, (screensize.height - size.height) / 2);
        log.info("Baue jetzt den Frame");
        long timestart = timerStart();
        log.info(System.getProperty("os.name"));
        if(Toolkit.getDefaultToolkit().getImage("icon.jpg") != null) {
        	//Icon für Windows setzen
        	frame.setIconImage(Toolkit.getDefaultToolkit().getImage("icon.jpg"));
        	//Icon für Mac setzen
        	try {
        		
				Class<?> util = Class.forName("com.apple.eawt.Application");
				
				Method getApplication = util.getMethod("getApplication", new Class[0]);
        		Object application = getApplication.invoke(util);
        		
				Class<?> params[] = new Class[1];
        		params[0] = Image.class;
        		
				Method setDockIconImage = util.getMethod("setDockIconImage", params);
        		URL url = R6Helper.class.getClassLoader().getResource("diamond.png");
        		log.info(url);
        		Image image = Toolkit.getDefaultToolkit().getImage(url);
        		setDockIconImage.invoke(application, image);
        	} 
        	catch (ClassNotFoundException | NoSuchMethodException  | InvocationTargetException | IllegalAccessException e1) {
        		log.warn("Klasse für ICON auf Mac nicht gefunden!", e1);
        	}
    		System.setProperty("apple.laf.useScreenMenuBar", "true");
    		System.setProperty("com.apple.mrj.application.apple.menu.about.name", "R6Helper");
        }
        else
        	log.warn("Icon wurde nicht gefunden! " + Toolkit.getDefaultToolkit().getImage("icon.jpg"));
        frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

        
        //// Links: Tabs für Gegnerteam und eigenes Team ////
        
        final Container linksRechtsRoot = new Box(BoxLayout.X_AXIS);
        frame.getContentPane().add(linksRechtsRoot);
        
        final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.WRAP_TAB_LAYOUT);
        final Dimension tabDimension = new Dimension(890, 600);
        
        // Gegnerteam
        Container gegnerTabRoot = createGegnerTabContent();
        JScrollPane tabbedPaneScroller = new JScrollPane(gegnerTabRoot);
        tabbedPaneScroller.setPreferredSize(tabDimension);
        tabbedPane.addTab("Gegnerteam", tabbedPaneScroller);
        
        // Eigenes Team
        JComponent teamTabRoot = spielerlisteController.getView().getRoot();
        tabbedPaneScroller = new JScrollPane(teamTabRoot);
        tabbedPaneScroller.setPreferredSize(tabDimension);
        tabbedPane.addTab("Team", teamTabRoot);
        linksRechtsRoot.add(tabbedPane);
        //// Rechts: Menu ////
        
        linksRechtsRoot.add(createMenuContent());

        
        //// Unten: Meldungen ////
        
        final Container meldungenRoot = new Box(BoxLayout.Y_AXIS);
        frame.getContentPane().add(meldungenRoot);
        meldungenRoot.add(Box.createVerticalStrut(lücke));

        panel_meldung = new JPanel();
        meldungenRoot.add(panel_meldung);

        meldunglabel = new JLabel("");
        meldunglabel.setForeground(Color.RED);
        meldunglabel.setFont(new Font(Font.DIALOG, Font.BOLD, 14));
        panel_meldung.add(meldunglabel);
        panel_meldung.setVisible(false);

        meldungenRoot.add(Box.createVerticalGlue());
        
        frame.setVisible(true);
        log.info("Framebau abgeschlossen nach: " + timerEnd(timestart) + "ms");
        // JSON einmal laden,
        // dann ggf.Refresh-Threads starten:
        final int refreshIntervalS = Math.max(0, einstellungen.getRefreshIntervalS());  // negative Werte krachen
        if(!readWrite) {
            
            if (refreshIntervalS == 0) {
                log.info("JSON nach dem Start einmal einlesen...");
                ladeAusJson();
            }
            else {
                // In einem eigenen Thread:
                // Sofort einmal das JSON holen
                // UND später regelmäßig neu einlesen
//            	BasicThreadFactory factory = new BasicThreadFactory.Builder()
//            		     .namingPattern("JSONPool-%d")
//            		     .build();
//            	einstellungen.getRefreshThreadPool().setThreadFactory(factory);
                Runnable refreshRunnable = () -> {
                    log.debug("Refresh läuft - JSON neu einlesen...");
                    ladeAusJson();
                };
                einstellungen.setRefreshRunnable(refreshRunnable, "JSON neu einlesen");
            }
        }
        else {
            log.info("JSON nach dem Start einmal einlesen...");
            ladeAusJson();
                
            if (refreshIntervalS > 0) {
                // In einem eigenen Thread das Model regelmäßig neu speichern
                Runnable refreshRunnable = () -> {
                    log.debug("Refresh läuft - JSON neu speichern");
                    speichereInJSON();
                };
                einstellungen.setRefreshRunnable(refreshRunnable, "JSON neu speichern");
            }
        }
        frame.addWindowListener(new WindowAdapter(){
            
			@SuppressWarnings("synthetic-access")
			@Override
			public void windowClosing(WindowEvent e){
                log.info("windowClosing...");
                e.getWindow().dispose();
                einstellungen.shutdown();
            	speichereInJSON();
            	System.exit(0);
            }
        });
        log.info("Frame fertig gebaut.");
    }
    
    private Container createGegnerTabContent() {
        final Container root = new Box(BoxLayout.X_AXIS);
        final Container leftColumn = new Box(BoxLayout.Y_AXIS);
        root.add(leftColumn);
        
        
        //// Ebene 1 ////

        rdbtnAngreifer = new JRadioButton("Angreifer", true);
        rdbtnAngreifer.setEnabled(readWrite);

        rdbtnVerteidiger = new JRadioButton("Verteidiger");
        rdbtnVerteidiger.setEnabled(readWrite);

        ButtonGroup art = new ButtonGroup();
        art.add(rdbtnAngreifer);
        art.add(rdbtnVerteidiger);

        FormBuilder avPanel = FormBuilder.create()
                .columns("pref, 6dlu, pref")
                .rows("p, $lgap, p")
                .padding("6dlu, 12px, 6dlu, 12px")
                .add(rdbtnAngreifer).xy(1, 1)
                .add(rdbtnVerteidiger).xy(3, 1);
        leftColumn.add(avPanel.build());

        
        //// Ebene 2 ////

        // Pseudo-Tabelle für Angreifer bzw. Verteidiger - Layout festlegen:
        final int numColumns = 5;  // Anzahl Checkboxen nebeneinander
        final int numRows = (int) Math.ceil((double) model.getAngreifer().size() / (double) numColumns);
        
        FormBuilder angriffBuilder = createAvBuilder(numColumns, numRows, angriffOpLabel);
        FormBuilder verteidigungBuilder = createAvBuilder(numColumns, numRows, verteidigungOpLabel);

        angriffCheckboxen = new HashMap<>();
        int col = 1;
        int row = 3;
        for (Operator op : model.getAngreifer()) {
            JCheckBox checkBox = new JCheckBox(op.getName());
            checkBox.setEnabled(readWrite);
            angriffCheckboxen.put(op, checkBox);
            
            // Und der "Tabelle" im View hinzufügen:
            angriffBuilder.add(checkBox).xy(2*col-1, row);
            col++;
            if (col > numColumns) {
                col = 1;
                row = row + 2;
            }
            
            checkBox.addActionListener((ActionEvent evt) -> {
                model.toggleSelected(op);
                fillPanelWaffen();
            });
        }
        panel_angriff = angriffBuilder.build();
        leftColumn.add(panel_angriff);
        
        verteidigungCheckboxen = new HashMap<>();
        col = 1;
        row = 3;
        for (Operator op : model.getVerteidiger()) {
            JCheckBox checkBox = new JCheckBox(op.getName());
            checkBox.setEnabled(readWrite);
            verteidigungCheckboxen.put(op, checkBox);
            
            // Und der "Tabelle" im View hinzufügen:
            verteidigungBuilder.add(checkBox).xy(2*col-1, row);
            col++;
            if (col > numColumns) {
                col = 1;
                row = row + 2;
            }
            
            checkBox.addActionListener((ActionEvent evt) -> {
                model.toggleSelected(op);
                fillPanelWaffen();
            });
        }
        panel_verteidigung = verteidigungBuilder.build();
        leftColumn.add(panel_verteidigung);
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

        panel_waffen = new Box(BoxLayout.X_AXIS);
        leftColumn.add(panel_waffen);
        
        
        //// Ebene 4 ////
        
        final int numWaffenTyp = 6;
        FormBuilder wArt = FormBuilder.create()
                .columns(String.join(", 6dlu, ", Collections.nCopies(numWaffenTyp, "[45dlu,pref]")))
                .rows("p, $lgap, p")
                .padding("6dlu, 12px, 6dlu, 12px")
                .addSeparator("Waffen des Teams").xyw(1, 1, 2*numWaffenTyp - 1);
        
        JLabel lblSturm = new JLabel("Sturmgewehre:");
        waffenTypMap.put(Waffentyp.STURM, new WaffenTypLabel(lblSturm));
        wArt.add(lblSturm).xy(1, 3);

        JLabel lblShot = new JLabel("Shotguns:");
        waffenTypMap.put(Waffentyp.SHOTGUN, new WaffenTypLabel(lblShot));
        wArt.add(lblShot).xy(3, 3);

        JLabel lblLMG = new JLabel("LMGs:");
        waffenTypMap.put(Waffentyp.LMG, new WaffenTypLabel(lblLMG));
        wArt.add(lblLMG).xy(5, 3);

        JLabel lblDMR = new JLabel("DMRs:");
        waffenTypMap.put(Waffentyp.DMR, new WaffenTypLabel(lblDMR));
        wArt.add(lblDMR).xy(7, 3);

        JLabel lblMP = new JLabel("MPs:");
        waffenTypMap.put(Waffentyp.MP, new WaffenTypLabel(lblMP));
        wArt.add(lblMP).xy(9, 3);

        JLabel lblP = new JLabel("Pistolen:");
        waffenTypMap.put(Waffentyp.PISTOLE, new WaffenTypLabel(lblP));
        wArt.add(lblP).xy(11, 3);
        
        leftColumn.add(wArt.build());
        
        return root;
    }

    private JPanel createMenuContent() {
        //// Menü-Buttons am rechten Rand ////
        
        FormBuilder menu = FormBuilder.create()
                .columns("pref")
                .rows("p, $lgap, p, $lgap, p, $lgap, p, $pgap, " +
                      "p, $lgap, p, $lgap, " +
                      "p, $pgap, p, $pgap, p, $pgap, p")
                .padding("6dlu, 12px, 6dlu, 12px");
        
        JComboBox<WebTyp> chooseWeb = new JComboBox<WebTyp>(WebTyp.values());
        JButton btnWeb = new JButton("R6 Programme");
        JComboBox<R6Map> comboWeb = new JComboBox<R6Map>(R6Map.values());
        if (Desktop.isDesktopSupported()) {
            btnWeb.setEnabled(true);
        } else {
            btnWeb.setEnabled(false);
        }
        menu.add(chooseWeb).xy(1, 1);
        menu.add(btnWeb).xy(1, 3);
        menu.add(comboWeb).xy(1, 5);
        btnWeb.addActionListener((ActionEvent evt) -> {
            URL url = null;
            if(chooseWeb.getSelectedItem() == WebTyp.R6HELPER) {
            		try {
            			if(readWrite)
            				//FIXME Wenn der 192.168.2.10er Server genutzt wird, crasht es hier
            				url = new URL("http://localhost:8080/r6/");
            			else
            				url = new URL("http://www.mine.kelling.de:8080/r6/");
            			log.info("Knopf gedrückt - URL: "+url);
            			Desktop.getDesktop().browse(url.toURI());
				}
            		catch (IOException | URISyntaxException e) {
            			logFehler("Website kann nicht geöffnet werden!");
            			e.printStackTrace();
				}
            }
            if(chooseWeb.getSelectedItem() == WebTyp.R6MAP) {
	            	try {
	            		url = new URL("http://www.r6maps.com/"+comboWeb.getItemAt(comboWeb.getSelectedIndex()).getUrl());
	            		log.info("Knopf gedrückt - URL: "+url);
	            		Desktop.getDesktop().browse(url.toURI());
				}
	            	catch (IOException | URISyntaxException e) {
	            		e.printStackTrace();
	            		logFehler("Website kann nicht geöffnet werden!");
				}
            }
            else {
            		logFehler("Das sollte nicht passieren! - WebTyp ist: "+chooseWeb.getSelectedItem());
            }
        });
        
        JButton jsonLoadButton = new JButton("Laden");
        jsonLoadButton.addActionListener((ActionEvent evt) -> {
            ladeAusJson();
        });
        menu.add(jsonLoadButton).xy(1, 9);

        if (readWrite) {
            JButton jsonSaveButton = new JButton("Speichern");
            jsonSaveButton.addActionListener((ActionEvent evt) -> {
                speichereInJSON();
                
            });
            menu.add(jsonSaveButton).xy(1, 11);
        }
        JButton settings = new JButton("Einstellungen");
        settings.addActionListener((ActionEvent evt) -> {
            if (einstellungsFrame == null)
                einstellungsFrame = new EinstellungsFrame(einstellungen);
            einstellungsFrame.setVisible(true);
            einstellungsFrame.toFront();
        });
        menu.add(settings).xy(1, 15);
        if(readWrite) {
        		JButton resetBtn = new JButton("Reset");
        		resetBtn.addActionListener((ActionEvent evt) -> {
        			reset();
        		});
        		menu.add(resetBtn).xy(1, 13);
        }
        
        JPanel menuPanel = menu.build();
        return menuPanel;
    }
    
    private FormBuilder createAvBuilder(final int numColumns, final int numRows, JLabel operatorLabel) {
        operatorLabel.setHorizontalAlignment(SwingConstants.LEFT);
        FormBuilder av = FormBuilder.create()
                .columns(String.join(", 3dlu, ", Collections.nCopies(numColumns, "pref")))  // n Checkboxen nebeneinander
                .rows("p, $lgap, " + String.join(", $lgap, ", Collections.nCopies(numRows, "p")))
                .padding("6dlu, 12px, 6dlu, 12px")
                .add(compFactory.createSeparator(operatorLabel)).xyw(1, 1, 2*numColumns - 1);
        
        return av;
    }
    private void reset() {
    	
    	List<Spieler> team = model.getTeam();
    	List<Spieler> spielerRepo = model.getSpielerRepo();
    	model = new R6HelperModel();
    	errors.clear();
    	model.setTeam(team);
    	model.setSpielerRepo(spielerRepo);
    	speichereInJSON();
    	refreshView();
    }
    private void speichereInJSON() {
    	try {
    		long time1 = System.currentTimeMillis();
            speicherService.speichereJson(model, errors, einstellungen.getUrlOutput());
            time1 = System.currentTimeMillis() - time1;
            
            if (time1 < 1000)
                log.debug("Speicherzeit: "+ time1 +"ms"); 
            else
                log.info("Speicherzeit erhöht: "+ time1 +"ms"); 
            
        } catch (Exception ex) {
            log.error("Fehler beim Speichern des JSON!", ex);
            showError("Fehler beim Speichern des JSON: "
                    + StringUtils.defaultIfEmpty(ex.getMessage(), ex.toString()));
        }
    	
    	if (speicherService.isFileUrl(einstellungen.getUrlOutput())) {
            	try {
            	    File dateiOutput = new File(einstellungen.getUrlOutput());          
            	    saveScreenshot(new File(dateiOutput.getParentFile(), "R6Screenshot.png"));
            	    log.info("Der Dateipfad ist: "+dateiOutput.getParentFile());
            	} catch (Exception ex) {
                    log.error("Fehler beim Speichern des Screenshots!", ex);
                    showError("Fehler beim Speichern des Screenshots: "
                            + StringUtils.defaultIfEmpty(ex.getMessage(), ex.toString()));
            }
    	}
    }

    private void ladeAusJson() {
        try {
            long timeAll = System.currentTimeMillis();
            log.info("Lade JSON...");
            
            long timeLoad = System.currentTimeMillis();
            SpeicherService.ModelWithErrors mwe = speicherService.ladeJson(einstellungen.getUriInput());
            model = mwe.getModel();
            spielerlisteController.setModel(model);
            errors.clear();
            errors.addAll(mwe.getErrors());
            timeLoad = System.currentTimeMillis() - timeLoad;

            long timeUi = System.currentTimeMillis();
            if (SwingUtilities.isEventDispatchThread())
                refreshView();
            else
                SwingUtilities.invokeAndWait(() -> refreshView());
            timeUi = System.currentTimeMillis() - timeUi;
            
            timeAll = System.currentTimeMillis() - timeAll;
            if (timeAll < 1000)
                log.debug("Ladezeit: " + timeAll + "ms (" + 
                        timeLoad + " JSON holen und verarbeiten, " +
                        timeUi + " UI aktualisieren)");
            else
                log.info("Ladezeit erhöht: " + timeAll + "ms (" + 
                    timeLoad + " JSON holen und verarbeiten, " +
                    timeUi + " UI aktualisieren)");
        }
        catch (Exception ex) {
            log.error("Fehler beim Laden des JSON!", ex);
            showError("Fehler beim Laden des JSON: " + StringUtils.defaultIfEmpty(ex.getMessage(), ex.toString()));
        }
    }

    private void showWaffentyp() {
        EnumSet<Waffentyp> interessierendeTypen;
        List<Operator> selectedOperators;
        //TODO Immer nur die gebrauchten Felder anzeigen
        if (rdbtnAngreifer.isSelected()) {
            interessierendeTypen = EnumSet.of(Waffentyp.PISTOLE, Waffentyp.REIHEN, Waffentyp.SHOTGUN, Waffentyp.STURM,
                    Waffentyp.DMR, Waffentyp.LMG);
            selectedOperators = model.getSelectedAngreifer();
        } else {
            interessierendeTypen = EnumSet.of(Waffentyp.PISTOLE, Waffentyp.REIHEN, Waffentyp.SHOTGUN, Waffentyp.MP, Waffentyp.STURM);
            selectedOperators = model.getSelectedVerteidiger();
        }

        // Zählen der Anzahl Waffen pro Typ
        Map<Waffentyp, Integer> anzahlByWaffentyp = new HashMap<>();
        for (Waffentyp typ : Waffentyp.values()) {
            anzahlByWaffentyp.put(typ, 0);
        }
        for (Operator op : selectedOperators) {
            List<Waffe> waffen = Arrays.asList(op.getSelectedPrimärwaffe(), op.getSelectedSekundärwaffe());
            for (Waffe waffe : waffen) {
                if (waffe != null && interessierendeTypen.contains(waffe.getTyp())) {
                    // Waffe ist ausgewählt und interessiert hier

                    // Sonderfall: Reihe zählt als Pistole
                    Waffentyp typ = waffe.getTyp();
                    if (typ == Waffentyp.REIHEN)
                        typ = Waffentyp.PISTOLE;

                    Integer anzahl = anzahlByWaffentyp.get(typ);
                    anzahl++;
                    anzahlByWaffentyp.put(typ, anzahl);
                }
            }
        }
        log.info(anzahlByWaffentyp);

        // Ändern der Texte an den Labeln
        for (Waffentyp waffentyp : Waffentyp.values()) {
            WaffenTypLabel wtl = waffenTypMap.get(waffentyp);
            if (wtl == null)
                continue;
            wtl.getLabel().setText(wtl.getText() + anzahlByWaffentyp.get(waffentyp));

            // Label sichtbar oder unsichtbar machen:
            wtl.getLabel().setVisible(interessierendeTypen.contains(waffentyp));
        }
    }

    
    private void fillPanelWaffen() {

        clearErrors();

        List<Operator> selectedOps = (rdbtnAngreifer.isSelected()) ? model.getSelectedAngreifer()
                : model.getSelectedVerteidiger();
        showErrorIf(() -> selectedOps.size() > R6HelperModel.MAX_TEAMGRÖSSE,
                Konst.ERROR_AKTIVIERE_HÖCHSTENS_N_OPERATOR);

        // Hat einer zu viele Gadgets?
        final Predicate<List<Operator>> einRekrutHatZuVieleGadgets = (_ops) -> _ops.stream()
                .filter(o -> o instanceof Rekrut).anyMatch(r -> r.getSelectedGadgets().size() > Rekrut.MAX_GADGETS);
        showErrorIf(() -> einRekrutHatZuVieleGadgets.test(selectedOps),
                Konst.ERROR_REKRUT_HAT_ZU_VIELE_GADGETS);

        panel_waffen.removeAll();
        
        // Pseudo-Tabelle für Operators und Rekruten - Layout festlegen:
        final int numRows = selectedOps.size();
        FormBuilder builder = FormBuilder.create()
                .padding("6dlu, 12px, 6dlu, 12px");
        if (readWrite) {
            builder.rows("p, $pgap, " + String.join(", $pgap, ", Collections.nCopies(numRows, "p")))
                   .columns("left:pref,  6dlu, pref,  6dlu, pref,  6dlu, pref,  6dlu, [pref,45px], 6dlu, [20px,pref], 3dlu, pref");
        }
        else {
            builder.rows("p, $lgap, " + String.join(", $lgap, ", Collections.nCopies(numRows, "p")))
                   .columns("left:pref, 12dlu, pref, 12dlu, pref, 12dlu, pref, 12dlu, pref, 12dlu, pref, 4dlu, [40px,pref]");
        }
        
        int row = 1;
        builder.addTitle("Operator").xy(1, row)
               .addTitle("Primärwaffe").xy(3, row)
               .addTitle("Sekundärwaffe").xy(5, row)
               .addTitle("Gadgets").xy(7, row)
               .add(title("Lifepoints")).xy(9, row)
               .add(title("Fähigkeit")).xyw(11, row, 3)
               .addSeparator("").xyw(1, 2, 13);
        
        for (Operator op : selectedOps) {
            row++;
            if (! readWrite)  // Trennlinien nur beim Lesen
                builder.addSeparator("").xyw(1, row, 13);
            row++;
            
            // Name
            JLabel nameLabel = new JLabel(op.getName());
            builder.add(nameLabel).xy(1, row);
            
            final JComboBox<Waffe> primW = new JComboBox<>(new Vector<Waffe>((op.getPrimärwaffen())));
            final JComboBox<Waffe> secW = new JComboBox<>(new Vector<Waffe>(op.getSekundärwaffen()));

            // Primärwaffe
            if (readWrite) {
                primW.setSelectedItem(op.getSelectedPrimärwaffe());
                primW.addActionListener((ActionEvent evt) -> {
                	Waffe prim = (Waffe) primW.getSelectedItem();
                    op.setSelectedPrimärwaffe(prim);
                    //Check ob Spieler ein Rekrut ist
                    if ( prim != null && secW.getSelectedItem() != null && 
                         ((Waffe) secW.getSelectedItem()).getC() != prim.getC() && op.isRekrut()) {
                    	secW.setSelectedItem(null);
                    	showError(Konst.ERROR_REKRUT_PRIWA_SEKWA_CTU);
                    }
                    else
                    	removeError(Konst.ERROR_REKRUT_PRIWA_SEKWA_CTU);
                    showWaffentyp();
                });
                
                // Sofort die einzige Waffe auswählen
                if (op.getPrimärwaffen().size() == 1)
                	primW.setSelectedItem(op.getPrimärwaffen().get(0));
                
                builder.add(primW).xy(3, row);
            } else {
                if (op.getSelectedPrimärwaffe() != null)
                    builder.addLabel(op.getSelectedPrimärwaffe().getName()).xy(3, row);
            }

            // Sekundärwaffe
            if (readWrite) {
                secW.setSelectedItem(op.getSelectedSekundärwaffe());
                secW.addActionListener((ActionEvent evt) -> {
                	Waffe sec = (Waffe) secW.getSelectedItem();
                    op.setSelectedSekundärwaffe(sec);
                    if ( sec != null && primW.getSelectedItem() != null && 
                    	 ((Waffe) primW.getSelectedItem()).getC() != sec.getC() && op.isRekrut()) {
                    	primW.setSelectedItem(null);
                    	showError(Konst.ERROR_REKRUT_PRIWA_SEKWA_CTU);
                    }
                    else
                    	removeError(Konst.ERROR_REKRUT_PRIWA_SEKWA_CTU);
                    showWaffentyp();
                });
                
                // Sofort die einzige Waffe auswählen
                if (op.getSekundärwaffen().size() == 1)
                	secW.setSelectedItem(op.getSekundärwaffen().get(0));

                builder.add(secW).xy(5, row);
            } else {
                if (op.getSelectedSekundärwaffe() != null)
                    builder.addLabel(op.getSelectedSekundärwaffe().getName()).xy(5, row);
            }

            // 1 oder mehrere Gadgets
            if (op instanceof Rekrut) {
                Rekrut rekrut = (Rekrut) op;
                final Predicate<Rekrut> dieserRekrutHatZuVieleGadgets = (r) -> r.getSelectedGadgets().size() > Rekrut.MAX_GADGETS;
                
                if (dieserRekrutHatZuVieleGadgets.test(rekrut)) {
                    nameLabel.setIcon(ValidationResultViewFactory.getErrorIcon());
                    nameLabel.setToolTipText(Konst.ERROR_REKRUT_HAT_ZU_VIELE_GADGETS);
                }
                
                // Checkboxen, um *2* Gadgets auszuwählen
                if (readWrite) {
                    // Mini-Tabelle für Gadgets
                    FormBuilder cbGrid = createGadgetGrid(rekrut, dieserRekrutHatZuVieleGadgets, einRekrutHatZuVieleGadgets, nameLabel);
                    builder.add(cbGrid.build()).xy(7, row);
                } else {
                	List<String> gadgetnamen = new ArrayList<>();
                	for(Gadget g:rekrut.getSelectedGadgets())
                		gadgetnamen.add(g.getName());
                	
                	builder.addLabel(String.join(", ", gadgetnamen)).xy(7, row);
                }
                
                
            } else {
                if (readWrite) {
                    // Combobox, um *1* Gadget auszuwählen
                    JComboBox<Gadget> gad = new JComboBox<>(new Vector<Gadget>(op.getGadgets()));
                    gad.setSelectedItem(op.getSelectedGadget());
                    gad.addActionListener((ActionEvent evt) -> {
                        op.setSelectedGadget((Gadget) gad.getSelectedItem());
                    });
                    builder.add(gad).xy(7, row);
                } else {
                    if (op.getSelectedGadget() != null)
                        builder.addLabel(op.getSelectedGadget().getName()).xy(7, row);
                }
            }
            
            if (readWrite){
            	final SpinnerNumberModel spinnerModel = new SpinnerNumberModel(op.getLifepoints(), 0, op.getMaxLifepoints(), 5);
            	JSpinner spinner = new JSpinner(spinnerModel);
                spinnerModel.addChangeListener((ChangeEvent evt) -> {
                	op.setLifepoints( spinnerModel.getNumber().intValue() );
                });
               
                builder.add(spinner).xy(9, row);
            }
            else {
                builder.addLabel(String.valueOf(op.getLifepoints())).xy(9, row);
            }
            
            if (op.getFähigkeit() != Faehigkeit.KEINE) {
                builder.addLabel(op.getFähigkeit().getName()).xy(11, row);
                if (readWrite) {
                        if (op.getFähigkeit().isAnzahlLimitiert() && op.isFähigkeitÜbrig()) {
                            // Fähigkeit herunterzählen können über Button
                            JButton countDownBtn = new JButton(String.valueOf(op.getFähigkeitAnzahlÜbrig()));
                            countDownBtn.setToolTipText("Fähigkeit verbrauchen");
                            countDownBtn.addActionListener((ActionEvent evt) -> {
                                op.verbraucheFähigkeit();
                                if (op.isFähigkeitÜbrig())
                                    countDownBtn.setText(String.valueOf(op.getFähigkeitAnzahlÜbrig()));
                                else
                                    countDownBtn.setVisible(false);
                                
                            });
                            builder.add(countDownBtn).xy(13, row);
                        }
                }
                else {
                    if (op.getFähigkeit().isAnzahlLimitiert())
                        builder.addLabel(String.valueOf(op.getFähigkeitAnzahlÜbrig())).xy(13, row);
                }
            }
        }

        panel_waffen.add(builder.build());

        frame.getContentPane().validate();

        showWaffentyp();
    }
    
    private FormBuilder createGadgetGrid(Rekrut rekrut, Predicate<Rekrut> dieserRekrutHatZuVieleGadgets, 
            Predicate<List<Operator>> einRekrutHatZuVieleGadgets, JLabel nameLabel) {
        final int numCols = 3;  // Anzahl Checkboxen nebeneinander
        final int numRows = (int) Math.ceil((double) rekrut.getGadgets().size() / (double) numCols);
        FormBuilder builder = FormBuilder.create()
                .columns(String.join(", 3dlu, ", Collections.nCopies(numCols, "pref")))  // n Checkboxen nebeneinander
                .rows(String.join(", $lgap, ", Collections.nCopies(numRows, "p")));

        int col = 1;
        int row = 1;
        for (Gadget gadget : rekrut.getGadgets()) {
            JCheckBox cb = new JCheckBox(gadget.getName());
            cb.setSelected(rekrut.getSelectedGadgets().contains(gadget));
            cb.addActionListener((ActionEvent evt) -> {
                rekrut.toggleGadget(cb.getText());
                
                if (dieserRekrutHatZuVieleGadgets.test(rekrut)) {
                    nameLabel.setIcon(ValidationResultViewFactory.getErrorIcon());
                    nameLabel.setToolTipText(Konst.ERROR_REKRUT_HAT_ZU_VIELE_GADGETS);
                }
                else {
                    nameLabel.setIcon(null);
                    nameLabel.setToolTipText(null);
                }
                
                List<Operator> _ops = (rdbtnAngreifer.isSelected()) ? model.getSelectedAngreifer()
                        : model.getSelectedVerteidiger();
                showErrorIf(() -> einRekrutHatZuVieleGadgets.test(_ops), Konst.ERROR_REKRUT_HAT_ZU_VIELE_GADGETS);
            });
            
            builder.add(cb).xy(2*col-1, row);
            col++;
            if (col > numCols) {
                col = 1;
                row = row + 2;
            }
        }
        
        return builder;
    }

    private void showError(String msg) {
        errors.add(msg);
        refreshErrors();
    }

    private void showErrorIf(BooleanSupplier condition, String msg) {
        if (condition.getAsBoolean()) {
            errors.add(msg);
            refreshErrors();            
        }
        else
        	removeError(msg);
    }
    
    private void removeError(String msg) {
        errors.remove(msg);
        refreshErrors();
    }

    private void refreshErrors() {
        if (errors.isEmpty()) {
            // Fehler-Icon oben entfernen
            removeErrorMarker(angriffOpLabel);
            removeErrorMarker(verteidigungOpLabel);
            
            panel_meldung.setVisible(false);
        }
        else {
            // Fehler-Icon oben anzeigen
            for (String error : errors) {
                if (Konst.ERROR_AKTIVIERE_HÖCHSTENS_N_OPERATOR.equals(error)) {
                    addErrorMarker(angriffOpLabel, error);
                    addErrorMarker(verteidigungOpLabel, error);
                    break;
                }
            }
            
            meldunglabel.setText("<html>" + String.join("<br>", errors.toArray(new String[0])) + "</html>");
            panel_meldung.setVisible(true);
        }
    }
    
    private void addErrorMarker(JLabel label, String error) {
        label.setIcon(ValidationResultViewFactory.getErrorIcon());
        label.setToolTipText(error);
    }
    private void removeErrorMarker(JLabel label) {
        label.setIcon(null);
        label.setToolTipText(null);
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
        // Gegnerteam
        rdbtnAngreifer.setSelected(model.isGegnerteamAngreifer());
        panel_angriff.setVisible(model.isGegnerteamAngreifer());
        rdbtnVerteidiger.setSelected(!model.isGegnerteamAngreifer());
        panel_verteidigung.setVisible(!model.isGegnerteamAngreifer());
        setAVCheckboxes(); // Checkboxen passend zum Model setzen
        fillPanelWaffen(); // Änderungen sichtbar machen
        
        // Team
        spielerlisteController.getView().refresh();
        
        refreshErrors();
    }
    
    
    private void saveScreenshot(File file)
    throws IOException {
    	
        log.info("Erstelle Screenshot...");
        Component comp = frame.getRootPane();
        BufferedImage image = new BufferedImage(comp.getWidth(), comp.getHeight(), BufferedImage.TYPE_INT_RGB);
        // call the Component's paint method, using
        // the Graphics object of the image.
        comp.paint(image.getGraphics()); // alternately use .printAll(..)

        ImageIO.write(image, "PNG", file);
    }
    private void logFehler(String s) {
    	log.warn(s);
    }
    private long timerStart() {
    	long time1 = System.currentTimeMillis();
        return time1;
    }
    private long timerEnd(long time1) {
    	long time2 = System.currentTimeMillis() - time1;
    	 return time2;
    }

}
