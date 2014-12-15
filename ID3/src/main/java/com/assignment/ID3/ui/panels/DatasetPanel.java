package com.assignment.ID3.ui.panels;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DatasetPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private final String TITLE = "Data set";
	
	private JLabel titleLabel = new JLabel(TITLE);
	private JButton browseButton = new JButton("Browse");
	
	public DatasetPanel(){
	}
	
	public JPanel generate(){
		add(titleLabel);
		add(browseButton);
		return this;
	}
}
