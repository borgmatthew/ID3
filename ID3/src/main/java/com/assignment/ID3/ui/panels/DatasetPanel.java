package com.assignment.ID3.ui.panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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
	private JLabel targetOffsetLabel = new JLabel("Target column: ");
	private JLabel message = new JLabel(" ");
	private JButton browseButton = new JButton("Browse");
	private JButton loadButton = new JButton("Load");
	private JTextField filePath = new JTextField(10);
	private JTextField targetText = new JTextField(2);
	private JFileChooser fileChooser = new JFileChooser();

	private Parser parser = null;
	private int targetOffset = -1;

	public DatasetPanel() {
		setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		add(titleLabel, setConstraints(constraints, 0, 0, 2, 1));
		add(filePath, setConstraints(constraints, 0, 1, 1, 1));
		add(browseButton, setConstraints(constraints, 1, 1, 1, 1));
		add(targetOffsetLabel, setConstraints(constraints, 0, 2, 1, 1));
		add(targetText, setConstraints(constraints, 1, 2, 1, 1));
		add(loadButton, setConstraints(constraints, 0, 3, 2, 1));
		add(message, setConstraints(constraints, 0, 4, 2, 1));
		browseButton.addActionListener(new BrowseButtonListener());
		loadButton.addActionListener(new ParseButtonListener());
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

	public Parser getParser() {
		return parser;
	}

	public class BrowseButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			int result = fileChooser.showOpenDialog(browseButton);
			if (result == JFileChooser.APPROVE_OPTION) {
				filePath.setText(fileChooser.getSelectedFile()
						.getAbsolutePath());
			}
		}

	}

	public class ParseButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			try {
				targetOffset = Integer.parseInt(targetText.getText());
			} catch (NumberFormatException nfe) {
				message.setText("Invalid number");
				return;
			}
			parser = new Parser();
			String result = parser.parse(getFilePath(), targetOffset);
			message.setText(result);
			if(result.compareTo("File parsed successfully") != 0){
				parser = null;
			}
		}
	}

	public int getTargetOffset() {
		return targetOffset-1;
	}
}
