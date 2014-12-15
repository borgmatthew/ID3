package com.assignment.ID3.ui.panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.assignment.ID3.parser.Parser;

public class DatasetPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private final String TITLE = "Data set";
	
	private JLabel titleLabel = new JLabel(TITLE);
	private JButton browseButton = new JButton("Browse");
	private JButton parseButton = new JButton("Parse");
	private JTextField filePath = new JTextField(10);
	private JFileChooser fileChooser = new JFileChooser();
	
	public DatasetPanel(){
		setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		add(titleLabel, setConstraints(constraints, 1, 1, 2, 1));
		add(filePath, setConstraints(constraints, 1, 2, 1, 1));
		add(browseButton, setConstraints(constraints, 2, 2, 1, 1));
		add(parseButton, setConstraints(constraints, 1, 3, 1, 1));
		browseButton.addActionListener(new BrowseButtonListener());
		parseButton.addActionListener(new ParseButtonListener());
	}
	
	public String getFilePath(){
		return filePath.getText();
	}
	
	private GridBagConstraints setConstraints(GridBagConstraints constraints, int x, int y, int gridWidth, int gridHeight){
		constraints.gridx = x;
		constraints.gridy = y;
		constraints.gridwidth = gridWidth;
		constraints.gridheight = gridHeight;
		return constraints;
	}
	
	public class BrowseButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			int result = fileChooser.showOpenDialog(browseButton);
			if(result == JFileChooser.APPROVE_OPTION){
				filePath.setText(fileChooser.getSelectedFile().getAbsolutePath());
			}
		}
		
	}
	
	public class ParseButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			Parser parser = new Parser();
			parser.parse(getFilePath());
			System.out.println(parser.getFieldTypes());
			System.out.println(parser.getRecords().size());
		}
		
	}
}
