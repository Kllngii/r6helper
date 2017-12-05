package kllngii.r6h;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Desktop;
import java.awt.Window.Type;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import javax.swing.JTextField;
import javax.swing.JButton;

public class R6DBWindow {

	private JFrame frmRdb;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@SuppressWarnings("synthetic-access")
			@Override
			public void run() {
				try {
					R6DBWindow window = new R6DBWindow();
					window.frmRdb.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public R6DBWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmRdb = new JFrame();
		frmRdb.setType(Type.POPUP);
		frmRdb.setTitle("R6DB");
		frmRdb.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmRdb.setBounds(100, 100, 450, 300);
		frmRdb.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 434, 1);
		frmRdb.getContentPane().add(panel);
		panel.setLayout(null);
		
		textField = new JTextField();
		textField.setToolTipText("Spielernamen");
		textField.setBounds(121, 83, 178, 20);
		frmRdb.getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnAbsenden = new JButton("Absenden");
		btnAbsenden.setBounds(165, 194, 89, 23);
		btnAbsenden.addActionListener((ActionEvent evt) -> {
			try {
				URL url = new URL(kllngii.r6h.model.R6DB.createUrl(textField.getText()));
				try {
					Desktop.getDesktop().browse(url.toURI());
				} catch (IOException | URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		});
		frmRdb.getContentPane().add(btnAbsenden);
	}
}
