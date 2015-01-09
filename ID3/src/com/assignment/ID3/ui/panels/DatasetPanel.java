package com.assignment.ID3.ui.panels;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.assignment.ID3.parser.HeaderParser;
import com.assignment.ID3.tree.FieldType;

public class DatasetPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private final String TITLE = "Data set";

	private JLabel fileLabel = new JLabel("File location: ");
	private JLabel message = new JLabel(" ");
	private JButton browseButton = new JButton("Browse");
	private JTextField filePath = new JTextField(10);
	private JFileChooser fileChooser = new JFileChooser();
	private DataFields dataFields = new DataFields();
	private JScrollPane dataFieldsScroll = new JScrollPane(dataFields);

	public DatasetPanel() {
		setLayout(new GridBagLayout());
		setBorder(BorderFactory.createTitledBorder(TITLE));
		GridBagConstraints constraints = new GridBagConstraints();
		add(fileLabel, setConstraints(constraints, 0, 0, 1, 1, GridBagConstraints.HORIZONTAL));
		add(filePath, setConstraints(constraints, 0, 1, 1, 1, GridBagConstraints.HORIZONTAL));
		add(browseButton, setConstraints(constraints, 1, 1, 1, 1, GridBagConstraints.NONE));
		add(message, setConstraints(constraints, 0, 2, 3, 1, GridBagConstraints.HORIZONTAL));
		add(dataFieldsScroll, setConstraints(constraints, 0, 3, 3, 1, GridBagConstraints.HORIZONTAL));
		dataFieldsScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		dataFieldsScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		browseButton.addActionListener(new BrowseButtonListener());
		dataFieldsScroll.setMinimumSize(new Dimension(dataFieldsScroll.getWidth(), 100));
	}

	public String getFilePath() {
		return filePath.getText();
	}

	private GridBagConstraints setConstraints(GridBagConstraints constraints,
			int x, int y, int gridWidth, int gridHeight, int fill) {
		constraints.gridx = x;
		constraints.gridy = y;
		constraints.gridwidth = gridWidth;
		constraints.gridheight = gridHeight;
		//constraints.anchor = (x == 0) ? GridBagConstraints.WEST	: GridBagConstraints.EAST;
		constraints.fill = fill;
		constraints.insets = new Insets(1, 2, 2, 1);
		constraints.weightx = 1.0;
		constraints.weighty = 1.0;
		return constraints;
	}

	public class BrowseButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {
			int result = fileChooser.showOpenDialog(browseButton);
			if (result == JFileChooser.APPROVE_OPTION) {
				String path = fileChooser.getSelectedFile().getAbsolutePath();
				filePath.setText(path);
				HeaderParser headParse = new HeaderParser(path);
				ArrayList<FieldType> types = headParse.parseHeaders();
				if(types == null){
					message.setText("Error while reading file!");
				}else{
					dataFields.setFieldTypes(types);
				}
			}
		}
	}

	public int getTargetOffset() {
		return dataFields.getTargetColumn();
	}
	
	public ArrayList<FieldType> getFieldTypes(){
		return dataFields.getTypes();
	}
	
	public ArrayList<Boolean> getFieldsToUse(){
		return dataFields.getFieldsToUse();
	}
}
