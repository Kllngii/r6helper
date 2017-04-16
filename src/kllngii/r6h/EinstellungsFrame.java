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
	private JRadioButton rbSchreibenPerFtp = new JRadioButton("FTP");
	
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
		
		
		FormBuilder builder = FormBuilder.create()
		        .columns("left:pref, 3dlu, pref, 3dlu, 200dlu")
		        .rows("p, $pgap, p, $lgap, p, 20dlu, " +
		              "p, $pgap, p, $pgap, p, $lgap, p, $lgap, p, $lgap, p, $lgap, p, $lgap")
		        .debug(false)
		        .padding("6dlu, 6dlu, 6dlu, 6dlu")
		        
		        // -- Lesen
		        .addSeparator("Lesen").xyw(1, 1, 5)
		        .add(rbLesenAusUri).xy(1, 3).add(uriInput).xyw(3, 3, 3)
		        .add(rbLesenPerFtp).xy(1, 5).addLabel("gleiche Einstellungen wie unten").xyw(3, 5, 3)
		        
		        // -- Schreiben
		        .addSeparator("Schreiben").xyw(1, 7, 5)
		        .add(rbSchreibenInDatei).xy(1, 9).add(dateiOutput).xyw(3, 9, 3)
        		.add(rbSchreibenPerFtp).xy(1, 11)
        		    .addLabel("Server").xy(3, 11).add(ftpHost).xy(5, 11)
        		    .addLabel("User").xy(3, 13).add(ftpUser).xy(5, 13)
        		    .addLabel("Passwort").xy(3, 15).add(ftpPwd).xy(5, 15)
		        ;
		        
		getContentPane().add(builder.build());
	}

}
