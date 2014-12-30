package com.assignment.ID3.ui.panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class GraphPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextArea graphvizCode = new JTextArea(10, 10);
	private JScrollPane scroll = new JScrollPane (graphvizCode, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

	public GraphPanel() {
		setLayout(new GridBagLayout());
		setBorder(BorderFactory.createTitledBorder("Dot Code"));
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		add(scroll, gbc);
	}
	
	public void setText(String text){
		graphvizCode.setText(text);
	}
}
