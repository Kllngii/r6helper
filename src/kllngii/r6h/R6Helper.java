package kllngii.r6h;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
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
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.jgoodies.forms.builder.FormBuilder;
import com.jgoodies.forms.factories.DefaultComponentFactory;
import com.jgoodies.looks.plastic.PlasticLookAndFeel;
import com.jgoodies.looks.plastic.PlasticXPLookAndFeel;
import com.jgoodies.looks.plastic.theme.ExperienceRoyale;
import com.jgoodies.validation.view.ValidationResultViewFactory;

import kllngii.r6h.model.Einstellungen;
import kllngii.r6h.model.Faehigkeit;
import kllngii.r6h.model.Gadget;
import kllngii.r6h.model.Operator;
import kllngii.r6h.model.R6HelperModel;
import kllngii.r6h.model.R6Map;
import kllngii.r6h.model.Rekrut;
import kllngii.r6h.model.Waffe;
import kllngii.r6h.model.Waffentyp;


public class R6Helper extends KllngiiApplication {
	private static final String ERROR_AKTIVIERE_HÖCHSTENS_N_OPERATOR = "Aktiviere höchstens " + R6HelperModel.MAX_TEAMGRÖSSE + " Operator!";
	private static final String ERROR_REKRUT_HAT_ZU_VIELE_GADGETS = "Ein Rekrut darf höchstens " + Rekrut.MAX_GADGETS + " Gadgets haben!";
	private static final String ERROR_REKRUT_PRIWA_SEKWA_CTU = "Ein Rekrut darf nicht die Waffen von mehr als " + Rekrut.MAX_CTUS + " CTUs haben!";
	
	//TODO Mehrere Channel einführen, um PW geschützt getrennt Programm zu nutzen
    
	//TODO Optional: Fenster auch auf Java FX umstellbar
	//TODO Webobefläche erneuern. Kein Screenshot sondern JSON übertragen -> angular Project
	
	//TODO Drohnen/Kamera Counter
	//TODO Ace und Headshot Counter
	//Wie Fähigkeiten einrichten
	
	//TODO Fähigkeiten Klarnamen brauchen Verwendung
	
    private final Logger log = Logger.getLogger(getClass());

    private final boolean readWrite;
    private JFrame frame;

    private final Einstellungen einstellungen = new Einstellungen();
    private EinstellungsFrame einstellungsFrame = null; // wird bei Bedarf erzeugt

    private R6HelperModel model = new R6HelperModel();
    
    private final DefaultComponentFactory compFactory = new DefaultComponentFactory();

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
     * Launch the application.
     */
    public static void main(String[] args) {
        // Nur-Lese-Modus kann über den ersten Parameter
        // angefordert werden
        boolean readWrite = true;
        if (args != null && args.length >= 1)
            readWrite = Boolean.valueOf(args[0]);

        PlasticLookAndFeel.setPlasticTheme(new ExperienceRoyale());  // ganz gut, farbig
        
        try {
            UIManager.setLookAndFeel(new PlasticXPLookAndFeel());
        } catch (Exception ex) {
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

        // Einstellungen laden:
        try {
            new EinstellungenService().ladeAusPreferences(einstellungen);
        } catch (BackingStoreException e) {
            log.warn("Alte Einstellungen können nicht aus den Preferences geladen werden!", e);
        }

        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setTitle("R6 Helper");

        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension size = new Dimension(940, 630);
        frame.setSize(size.width, size.height);
        frame.setLocation((screensize.width - size.width) / 2, (screensize.height - size.height) / 2);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));
        Container root = new Box(BoxLayout.Y_AXIS);
        frame.getContentPane().add(root);
        
        
        //// Menü-Buttons am rechten Rand ////
        
        FormBuilder menu = FormBuilder.create()
                .columns("pref")
                .rows("p, $lgap, p, $pgap, " +
                      "p, $lgap, p, $pgap, " +
                      "p, $pgap, p")
                .padding("6dlu, 12px, 6dlu, 12px");
        
        
        JButton btnWeb = new JButton("Externe Rainbow Programme");
        JComboBox<R6Map> comboWeb = new JComboBox<R6Map>(R6Map.values());
        if (Desktop.isDesktopSupported()) {
            btnWeb.setEnabled(true);
        } else {
            btnWeb.setEnabled(false);
        }
        menu.add(btnWeb).xy(1, 1);
        menu.add(comboWeb).xy(1, 3);
        btnWeb.addActionListener((ActionEvent evt) -> {
            URL url = null;
            try {
            	
                url = new URL("http://www.r6maps.com/"+comboWeb.getItemAt(comboWeb.getSelectedIndex()).getUrl());

                try {
                	R6DBWindow.main(null);
                    Desktop.getDesktop().browse(url.toURI());
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        });
        
        
        JButton jsonLoadButton = new JButton("Laden");
        jsonLoadButton.addActionListener((ActionEvent evt) -> {
            ladeAusJson();
        });
        menu.add(jsonLoadButton).xy(1, 3);

        if (readWrite) {
            JButton jsonSaveButton = new JButton("Speichern");
            jsonSaveButton.addActionListener((ActionEvent evt) -> {
                speichereInJSON();
                
            });
            menu.add(jsonSaveButton).xy(1, 5);
        }
        JButton settings = new JButton("Einstellungen");
        settings.addActionListener((ActionEvent evt) -> {
            if (einstellungsFrame == null)
                einstellungsFrame = new EinstellungsFrame(einstellungen);
            einstellungsFrame.setVisible(true);
            einstellungsFrame.toFront();
        });
        menu.add(settings).xy(1, 7);
        
        JButton resetBtn = new JButton("Reset");
        resetBtn.addActionListener((ActionEvent evt) -> {
            reset();
        });
        menu.add(resetBtn).xy(1, 9);
        
        frame.getContentPane().add(menu.build());

        
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
                .addSeparator("Gegnerteam").xyw(1, 1, 3)
                .add(rdbtnAngreifer).xy(1, 3)
                .add(rdbtnVerteidiger).xy(3, 3);
        root.add(avPanel.build());

        
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
        root.add(panel_angriff);
        
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
        root.add(panel_verteidigung);
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
        root.add(panel_waffen);
        
        
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
        
        root.add(wArt.build());
        
        
        //// Meldungen ////
        
        root.add(Box.createVerticalStrut(lücke));

        panel_meldung = new JPanel();
        root.add(panel_meldung);

        meldunglabel = new JLabel("");
        meldunglabel.setForeground(Color.RED);
        meldunglabel.setFont(new Font(Font.DIALOG, Font.BOLD, 14));
        panel_meldung.add(meldunglabel);
        panel_meldung.setVisible(false);

        root.add(Box.createVerticalGlue());
        
        frame.setVisible(true);

        // Timer starten:
        final int refreshIntervalS = Math.max(0, einstellungen.getRefreshIntervalS());  // negative Werte krachen
        if(!readWrite) {
            
            // Per Timer (in einem eigenen Thread):
            // Sofort einmal das JSON holen
            // UND später regelmäßig neu einlesen
            Timer refreshTimer = new Timer(refreshIntervalS*1000, (ActionEvent e) -> {
                log.info("Timer feuert - JSON neu einlesen...");
                ladeAusJson();
            });
            refreshTimer.setInitialDelay(0);  // sofort einmal holen
            if (refreshIntervalS == 0)
                refreshTimer.setRepeats(false);  // NUR einmal holen
            else
                log.info("Timer wird erzeugt, um das JSON alle " + einstellungen.getRefreshIntervalS() + " s neu einzulesen.");
            einstellungen.setRefreshTimer(refreshTimer);
            refreshTimer.start();
        }
        else {
            Timer loadOnceTimer = new Timer(0, (ActionEvent e1) -> {
                log.info("JSON nach dem Start einmal einlesen...");
                ladeAusJson();
                
                // Per Timer das Model regelmäßig neu speichern
                Timer refreshTimer = new Timer(einstellungen.getRefreshIntervalS()*1000, (ActionEvent e2) -> {
                    log.info("Timer feuert - JSON neu speichern");
                    speichereInJSON();
                });
                einstellungen.setRefreshTimer(refreshTimer);
                if (refreshIntervalS != 0) {
                    log.info("Timer wird gestartet, um das JSON alle " + einstellungen.getRefreshIntervalS() + " s zu speichern.");
                    refreshTimer.start();
                }

            });
            loadOnceTimer.setRepeats(false);
            loadOnceTimer.start();
            
        }
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
    	model = new R6HelperModel();
    	errors.clear();
    	speichereInJSON();
    	refreshView();
    }
    private void speichereInJSON() {
    	try {
    		final long time1 = System.currentTimeMillis();
            speicherService.speichereJson(model, errors, einstellungen.getDateiOutput());
            final long time2 = System.currentTimeMillis();
            log.info("Speicherzeit:  "+(time2-time1)+"ms"); 
            
        } catch (Exception ex) {
            log.error("Fehler beim Speichern des JSON!", ex);
            showError("Fehler beim Speichern des JSON: "
                    + StringUtils.defaultIfEmpty(ex.getMessage(), ex.toString()));
        }
    	
    	try {
    	    saveScreenshot(new File(einstellungen.getDateiOutput().getParentFile(), "R6Screenshot.png"));
    	} catch (Exception ex) {
            log.error("Fehler beim Speichern des Screenshots!", ex);
            showError("Fehler beim Speichern des Screenshots: "
                    + StringUtils.defaultIfEmpty(ex.getMessage(), ex.toString()));
        }
		
	}

	private void ladeAusJson() {
        try {
        	// Im richtigen Thread (EDT) den Mauszeiger auf "Warten" setzen:
        	if (! SwingUtilities.isEventDispatchThread())
        		SwingUtilities.invokeAndWait(() -> frame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)));
        	else
        		frame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            log.info("Lade JSON...");
        	final long time1 = System.currentTimeMillis(); 
            SpeicherService.ModelWithErrors mwe = speicherService.ladeJson(einstellungen.getUriInput());
            model = mwe.getModel();
            errors.clear();
            errors.addAll(mwe.getErrors());
            
            refreshView();
            final long time2 = System.currentTimeMillis();
            log.info("Ladezeit:  "+(time2-time1)+"ms"); 
        } catch (Exception ex) {
            log.error("Fehler beim Laden des JSON!", ex);
            showError("Fehler beim Laden des JSON: " + StringUtils.defaultIfEmpty(ex.getMessage(), ex.toString()));
        }
        finally {
        	SwingUtilities.invokeLater(() -> frame.setCursor(Cursor.getDefaultCursor()));
        }

    }

    private void showWaffentyp() {
        EnumSet<Waffentyp> interessierendeTypen;
        List<Operator> selectedOperators;

        if (rdbtnAngreifer.isSelected()) {
            interessierendeTypen = EnumSet.of(Waffentyp.PISTOLE, Waffentyp.REIHEN, Waffentyp.SHOTGUN, Waffentyp.STURM,
                    Waffentyp.DMR, Waffentyp.LMG);
            selectedOperators = model.getSelectedAngreifer();
        } else {
            interessierendeTypen = EnumSet.of(Waffentyp.PISTOLE, Waffentyp.REIHEN, Waffentyp.SHOTGUN, Waffentyp.MP);
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
                ERROR_AKTIVIERE_HÖCHSTENS_N_OPERATOR);

        // Hat einer zu viele Gadgets?
        final Predicate<List<Operator>> einRekrutHatZuVieleGadgets = (_ops) -> _ops.stream()
                .filter(o -> o instanceof Rekrut).anyMatch(r -> r.getSelectedGadgets().size() > Rekrut.MAX_GADGETS);
        showErrorIf(() -> einRekrutHatZuVieleGadgets.test(selectedOps),
                ERROR_REKRUT_HAT_ZU_VIELE_GADGETS);

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
        
        JLabel lifepointsLabel = compFactory.createTitle("Lifepoints");
        lifepointsLabel.setToolTipText("Lifepoints");
        JLabel fähigkeitLabel = compFactory.createTitle("Fähigkeit");
        fähigkeitLabel.setToolTipText("Fähigkeit");

        int row = 1;
        builder.addTitle("Operator").xy(1, row)
               .addTitle("Primärwaffe").xy(3, row)
               .addTitle("Sekundärwaffe").xy(5, row)
               .addTitle("Gadgets").xy(7, row)
               .add(lifepointsLabel).xy(9, row)
               .add(fähigkeitLabel).xyw(11, row, 3)
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
                    if ( prim != null && secW.getSelectedItem() != null && 
                         ((Waffe) secW.getSelectedItem()).getC() != prim.getC() ) {
                    	secW.setSelectedItem(null);
                    	showError(ERROR_REKRUT_PRIWA_SEKWA_CTU);
                    }
                    else
                    	removeError(ERROR_REKRUT_PRIWA_SEKWA_CTU);
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
                    	 ((Waffe) primW.getSelectedItem()).getC() != sec.getC() ) {
                    	primW.setSelectedItem(null);
                    	showError(ERROR_REKRUT_PRIWA_SEKWA_CTU);
                    }
                    else
                    	removeError(ERROR_REKRUT_PRIWA_SEKWA_CTU);
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
                    nameLabel.setToolTipText(ERROR_REKRUT_HAT_ZU_VIELE_GADGETS);
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
                builder.addLabel(op.getFähigkeit().toString()).xy(11, row);
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
                    nameLabel.setToolTipText(ERROR_REKRUT_HAT_ZU_VIELE_GADGETS);
                }
                else {
                    nameLabel.setIcon(null);
                    nameLabel.setToolTipText(null);
                }
                
                List<Operator> _ops = (rdbtnAngreifer.isSelected()) ? model.getSelectedAngreifer()
                        : model.getSelectedVerteidiger();
                showErrorIf(() -> einRekrutHatZuVieleGadgets.test(_ops), ERROR_REKRUT_HAT_ZU_VIELE_GADGETS);
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
                if (ERROR_AKTIVIERE_HÖCHSTENS_N_OPERATOR.equals(error)) {
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
        rdbtnAngreifer.setSelected(model.isGegnerteamAngreifer());
        panel_angriff.setVisible(model.isGegnerteamAngreifer());
        rdbtnVerteidiger.setSelected(!model.isGegnerteamAngreifer());
        panel_verteidigung.setVisible(!model.isGegnerteamAngreifer());
        setAVCheckboxes(); // Checkboxen passend zum Model setzen
        fillPanelWaffen(); // Änderungen sichtbar machen
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

}
