package kllngii.r6h;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.prefs.BackingStoreException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.jgoodies.forms.builder.FormBuilder;

import kllngii.r6h.model.Einstellungen;

public class EinstellungsFrame extends JFrame {

    private static final long serialVersionUID = 4749625493367013570L;
    
    private final Logger log = Logger.getLogger(getClass());

    
    /** Das Model */
    private Einstellungen model;

    private JRadioButton rbLesenAusUri = new JRadioButton("URI");
    private JRadioButton rbLesenPerFtp = new JRadioButton("FTP");
	
	private JTextField uriInput = new JTextField();
	private JTextField refreshInterval = new JTextField();

	private JRadioButton rbSchreibenInDatei = new JRadioButton("Datei");
	private JRadioButton rbSchreibenPerFtp = new JRadioButton("FTP");
	
	private JTextField dateiOutput = new JTextField();
	private JTextField ftpHost = new JTextField();
	private JTextField ftpUser = new JTextField();
	private JTextField ftpPwd = new JTextField();
	
	private JButton saveButton = new JButton("Speichern");
	private JButton cancelButton = new JButton("Verwerfen");
	

	/**
	 * Create the application.
	 */
	public EinstellungsFrame(Einstellungen e) {
	    this.model = e;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension size = new Dimension(640, 480);
		setSize(size.width, size.height);
		setLocation((screensize.width-size.width)/2, (screensize.height-size.height)/2);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		//TODO FTP implementieren
		//TODO Autosave an/aus Schalter
		rbLesenPerFtp.setEnabled(false);
		rbSchreibenPerFtp.setEnabled(false);
		
		JLabel refreshLabel = new JLabel("Aktualisiere alle");
		refreshLabel.setToolTipText("0 oder negativ, um das automatische Laden auszuschalten");
		refreshInterval.setToolTipText(refreshLabel.getToolTipText());
		
		
		// Layout aufbauen:
		FormBuilder builder = FormBuilder.create()
		        .columns("left:pref, 3dlu, pref, 3dlu, 200dlu")
		        .rows(// -- Lesen
   	                  "p, $pgap, p, $lgap, p, $lgap, p, " +
		              // -- Schreiben
		              "20dlu, " +
		              "p, $pgap, p, $pgap, p, $lgap, p, $lgap, p, " +
		              // -- Buttons
		              "30dlu, " +
		              "p")
		        .debug(false)
		        .padding("6dlu, 6dlu, 6dlu, 6dlu")
		        
		        // -- Lesen
		        .addSeparator("Lesen").xyw(1, 1, 5)
		        .add(rbLesenAusUri).xy(1, 3).add(uriInput).xyw(3, 3, 3)
		        .add(rbLesenPerFtp).xy(1, 5).addLabel("gleiche Einstellungen wie unten").xyw(3, 5, 3)
		        .add(refreshLabel).xy(1, 7).add(refreshInterval).xy(3, 7).addLabel("s").xy(5, 7)
		        
		        // -- Schreiben
		        .addSeparator("Schreiben").xyw(1, 9, 5)
		        .add(rbSchreibenInDatei).xy(1, 11).add(dateiOutput).xyw(3, 11, 3)
        		.add(rbSchreibenPerFtp).xy(1, 13)
        		    .addLabel("Server").xy(3, 13).add(ftpHost).xy(5, 13)
        		    .addLabel("User").xy(3, 15).add(ftpUser).xy(5, 15)
        		    .addLabel("Passwort").xy(3, 17).add(ftpPwd).xy(5, 17)
        		    
        		// -- Buttons
        		.addBar(saveButton, cancelButton).xyw(1, 19, 5)
		        ;
		        
		getContentPane().add(builder.build());
		
		
		// RadioButtons gruppieren:
		ButtonGroup lesenGroup = new ButtonGroup();
		lesenGroup.add(rbLesenAusUri);
		lesenGroup.add(rbLesenPerFtp);
		
		ButtonGroup schreibenGroup = new ButtonGroup();
		schreibenGroup.add(rbSchreibenInDatei);
		schreibenGroup.add(rbSchreibenPerFtp);
		
		
		// Stand des Models im View anzeigen:
		modelToView();
		
		
		// Buttons unten:
		saveButton.setToolTipText("Speichert auf diesem Computer");
		saveButton.addActionListener((ActionEvent evt) -> {
		    try {
		        viewToModel();
		        new EinstellungenService().speichereInPreferences(model);
		        setVisible(false);
		    }
		    catch (IllegalArgumentException ex) {
		        // Dem User das Problem anzeigen:
		        JOptionPane.showMessageDialog(this, ex.getMessage(), "So kann ich nicht speichern!", JOptionPane.WARNING_MESSAGE);
		    }
		}); 
		
		cancelButton.setToolTipText("Verwirft alle Änderungen seit dem letzten Speichern");
		cancelButton.addActionListener((ActionEvent evt) -> {
		    try {
                new EinstellungenService().ladeAusPreferences(model);
                modelToView();
                setVisible(false);
            } catch (BackingStoreException e) {
                log.warn("Alte Einstellungen können nicht aus den Preferences geladen werden!", e);
            }
        }); 
	}
	
	/**
	 * Kopiert die Werte aus dem View in das Model um,
	 * sofern sie fachlich richtig sind.
	 * @throws IllegalArgumentException, wenn mindestens ein fachlicher Fehler aufgetreten ist.
	 */
	private void viewToModel()
	throws IllegalArgumentException {
	    // -- Lesen
	    try {
	        model.setUriInput(new URI(uriInput.getText()));
	    }
	    catch (URISyntaxException ex) {
	        String msg = "Ungültige URI: " + uriInput.getText();
	        log.warn(msg);
	        throw new IllegalArgumentException(msg);
	    }
	    
	    model.setFtpInput(rbLesenPerFtp.isSelected());
	    
	    try {
	        String interval = StringUtils.trim(refreshInterval.getText()); 
	        interval = StringUtils.defaultIfBlank(interval, String.valueOf(Einstellungen.DEFAULT_REFRESH_INTERVAL_S));
	        model.setRefreshIntervalS(Integer.parseInt(interval));
	    }
	    catch (NumberFormatException ex) {
	        String msg = "Bitte nur ganze Sekunden eingeben.";
	        log.warn(msg, ex);
	        throw new IllegalArgumentException(msg);
	    }
	    refreshInterval.setText(String.valueOf(model.getRefreshIntervalS()));
	    
	    
	    // -- Schreiben
	    model.setDateiOutput(new File(dateiOutput.getText()));
	    model.setFtpOutput(rbSchreibenPerFtp.isSelected());
	    model.setFtpHost(ftpHost.getText());
	    model.setFtpUser(ftpUser.getText());
	    model.setFtpPwd(ftpPwd.getText());
	}

    private void modelToView() {
        // -- Lesen
        uriInput.setText(model.getUriInput().toString());
        if (model.isFtpInput())
            rbLesenPerFtp.setSelected(true);
        else
            rbLesenAusUri.setSelected(true);
        refreshInterval.setText("" + model.getRefreshIntervalS());
        
        // -- Speichern
        dateiOutput.setText((model.getDateiOutput() == null) ? "" : model.getDateiOutput().toString());
        if (model.isFtpOutput())
            rbSchreibenPerFtp.setSelected(true);
        else
            rbSchreibenInDatei.setSelected(true);
        ftpHost.setText(model.getFtpHost());
        ftpUser.setText(model.getFtpUser());
        ftpPwd.setText(model.getFtpPwd());
    }
}
