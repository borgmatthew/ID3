package com.assignment.ID3.ui.windows;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;

import com.assignment.ID3.ui.panels.DatasetPanel;
import com.assignment.ID3.ui.panels.GraphPanel;
import com.assignment.ID3.ui.panels.ParametersPanel;

public class MainWindow extends JFrame{

	private static final long serialVersionUID = 1L;
	private final int WINDOW_WIDTH = 500;
	private final int WINDOW_HEIGHT = 500;
	private final DatasetPanel dataSet = new DatasetPanel();
	private final ParametersPanel parameters = new ParametersPanel();
	private final GraphPanel graph = new GraphPanel();

	public MainWindow(){
		initGraphics();
		buildLayout();
	}
	
	private void initGraphics(){
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
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
		contentPane.add(graph, setConstraints(constraints, 1, 0, 1, 2));
		setContentPane(contentPane);
	}
	
	private GridBagConstraints setConstraints(GridBagConstraints constraints, int x, int y, int gridWidth, int gridHeight){
		constraints.gridx = x;
		constraints.gridy = y;
		constraints.gridwidth = gridWidth;
		constraints.gridheight = gridHeight;
		return constraints;
	}
}
