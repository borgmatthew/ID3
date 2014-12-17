package com.assignment.ID3.ui.panels;

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
		add(fileLabel, setConstraints(constraints, 0, 0, 1, 1));
		add(filePath, setConstraints(constraints, 1, 0, 1, 1));
		add(browseButton, setConstraints(constraints, 2, 0, 1, 1));
		add(message, setConstraints(constraints, 0, 1, 3, 1));
		add(dataFieldsScroll, setConstraints(constraints, 0, 2, 3, 1));
		dataFieldsScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		dataFieldsScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		browseButton.addActionListener(new BrowseButtonListener());
	}

	public String getFilePath() {
		return filePath.getText();
	}

	private GridBagConstraints setConstraints(GridBagConstraints constraints,
			int x, int y, int gridWidth, int gridHeight) {
		constraints.gridx = x;
		constraints.gridy = y;
		constraints.gridwidth = gridWidth;
		constraints.gridheight = gridHeight;
		constraints.anchor = (x == 0) ? GridBagConstraints.WEST	: GridBagConstraints.EAST;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.insets = new Insets(1, 2, 2, 1);
		constraints.weightx = (x == 0) ? 0.1 : 1.0;
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
					revalidate();
					repaint();
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
}
