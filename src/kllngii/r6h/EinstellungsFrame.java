package kllngii.r6h;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.jgoodies.forms.builder.FormBuilder;

public class EinstellungsFrame extends JFrame {

    private static final long serialVersionUID = 4749625493367013570L;

    private JRadioButton rbLesenAusUri = new JRadioButton("URI");
    private JRadioButton rbLesenPerFtp = new JRadioButton("FTP");
	
	private JTextField uriInput = new JTextField();

	private JRadioButton rbSchreibenInDatei = new JRadioButton("Datei");
	private JRadioButton rbSchreibenPerFtp = new JRadioButton("FTP-Server");
	
	private JTextField dateiOutput = new JTextField();
	private JTextField ftpHost = new JTextField();
	private JTextField ftpUser = new JTextField();
	private JTextField ftpPwd = new JTextField();

	/**
	 * Create the application.
	 */
	public EinstellungsFrame() {
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
		
			/*
		 * * <pre>
 *     .columns("pref, $lcgap, %sdlu, p, p", "50")  // Format string
 *     .columnGroup(4, 5)                           // Grouping short hand
 *     .debug(true)                                 // Installs FormDebugPanel
 * 
 *     .add("Title:")         .xy(1, 1)             // Implicitly created label
 *     .add("&Price:")        .xy(1, 1)             // Label with mnemonic
 * 
 *     .add(hasCountry, combo).xy(3, 1)             // Conditional adding
 * 
 *     .add(aTable)           .xywh(1, 1, 3, 5)    // Auto-wrapped with scrollpane
 *     .addScrolled(aTextArea).xywh(1, 1, 1, 3)    // scrollpane shorthand
 * 
 *     .addBar(newBtn, editBtn, deleteBtn).xy(1, 5) // button bar
 *     .addBar(landscapeRadio, portraitRadio).xy(1, 1) // Radio button bar
		 */
		
		FormBuilder builder = FormBuilder.create()
		        .columns("left:70dlu, $lcgap, 200dlu")
		        .rows("p, $pgap, p, $lgap, p, 20dlu, " +
		              "p, $pgap, p, $pgap, p, $lgap, p, $lgap, p, $lgap, p, $lgap, p, $lgap")
		        .debug(false)
		        .padding("6dlu, 6dlu, 6dlu, 6dlu")
		        
		        // -- Lesen
		        .addSeparator("Lesen").xyw(1, 1, 3)
		        .add(rbLesenAusUri).xy(1, 3).add(uriInput).xy(3, 3)
		        .add(rbLesenPerFtp).xy(1, 5).addLabel("gleicher Server wie unten").xy(3, 5)
		        
		        // -- Schreiben
		        .addSeparator("Schreiben").xyw(1, 7, 3)
		        .add(rbSchreibenInDatei).xy(1, 9).add(dateiOutput).xy(3, 9)
        		.add(rbSchreibenPerFtp).xy(1, 11).add(ftpHost).xy(3, 11)
        		    .add(ftpUser).xy(3, 13)
        		    .add(ftpPwd).xy(3, 15)
		        ;
		        
		getContentPane().add(builder.build());
	}

}
