package kllngii.r6h.spieler;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import kllngii.r6h.model.Spieler;

public class SpielerListeAddDialog extends JDialog {

	private static final long serialVersionUID = -696154309824344401L;
	private final JPanel contentPanel = new JPanel();
	
	private final SpielerlisteController controller;
	


	/**
	 * Create the dialog.
	 */
	public SpielerListeAddDialog(SpielerlisteController _controller) {
		this.controller = _controller;
		
		
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblName = new JLabel("Name");
		lblName.setBounds(6, 6, 81, 16);
		contentPanel.add(lblName);
		
		JLabel lblHeadshots = new JLabel("Headshots");
		lblHeadshots.setBounds(6, 34, 81, 16);
		contentPanel.add(lblHeadshots);
		
		JLabel lblAces = new JLabel("Aces");
		lblAces.setBounds(6, 62, 81, 16);
		contentPanel.add(lblAces);
		
		final JTextField txtName = new JTextField();
		txtName.setText("Name");
		txtName.setBounds(99, 1, 130, 26);
		contentPanel.add(txtName);
		txtName.setColumns(10);
		
		JTextField txtHs = new JTextField();
		txtHs.setBounds(99, 29, 33, 26);
		contentPanel.add(txtHs);
		
		JTextField txtAce = new JTextField();
		txtAce.setBounds(99, 57, 33, 26);
		contentPanel.add(txtAce);
		
		JCheckBox advancedSettings = new JCheckBox("Erweiterte Einstellungen");
		advancedSettings.addActionListener(e -> {
			if (advancedSettings.isSelected()) {
				lblHeadshots.setVisible(true);
				lblAces.setVisible(true);
				txtAce.setEnabled(true);
				txtHs.setEnabled(true);
			} else {
				lblHeadshots.setVisible(false);
				lblAces.setVisible(false);
				txtAce.setEnabled(false);
				txtHs.setEnabled(false);
			}
		});
		advancedSettings.setBounds(6, 210, 188, 23);
		contentPanel.add(advancedSettings);
		
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		
		JButton okButton = new JButton("OK");
		okButton.addActionListener(event -> {
				if(txtName.toString() != null) {
					controller.erzeugeSpieler(new Spieler(txtName.toString()));
				}
			}
		);
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);

		JButton cancelButton = new JButton("Abbrechen");
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);
	}
}
