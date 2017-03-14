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


public class R6Helper {

	private JFrame frmRHelper;
	

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
				System.out.println("Angreifer wurde gewählt");
			}
		});
		panel.add(rdbtnAngreifer);
		
		JRadioButton rdbtnVerteidiger = new JRadioButton("Verteidiger");
		panel.add(rdbtnVerteidiger);
		
		ButtonGroup art = new ButtonGroup();
	    art.add(rdbtnAngreifer);
	    art.add(rdbtnVerteidiger);
	        
		JPanel panel_2 = new JPanel();
		frmRHelper.getContentPane().add(panel_2);
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.X_AXIS));
		
		JPanel panel_1 = new JPanel();
		frmRHelper.getContentPane().add(panel_1);
		
		JLabel lblNewLabel = new JLabel("Operator:");
		panel_1.add(lblNewLabel);
		
		String a = null;
		String b = null;
		String c = null;
		String d = null;
		String e = null;
		
		String f = null;
		String g = null;
		String h = null;
		String i = null;
		String j = null;
		
		String k = null;
		String l = null;
		String m = null;
		String n = null;
		String o = null;
		
		if(rdbtnAngreifer.isSelected()){
			a = "Sledge";
			b = "Thatcher";
			c = "Ash";
			d = "Thermite";
			e = "Twitch";
			
			f = "Montagne";
			g = "Glaz";
			h = "Fuze";
			i = "Blitz";
			j = "IQ";
			
			k = "Buck";
			l = "Capitao";
			m = "Hibana";
			n = "Jackal";
			o = "Blackbeard";
		}
		
		
		
		JCheckBox checkBox = new JCheckBox(a);
		panel_1.add(checkBox);
		
		JCheckBox checkBox_1 = new JCheckBox(b);
		panel_1.add(checkBox_1);
		
		JCheckBox checkBox_2 = new JCheckBox(c);
		panel_1.add(checkBox_2);
		
		JCheckBox checkBox_3 = new JCheckBox(d);
		panel_1.add(checkBox_3);
		
		JCheckBox checkBox_4 = new JCheckBox(e);
		panel_1.add(checkBox_4);
		
		JCheckBox checkBox_5 = new JCheckBox(f);
		panel_1.add(checkBox_5);
		
		JCheckBox checkBox_6 = new JCheckBox(g);
		panel_1.add(checkBox_6);
		
		JCheckBox checkBox_7 = new JCheckBox(h);
		panel_1.add(checkBox_7);
		
		JCheckBox checkBox_8 = new JCheckBox(i);
		panel_1.add(checkBox_8);
		
		JCheckBox checkBox_9 = new JCheckBox(j);
		panel_1.add(checkBox_9);
		
		JCheckBox checkBox_10 = new JCheckBox(k);
		panel_1.add(checkBox_10);
		
		JCheckBox checkBox_11 = new JCheckBox(l);
		panel_1.add(checkBox_11);
		
		JCheckBox checkBox_12 = new JCheckBox(m);
		panel_1.add(checkBox_12);
		
		JCheckBox checkBox_13 = new JCheckBox(n);
		panel_1.add(checkBox_13);
		
		JCheckBox checkBox_14 = new JCheckBox(o);
		panel_1.add(checkBox_14);
	}
}
