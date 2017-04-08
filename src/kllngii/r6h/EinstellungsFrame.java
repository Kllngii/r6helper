package kllngii.r6h;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPasswordField;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JTextField;

public class EinstellungsFrame {

	public JFrame eFrame;
	public JFrame geteFrame() {
		return eFrame;
	}

	public void seteFrame(JFrame eFrame) {
		this.eFrame = eFrame;
	}

	private JPasswordField passwordField;
	private JTextField txtAdresse;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					EinstellungsFrame window = new EinstellungsFrame();
					window.eFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

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
		eFrame = new JFrame();
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension size = new Dimension(100, 100);
		eFrame.setSize(size.width, size.height);
		eFrame.setLocation((screensize.width-size.width)/2, (screensize.height-size.height)/2);
		eFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Container _root = eFrame.getContentPane();
//		_root.setLayout(new BoxLayout(_root, BoxLayout.X_AXIS));
		_root.setLayout(new FlowLayout(FlowLayout.LEADING));
		
		Box root = new Box(BoxLayout.Y_AXIS);
		_root.add(root, BorderLayout.PAGE_END);
		
		txtAdresse = new JTextField();
		txtAdresse.setText("Adresse");
		
		root.add(txtAdresse);
		
		passwordField = new JPasswordField();
		passwordField.setText("Passwort");
		root.add(passwordField);
		
		
	}

}
