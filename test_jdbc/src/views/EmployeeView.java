package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import pojos.Employee;
import repository.DataAccess;

public class EmployeeView extends JPanel{
	
	private JCheckBox[] checkArray; //for projections
	private JComboBox selectionField1; //for selections
	private JComboBox equalityField1;
	private JComboBox conjunctionField;
	private JComboBox selectionField2; //for selections
	private JComboBox equalityField2;
	private JTextField selectionValue1;
	private JTextField selectionValue2;
	private JCheckBox isInstructor;
	private JCheckBox isManager;
	private JScrollPane tableSP;
	
	public EmployeeView(){
		initPanel();
	}
	
	/*
	 * Set up what the employee view should look like
	 * Functionality:
	 * project and select employees
	 */
	public void initPanel() {
		setUpCheckBoxes();
		setUpFilters();
		List<String> colNames = new ArrayList<String>();
		for(int i = 0; i < 7; i++){
			if(checkArray[i].isSelected()){
				colNames.add(checkArray[i].getText());
			}
		}
		setUpResultsSpace(DataAccess.getInstance().EmployeeDemoSelectProject(colNames, 
				(String) selectionField1.getSelectedItem(), selectionValue1.getText(), 
				(String) equalityField1.getSelectedItem(), (String) conjunctionField.getSelectedItem(),
				(String) selectionField2.getSelectedItem(), selectionValue2.getText(),
				(String) equalityField2.getSelectedItem(),
				isInstructor.isSelected(), isManager.isSelected()));
    }
	
	private void setUpCheckBoxes(){
		int numAttr = 7;
		this.checkArray = new JCheckBox[numAttr];
		String[] 
			attrNames = {"sin", "name", "job title", "phone number", "street address", "postal code", "email address"};
		JLabel projectText = new JLabel("Select possible projections for employees:");
		projectText.setForeground(Color.DARK_GRAY);
		projectText.setBackground(Color.GRAY);
		projectText.setFont(new Font("Serif", Font.PLAIN, 24));
		this.add(projectText);
		for(int i = 0; i < numAttr; i++){
			this.checkArray[i] = new JCheckBox(attrNames[i]);
			this.add(this.checkArray[i]);
			this.checkArray[i].setFont(new Font("Serif", Font.PLAIN, 24));
			this.checkArray[i].setSelected(true);
			this.checkArray[i].addItemListener(new ItemListener() {
			    @Override
			    public void itemStateChanged(ItemEvent e) {
			    	UpdateQuery();
			    }
			});
		}
	}
	
	private void setUpFilters(){
		JLabel projectText = new JLabel("Filter employees on:");
		projectText.setForeground(Color.DARK_GRAY);
		projectText.setBackground(Color.GRAY);
		projectText.setFont(new Font("Serif", Font.PLAIN, 24));
		this.add(projectText);
		
		//name starts with
		
		String[] 
				attrNames = {"sin", "name", "job title", "phone number", "street address", "postal code", "email address"};
		this.selectionField1 = new JComboBox<String>(attrNames);
		this.add(selectionField1);
		this.selectionField1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				UpdateQuery();
			}
		});
		
		String[] 
				eqOpts = {"", "=", "<>", "starts with", "is null"};
		this.equalityField1 = new JComboBox<String>(eqOpts);
		this.add(equalityField1);
		this.equalityField1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				UpdateQuery();
			}
		});
		
		this.selectionValue1 = new JTextField(24);
		this.add(selectionValue1);
		
		this.selectionValue1.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent arg0) {		
				UpdateQuery();
			}
			@Override
			public void insertUpdate(DocumentEvent arg0) {
				UpdateQuery();
			}
			@Override
			public void removeUpdate(DocumentEvent arg0) {	
				UpdateQuery();
			}
		});
		
		String[] 
				comboOpts = {"", "and", "or", "and not", "or not"};
		this.conjunctionField = new JComboBox<String>(comboOpts);
		this.add(conjunctionField);
		this.conjunctionField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				UpdateQuery();
			}
		});
		
		this.selectionField2 = new JComboBox<String>(attrNames);
		this.add(selectionField2);
		this.selectionField2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				UpdateQuery();
			}
		});
	
		this.equalityField2 = new JComboBox<String>(eqOpts);
		this.add(equalityField2);
		this.equalityField2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				UpdateQuery();
			}
		});
		
		this.selectionValue2 = new JTextField(24);
		this.add(selectionValue2);
		
		this.selectionValue2.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent arg0) {		
				UpdateQuery();
			}
			@Override
			public void insertUpdate(DocumentEvent arg0) {
				UpdateQuery();
			}
			@Override
			public void removeUpdate(DocumentEvent arg0) {	
				UpdateQuery();
			}
		});
		
		
		//is an instructor
		this.isInstructor = new JCheckBox("is an instructor");
		this.add(this.isInstructor);
		this.isInstructor.setFont(new Font("Serif", Font.PLAIN, 24));
		this.isInstructor.setSelected(false);
		this.isInstructor.addItemListener(new ItemListener() {
		    @Override
		    public void itemStateChanged(ItemEvent e) {
		    	UpdateQuery();
		    }
		});
		
		//is a manager
		this.isManager = new JCheckBox("is a manager");
		this.add(this.isManager);
		this.isManager.setFont(new Font("Serif", Font.PLAIN, 24));
		this.isManager.setSelected(false);
		this.isManager.addItemListener(new ItemListener() {
		    @Override
		    public void itemStateChanged(ItemEvent e) {
		    	UpdateQuery();
		    }
		});
		
		
	}
	
	private void setUpResultsSpace(List<Employee> employees){
		//get the projected fields
		List<String> colNames = new ArrayList<String>();
		for(int i = 0; i < 7; i++){
			if(checkArray[i].isSelected()){
				colNames.add(checkArray[i].getText());
			}
		}
		String rowData[][] = new String[employees.size()][colNames.size()];
		for(int i = 0; i < employees.size(); i++){
			for(int j = 0; j < colNames.size(); j++){
				switch(colNames.get(j)){
				case "sin":
					rowData[i][j] = Integer.toString(employees.get(i).sin);
					break;
				case "name":
					rowData[i][j] = employees.get(i).name;
					break;
				case "job title":
					rowData[i][j] = employees.get(i).jobtitle;
					break;
				case "phone number":
					rowData[i][j] = employees.get(i).phoneNumber;
					break;
				case "street address":
					rowData[i][j] = employees.get(i).address;
					break;
				case "postal code":
					rowData[i][j] = employees.get(i).postalCode;
					break;
				case "email address":
					rowData[i][j] = employees.get(i).email;
					break;
				default:
					rowData[i][j] = "";
					System.out.println("didn't have switch for: " + colNames.get(j));
					break;
				}
			}
		}
		if(this.tableSP != null){
			this.remove(this.tableSP);
		}
		JTable table = new JTable(rowData, colNames.toArray());
        JScrollPane tableSP = new JScrollPane(table);
        tableSP.setPreferredSize(new Dimension(1150, 500));

        this.add(tableSP);
        this.tableSP = tableSP; 
        this.repaint();
        this.revalidate();
	}
	
	private void UpdateQuery(){
		List<String> projectionFields = new ArrayList<String>();
		for(int i = 0; i < 7; i++){
    		if(checkArray[i].isSelected()){
    			projectionFields.add(checkArray[i].getText());
    		}
    	}
		
		setUpResultsSpace(DataAccess.getInstance().EmployeeDemoSelectProject(projectionFields, 
				(String) selectionField1.getSelectedItem(), selectionValue1.getText(), 
				(String) equalityField1.getSelectedItem(), (String) conjunctionField.getSelectedItem(),
				(String) selectionField2.getSelectedItem(), selectionValue2.getText(),
				(String) equalityField2.getSelectedItem(),
				isInstructor.isSelected(), isManager.isSelected()));
	}
}
