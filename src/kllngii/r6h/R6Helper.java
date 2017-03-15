package kllngii.r6h;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.UIManager;

import kllngii.r6h.model.Operator;
import kllngii.r6h.model.R6HelperModel;
import kllngii.r6h.model.Waffe;


public class R6Helper {
	
	private JFrame frame;
	
	private R6HelperModel model = new R6HelperModel();

	private JPanel panel_angriff;
	
	private JPanel panel_verteidigung;
	
	private JPanel panel_waffen;
	
	private JPanel panel_meldung;
	
	private Map<JCheckBox, Operator> angriffCheckboxen;
	private Map<JCheckBox, Operator> verteidigungCheckboxen;

	private JRadioButton rdbtnAngreifer;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
	    try {
	        // Set cross-platform Java L&F (also called "Metal")
	        UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
	        
//	        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    }
	    catch (Exception ex) {
	        // Aufgeben...
	        throw new RuntimeException(ex);
	    }
        
		EventQueue.invokeLater(new Runnable() {
			@SuppressWarnings("unused")
            @Override
            public void run() {
				new R6Helper();
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
		frame = new JFrame();
		frame.setTitle("R6 Helper");
		
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension size = new Dimension(700, 600);
		frame.setSize(size.width, size.height);
		frame.setLocation((screensize.width-size.width)/2, (screensize.height-size.height)/2);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		//panel.setAlignmentX(0);
		
		JLabel lblArt = new JLabel("Gegnerteam:");
		panel.add(lblArt);
		
		rdbtnAngreifer = new JRadioButton("Angreifer");
		panel.add(rdbtnAngreifer);
		rdbtnAngreifer.setSelected(true);
		
		JRadioButton rdbtnVerteidiger = new JRadioButton("Verteidiger");
		panel.add(rdbtnVerteidiger);
		
		ButtonGroup art = new ButtonGroup();
	    art.add(rdbtnAngreifer);
	    art.add(rdbtnVerteidiger);
	    
		panel_angriff = new JPanel();
		frame.getContentPane().add(panel_angriff);
		
		panel_verteidigung = new JPanel();
		frame.getContentPane().add(panel_verteidigung);
		
		panel_angriff.add(new JLabel("Operator:"));
		panel_verteidigung.add(new JLabel("Operator:"));
		
		angriffCheckboxen = new HashMap<>();
		for(Operator op:model.getAngreifer()){
			JCheckBox checkBox = new JCheckBox(op.getName());
			angriffCheckboxen.put(checkBox, op);
			panel_angriff.add(checkBox);
			checkBox.addActionListener((ActionEvent evt)->{
				model.toggleSelected(op);
				fillPanelWaffen();
			});
		}
		verteidigungCheckboxen = new HashMap<>();
		for(Operator op:model.getVerteidiger()){
			JCheckBox checkBox = new JCheckBox(op.getName());
			verteidigungCheckboxen.put(checkBox, op);
			panel_verteidigung.add(checkBox);
			checkBox.addActionListener((ActionEvent evt)->{
				model.toggleSelected(op);
				fillPanelWaffen();
			});
		}
		panel_verteidigung.setVisible(false);
		
		
		
		// Event-Handler für Auswahl Angriff/Verteidigung:
		rdbtnAngreifer.addActionListener((ActionEvent evt) -> {
				panel_angriff.setVisible(true);
				panel_verteidigung.setVisible(false);
				fillPanelWaffen();
		});
		rdbtnVerteidiger.addActionListener((ActionEvent evt) -> {
			panel_angriff.setVisible(false);
			panel_verteidigung.setVisible(true);
			fillPanelWaffen();
		});
//		panel_angriff.setAlignmentX(0);
//		panel_verteidigung.setAlignmentX(0);
		
		panel_waffen = new JPanel();
		panel_waffen.setLayout(new BoxLayout(panel_waffen, BoxLayout.Y_AXIS));
		frame.getContentPane().add(panel_waffen);
		
		panel_meldung = new JPanel();
		frame.getContentPane().add(panel_meldung);
		
		JLabel meldunglabel = new JLabel("Aktiviere höchstens "+R6HelperModel.MAX_TEAMGRÖSSE+" Operator!");
		meldunglabel.setForeground(Color.RED);
		meldunglabel.setFont(new Font(Font.DIALOG, Font.BOLD, 20));
		panel_meldung.add(meldunglabel);
		panel_meldung.setVisible(false);
		
		frame.setVisible(true);
	}
	
	
	@SuppressWarnings("static-access")
	private void fillPanelWaffen() {
		
		List<Operator> ops = (rdbtnAngreifer.isSelected()) ? model.getSelectedAngreifer() : model.getSelectedVerteidiger();
		if (ops.size() > model.MAX_TEAMGRÖSSE) {
			panel_meldung.setVisible(true);
			panel_waffen.setVisible(false);
		}
		else {
			panel_meldung.setVisible(false);
			panel_waffen.setVisible(true);

			panel_waffen.removeAll();
			
			for (Operator op : ops) {
				JPanel panel = new JPanel();
				panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
				
				Dimension dim = new Dimension(100, 32);
				
				JLabel label = new JLabel(op.getName());
				label.setPreferredSize(dim);
				panel.add(label);
				
				JComboBox<String> primW = new JComboBox<>( waffennamen(op.getPrimärwaffen()));
				primW.setPreferredSize(dim);
				//FIXME Die ausgewählte Waffe vom letzten Mal wieder einstellen
				//FIXME ChangeListener dafür, um das Model zu ändern
				panel.add(primW);
				
				JComboBox<String> secW = new JComboBox<>(waffennamen(op.getSekundärwaffen()));
				secW.setPreferredSize(dim);
				panel.add(secW);
				
				panel_waffen.add(panel);
			}
		}
		
		frame.getContentPane().validate();
	}
	
	private Vector<String> waffennamen(List<Waffe> waffen) {
		Vector<String> namen = new Vector<>(waffen.size());
		for (Waffe waffe : waffen)
			namen.add(waffe.getName());
		return namen;
	}
}
