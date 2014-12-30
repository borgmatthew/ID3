package com.assignment.ID3.ui.windows;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import com.assignment.ID3.parser.Parser;
import com.assignment.ID3.tree.Id3Tree;
import com.assignment.ID3.ui.panels.DatasetPanel;
import com.assignment.ID3.ui.panels.GeneratePanel;
import com.assignment.ID3.ui.panels.GraphPanel;
import com.assignment.ID3.ui.panels.ParametersPanel;

public class MainWindow extends JFrame{

	private static final long serialVersionUID = 1L;
	private final int WINDOW_WIDTH = 800;
	private final int WINDOW_HEIGHT = 450;
	private final DatasetPanel dataSet = new DatasetPanel();
	private final ParametersPanel parameters = new ParametersPanel();
	private final GeneratePanel generate = new GeneratePanel(new GenerateButtonListener());
	private final GraphPanel graphPanel = new GraphPanel();
	private Id3Tree tree;

	public MainWindow(){
		initGraphics();
		buildLayout();
	}
	
	private void initGraphics(){
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setMinimumSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
		setTitle("ID3 Implementation - Matthew Borg");
		setVisible(true);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int startX = (int)(dim.getWidth()/2 - WINDOW_WIDTH/2);
		int startY = (int)(dim.getHeight()/2 - WINDOW_HEIGHT/2);
		setLocation(startX, startY);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	private void buildLayout(){
		Container contentPane = getContentPane();
		contentPane.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		contentPane.add(dataSet, setConstraints(constraints, 0, 0, 1, 1));
		contentPane.add(parameters, setConstraints(constraints, 0, 1, 1, 1));
		contentPane.add(generate, setConstraints(constraints, 0, 2, 1, 1));
		contentPane.add(graphPanel, setConstraints(constraints, 1, 0, 1, 3));
		setContentPane(contentPane);
	}
	
	private GridBagConstraints setConstraints(GridBagConstraints constraints, int x, int y, int gridWidth, int gridHeight){
		constraints.gridx = x;
		constraints.gridy = y;
		constraints.gridwidth = gridWidth;
		constraints.gridheight = gridHeight;
		//constraints.anchor = (x == 0) ? GridBagConstraints.WEST : GridBagConstraints.EAST;
		constraints.fill = GridBagConstraints.BOTH ;
		constraints.insets = new Insets(5, 5, 5, 5);
		constraints.weightx = 1.0;
		constraints.weighty = 1.0;
		return constraints;
	}
	
	public class GenerateButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			Parser parser = new Parser(dataSet.getFilePath(), dataSet.getFieldTypes());
			if(parser.parse()){
				if(parameters.getOverFittingChoice().compareTo("none") == 0){
					tree = new Id3Tree(parser.getRecords(), dataSet.getTargetOffset() , dataSet.getFieldTypes(), 1);
					tree.generateTree();
				}else{
					tree = new Id3Tree(parser.getRecords(), dataSet.getTargetOffset() , dataSet.getFieldTypes(), parameters.getTrainRatio());
					tree.generateTree();
					tree.removeOverfitting();
				}
				
				graphPanel.setText(tree.printTree());
				
			}else{
				generate.setMessage(parser.getMessage());
			}
		}
		
	}
}
