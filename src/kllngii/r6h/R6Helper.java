package kllngii.r6h;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import kllngii.r6h.model.Operator;
import kllngii.r6h.model.R6HelperModel;


public class R6Helper {
	
	private JFrame frmRHelper;
	
	private R6HelperModel model = new R6HelperModel();

	private JPanel panel_angriff;

	private JPanel panel_verteidigung;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					R6Helper window = new R6Helper();
					window.frmRHelper.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public R6Helper() {
		initialize();
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmRHelper = new JFrame();
		frmRHelper.setTitle("R6 Helper");
		frmRHelper.setBounds(100, 100, 450, 300);
		frmRHelper.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmRHelper.getContentPane().setLayout(new BoxLayout(frmRHelper.getContentPane(), BoxLayout.Y_AXIS));
		
		JPanel panel = new JPanel();
		frmRHelper.getContentPane().add(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		JLabel lblArt = new JLabel("Gegnerteam:");
		panel.add(lblArt);
		
		JRadioButton rdbtnAngreifer = new JRadioButton("Angreifer");
		rdbtnAngreifer.setSelected(true);
		rdbtnAngreifer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				panel_angriff.setVisible(true);
				panel_verteidigung.setVisible(false);
			}
		});
		
		panel.add(rdbtnAngreifer);
		
		JRadioButton rdbtnVerteidiger = new JRadioButton("Verteidiger");
		panel.add(rdbtnVerteidiger);
		
		rdbtnVerteidiger.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				panel_angriff.setVisible(false);
				panel_verteidigung.setVisible(true);
			}
		});
		ButtonGroup art = new ButtonGroup();
	    art.add(rdbtnAngreifer);
	    art.add(rdbtnVerteidiger);
	    
		panel_angriff = new JPanel();
		frmRHelper.getContentPane().add(panel_angriff);
		
		panel_verteidigung = new JPanel();
		frmRHelper.getContentPane().add(panel_verteidigung);
		
		panel_angriff.add(new JLabel("Operator:"));
		panel_verteidigung.add(new JLabel("Operator:"));
		
		for(Operator op:model.getAngreifer()){
			JCheckBox checkBox = new JCheckBox(op.getName());
			panel_angriff.add(checkBox);
		}
		for(Operator op:model.getVerteidiger()){
			JCheckBox checkBox = new JCheckBox(op.getName());
			panel_verteidigung.add(checkBox);
		}
		panel_verteidigung.setVisible(false);
		
		
	}
}
