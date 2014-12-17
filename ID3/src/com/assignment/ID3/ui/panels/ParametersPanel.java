package com.assignment.ID3.ui.panels;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ParametersPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private final String TITLE = "Parameters";

	private JLabel titleLabel = new JLabel(TITLE);
	private JLabel param1Label = new JLabel("Param 1:");
	private JTextField param1Text = new JTextField();

	public ParametersPanel() {
		add(titleLabel);
		add(param1Label);
		add(param1Text);
	}
}
