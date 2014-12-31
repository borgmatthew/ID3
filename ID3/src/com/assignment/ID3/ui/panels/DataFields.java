package com.assignment.ID3.ui.panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import com.assignment.ID3.tree.FieldType;

public class DataFields extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private ArrayList<JRadioButton> radioButtons;
	private ArrayList<JCheckBox> checkBoxes;
	private ArrayList<JComboBox<String>> comboBoxes;
	private ArrayList<JLabel> idLabels;
	private ButtonGroup radioButtonGroup;
	private JLabel isTarget = new JLabel("Target field ");
	private JLabel type = new JLabel("Type ");
	private JLabel field = new JLabel("Fields ");
	private JLabel use = new JLabel("Use field");
	private GridBagConstraints constraints = new GridBagConstraints();
	private int targetColumn = 0;
	
	public DataFields(){
		initLists();		
		setLayout(new GridBagLayout());
		addLabels();
		generateDummyFields();
	}
	
	private void generateDummyFields(){
		setEnabled(false);
		for(int i = 0; i < 5; i++){
			JLabel label = new JLabel("#" + i);
			idLabels.add(label);
			add(label, setConstraints(i+1, 0, 1, 1));
			
			JRadioButton radio = new JRadioButton();
			radio.setEnabled(false);
			radioButtons.add(radio);
			radioButtonGroup.add(radio);
			add(radio, setConstraints(i+1, 1, 1, 1));
			
			JCheckBox check = new JCheckBox();
			check.setEnabled(false);
			checkBoxes.add(check);
			add(check, setConstraints(i+1, 2, 1, 1));
			
			JComboBox<String> combo = new JComboBox<String>();
			combo.addItem("Discrete");
			combo.setEnabled(false);
			combo.setSelectedIndex(0);
			comboBoxes.add(combo);
			add(combo, setConstraints(i+1, 3, 1, 1));
		}
	}
	
	private void addLabels(){
		setEnabled(true);
		add(field, setConstraints(0, 0, 1,1));
		add(isTarget, setConstraints(0, 1, 1, 1));
		add(use, setConstraints(0, 2, 1, 1));
		add(type, setConstraints(0, 3, 1, 1));
	}
	
	private void initLists(){
		radioButtons = new ArrayList<JRadioButton>();
		comboBoxes = new ArrayList<JComboBox<String>>();
		idLabels = new ArrayList<JLabel>();
		radioButtonGroup = new ButtonGroup();
		checkBoxes = new ArrayList<JCheckBox>();
	}
	
	public void setFieldTypes(List<FieldType> fieldTypes){
		initLists();
		removeAll();
		addLabels();
		
		int fields = fieldTypes.size();
		
		for(int i = 0; i < fields; i++){
			JLabel label = new JLabel("#" + i);
			idLabels.add(label);
			add(label, setConstraints(i+1, 0, 1, 1));
			
			JRadioButton radio = new JRadioButton();
			radioButtons.add(radio);
			radio.setSelected(true);
			radio.addActionListener(new RadioButtonListener());
			radioButtonGroup.add(radio);
			add(radio, setConstraints(i+1, 1, 1, 1));
			
			JCheckBox check = new JCheckBox();
			check.setEnabled(true);
			check.setSelected(true);
			checkBoxes.add(check);
			add(check, setConstraints(i+1, 2, 1, 1));
			
			JComboBox<String> combo = new JComboBox<String>();
			combo.addItem("Discrete");
			combo.setSelectedIndex(0);
			if(fieldTypes.get(i) == FieldType.CONTINUOUS){
				combo.addItem("Continuous");
				combo.setSelectedIndex(1);
			}
			comboBoxes.add(combo);
			add(combo, setConstraints(i+1, 3, 1, 1));
		}
		revalidate();
	}
	
	public int getTargetColumn(){
		return targetColumn;
	}
	
	public ArrayList<Boolean> getFieldsToUse(){
		ArrayList<Boolean> use = new ArrayList<Boolean>();
		for(JCheckBox c : checkBoxes){
			use.add(c.isSelected());
		}
		return use;
	}
	
	public ArrayList<FieldType> getTypes(){
		ArrayList<FieldType> result = new ArrayList<FieldType>();
		for(int i =0; i < comboBoxes.size(); i++){
			if(comboBoxes.get(i).getSelectedIndex() == 0){
				result.add(FieldType.DISCRETE);
			}else{
				result.add(FieldType.CONTINUOUS);
			}
		}
		return result;
	}
	
	private GridBagConstraints setConstraints(int x, int y, int gridWidth, int gridHeight) {
		constraints.gridx = x;
		constraints.gridy = y;
		constraints.gridwidth = gridWidth;
		constraints.gridheight = gridHeight;
		//constraints.anchor = (x == 0) ? GridBagConstraints.WEST	: GridBagConstraints.EAST;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets = new Insets(1, 2, 2, 1);
		constraints.weightx = (x == 0) ? 0.1 : 1.0;
		constraints.weighty = 1.0;
		return constraints;
	}
	
	private class RadioButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			targetColumn = radioButtons.indexOf(arg0.getSource());
		}
		
	}
}
