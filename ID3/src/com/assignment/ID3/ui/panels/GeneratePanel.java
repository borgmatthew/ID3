package com.assignment.ID3.ui.panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GeneratePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JButton generateButton = new JButton("Generate Tree");
	private JLabel message = new JLabel(" ");
	
	public GeneratePanel(ActionListener listener){
		generateButton.addActionListener(listener);
		setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		add(generateButton, setConstraints(constraints, 0, 0, 1, 1));
		add(message, setConstraints(constraints, 0, 1, 1, 1));
	}
	
	public void setMessage(String message){
		this.message.setText(message);
	}
	
	private GridBagConstraints setConstraints(GridBagConstraints constraints,
			int x, int y, int gridWidth, int gridHeight) {
		constraints.gridx = x;
		constraints.gridy = y;
		constraints.gridwidth = gridWidth;
		constraints.gridheight = gridHeight;
		constraints.anchor = GridBagConstraints.SOUTH;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.insets = new Insets(1, 2, 2, 1);
		constraints.weightx = 1.0;
		constraints.weighty = 1.0;
		return constraints;
	}
}
