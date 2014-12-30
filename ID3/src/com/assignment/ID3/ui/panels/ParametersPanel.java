package com.assignment.ID3.ui.panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ParametersPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private final String TITLE = "Settings";

	private JLabel overFitting = new JLabel("Over fitting counter-measure:");
	private JComboBox<String> overFittingCombo = new JComboBox<String>();
	private JLabel trainRatio = new JLabel("Ratio of training : verification");
	private JTextField trainRatioBox = new JTextField();
	private JLabel errorMessage = new JLabel("");

	public ParametersPanel() {
		setLayout(new GridBagLayout());
		setBorder(BorderFactory.createTitledBorder(TITLE));
		overFittingCombo.addItem("none");
		overFittingCombo.addItem("Reduced Error Pruning");
		GridBagConstraints constraints = new GridBagConstraints();
		add(overFitting, setConstraints(constraints, 0,0,1,1));
		add(overFittingCombo, setConstraints(constraints, 1, 0, 1, 1));
		add(trainRatio, setConstraints(constraints, 0, 1, 1, 1));
		add(trainRatioBox, setConstraints(constraints, 1, 1, 1, 1));
		add(errorMessage, setConstraints(constraints, 0, 2, 2, 1));
	}
	
	private GridBagConstraints setConstraints(GridBagConstraints constraints,
			int x, int y, int gridWidth, int gridHeight) {
		constraints.gridx = x;
		constraints.gridy = y;
		constraints.gridwidth = gridWidth;
		constraints.gridheight = gridHeight;
		//constraints.anchor = (x == 0) ? GridBagConstraints.WEST	: GridBagConstraints.EAST;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.insets = new Insets(1, 2, 2, 1);
		constraints.weightx = 1.0;
		constraints.weighty = 1.0;
		return constraints;
	}
	
	public double getTrainRatio(){
		errorMessage.setText("");
		double result = -1;
		try{
			result = Double.parseDouble(trainRatioBox.getText());
			if(result < 0){
				errorMessage.setText("Negative ratio!");
				result = -1;
			}
		}catch(NumberFormatException nfe){
			errorMessage.setText("Invalid train ratio");
		}
		return result;
	}
	
	public String getOverFittingChoice(){
		return (String)overFittingCombo.getSelectedItem();
	}
}
