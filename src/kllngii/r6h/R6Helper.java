package kllngii.r6h;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Vector;
import java.util.function.BooleanSupplier;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.UIManager;

import kllngii.r6h.model.Gadget;
import kllngii.r6h.model.Operator;
import kllngii.r6h.model.R6HelperModel;
import kllngii.r6h.model.Rekrut;
import kllngii.r6h.model.Waffe;


public class R6Helper {
	
	private JFrame frame;
	
	private R6HelperModel model = new R6HelperModel();

	private JPanel panel_angriff;
	
	private JPanel panel_verteidigung;
	
	private Box panel_waffen;
	
	private JPanel panel_meldung;
	
	private Map<JCheckBox, Operator> angriffCheckboxen;
	private Map<JCheckBox, Operator> verteidigungCheckboxen;

	private JRadioButton rdbtnAngreifer;
	
	private final int lücke = 12;

	private JLabel meldunglabel;
	
	private SortedSet<String> errors = new TreeSet<>();
	

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
		Dimension size = new Dimension(1000, 600);
		frame.setSize(size.width, size.height);
		frame.setLocation((screensize.width-size.width)/2, (screensize.height-size.height)/2);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container root = frame.getContentPane();
		root.setLayout(new BoxLayout(root, BoxLayout.Y_AXIS));
		
		
		//// Ebene 1 ////
		
		root.add(Box.createVerticalStrut(lücke));
		
		JPanel panel = new JPanel();
		root.add(panel);
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

	    
        //// Ebene 2 ////
        
        root.add(Box.createVerticalStrut(lücke));
	    
		panel_angriff = new JPanel();
//		panel_angriff.setBorder(new LineBorder(Color.BLACK));
		panel_angriff.setMaximumSize(new Dimension(999999, 120));
		panel_angriff.setPreferredSize(new Dimension(size.width, 120));
		root.add(panel_angriff);
		
		panel_verteidigung = new JPanel();
		panel_verteidigung.setMaximumSize(new Dimension(999999, 120));
		panel_verteidigung.setPreferredSize(new Dimension(size.width, 120));
		root.add(panel_verteidigung);
		
		panel_angriff.add(Box.createHorizontalStrut(lücke));
		panel_angriff.add(new JLabel("Operator:"));
		
		panel_verteidigung.add(Box.createHorizontalStrut(lücke));
		panel_verteidigung.add(new JLabel("Operator:"));
		
		angriffCheckboxen = new HashMap<>();
		for (Operator op : model.getAngreifer()) {
			JCheckBox checkBox = new JCheckBox(op.getName());
			angriffCheckboxen.put(checkBox, op);
			panel_angriff.add(checkBox);
			checkBox.addActionListener((ActionEvent evt)->{
				model.toggleSelected(op);
				fillPanelWaffen();
			});
		}
		verteidigungCheckboxen = new HashMap<>();
		for (Operator op : model.getVerteidiger()) {
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

		
        //// Ebene 3 ////
        
        root.add(Box.createVerticalStrut(lücke));

		panel_waffen = new Box(BoxLayout.Y_AXIS);
//		panel_waffen.setBorder(new LineBorder(Color.BLACK));
		root.add(panel_waffen);
		
		panel_meldung = new JPanel();
		root.add(panel_meldung);
		
		meldunglabel = new JLabel("");
		meldunglabel.setForeground(Color.RED);
		meldunglabel.setFont(new Font(Font.DIALOG, Font.BOLD, 20));
		panel_meldung.add(meldunglabel);
		panel_meldung.setVisible(false);
		
		root.add(Box.createVerticalGlue());
		
		frame.setVisible(true);
	}
	
	
	@SuppressWarnings("static-access")
	private void fillPanelWaffen() {
	    
	    clearErrors();
		
		List<Operator> ops = (rdbtnAngreifer.isSelected()) ? model.getSelectedAngreifer() : model.getSelectedVerteidiger();
		showErrorIf(() -> ops.size() > model.MAX_TEAMGRÖSSE,
		        "Aktiviere höchstens "+R6HelperModel.MAX_TEAMGRÖSSE+" Operator!");

		panel_waffen.removeAll();
		
		for (Operator op : ops) {
            Dimension labelPreferredSize = new Dimension(100, 24);
            Dimension comboPreferredSize = new Dimension(150, 24);
            Dimension maxSize = new Dimension(250, 22);
		    
			Box panel = new Box(BoxLayout.X_AXIS);
//				panel.setBorder(new LineBorder(Color.RED));
			panel.setMaximumSize(new Dimension(999999, 22));
			
			panel.add(Box.createHorizontalStrut(lücke));
			
			JLabel label = new JLabel(op.getName());
			label.setPreferredSize(labelPreferredSize);
			label.setMaximumSize(maxSize);
			panel.add(label);
			
			JComboBox<Waffe> primW = new JComboBox<>(new Vector<Waffe>((op.getPrimärwaffen())));
			primW.setSelectedItem(op.getSelectedPrimärwaffe());
			primW.addActionListener((ActionEvent evt) -> {
			    op.setSelectedPrimärwaffe((Waffe)primW.getSelectedItem());
			    
			});
			
			primW.setPreferredSize(comboPreferredSize);
			primW.setMaximumSize(maxSize);
			panel.add(primW);
			
			panel.add(Box.createHorizontalStrut(lücke));
			

			JComboBox<Waffe> secW = new JComboBox<>(new Vector<Waffe>(op.getSekundärwaffen()));
			secW.setSelectedItem(op.getSelectedSekundärwaffe());
			secW.setPreferredSize(comboPreferredSize);
			secW.setMaximumSize(maxSize);
			secW.addActionListener((ActionEvent evt) -> {
				op.setSelectedSekundärwaffe((Waffe)secW.getSelectedItem());
			});
			panel.add(secW);
			
			panel.add(Box.createHorizontalStrut(lücke));
			
			if (op instanceof Rekrut) {
				Rekrut rekrut = (Rekrut) op;
				// Checkboxen, um *2* Gadgets auszuwählen
				for (Gadget gadget : rekrut.getGadgets()) {
					JCheckBox cb = new JCheckBox(gadget.getName());
					cb.setSelected(rekrut.getSelectedGadgets().contains(gadget));
					cb.addActionListener((ActionEvent evt)-> {
						rekrut.toggleGadget(cb.getText());
						showErrorIf(() -> rekrut.getSelectedGadgets().size() > Rekrut.MAX_GADGETS,
						        "Ein Rekrut darf höchstens " + Rekrut.MAX_GADGETS + " Gadgets haben!");
					});
					
                    showErrorIf(() -> rekrut.getSelectedGadgets().size() > Rekrut.MAX_GADGETS,
                            "Ein Rekrut darf höchstens " + Rekrut.MAX_GADGETS + " Gadgets haben!");

					panel.add(cb);
					
				}
			}
			else {
				// Combobox, um *1* Gadget auszuwählen
				JComboBox<Gadget> gad = new JComboBox<>(new Vector<Gadget>(op.getGadgets()));
				gad.setSelectedItem(op.getSelectedGadget());
				gad.setPreferredSize(comboPreferredSize);
				gad.setMaximumSize(maxSize);
				gad.addActionListener((ActionEvent evt) -> {
					op.setSelectedGadget((Gadget)gad.getSelectedItem());
				});
				panel.add(gad);
			}
			
			panel.add(Box.createHorizontalStrut(lücke));
			panel.add(Box.createHorizontalGlue());
			
			panel_waffen.add(panel);
			
			// Lücke zwischen den Operatoren
			panel_waffen.add(Box.createVerticalStrut(lücke/2));
		}
		
		panel_waffen.add(Box.createVerticalGlue());
		
		frame.getContentPane().validate();
	}

	private void showErrorIf(BooleanSupplier condition, String msg) {
	    if (condition.getAsBoolean())
	        errors.add(msg);
	    else
	        errors.remove(msg);
	    
	    if (errors.isEmpty())
	        panel_meldung.setVisible(false);
	    else {
	        meldunglabel.setText("<html>" + String.join("<br>", errors.toArray(new String[0])) + "</html>");
	        panel_meldung.setVisible(true);
	    }
	}
	
	
	private void clearErrors() {
	    errors.clear();
	    panel_meldung.setVisible(false);
	}
	
}
