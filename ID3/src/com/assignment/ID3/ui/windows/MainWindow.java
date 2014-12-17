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
import com.assignment.ID3.tree.ColumnValues;
import com.assignment.ID3.tree.Field;
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
	private final GraphPanel graph = new GraphPanel();
	private final GeneratePanel generate = new GeneratePanel(new GenerateButtonListener());
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
		contentPane.add(graph, setConstraints(constraints, 1, 0, 1, 3));
		contentPane.add(generate, setConstraints(constraints, 0, 3, 1, 1));
		setContentPane(contentPane);
	}
	
	private GridBagConstraints setConstraints(GridBagConstraints constraints, int x, int y, int gridWidth, int gridHeight){
		constraints.gridx = x;
		constraints.gridy = y;
		constraints.gridwidth = gridWidth;
		constraints.gridheight = gridHeight;
		constraints.anchor = (x == 0) ? GridBagConstraints.WEST : GridBagConstraints.EAST;
		constraints.fill = (x == 0) ? GridBagConstraints.VERTICAL : GridBagConstraints.BOTH ;
		constraints.insets = new Insets(5, 5, 5, 5);
		constraints.weightx = (x == 0) ? 0.1 : 1.0;
		constraints.weighty = 1.0;
		return constraints;
	}
	
	public class GenerateButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			Parser parser = dataSet.getParser();
			if(parser != null){
				tree = new Id3Tree(parser.getRecords(), dataSet.getTargetOffset() , parser.getFieldTypes());
				ColumnValues cv = tree.calculateDataAttributes(2);
			}else{
				generate.setMessage("Load a file first!");
			}
		}
		
	}
}
