package views;

import java.awt.BorderLayout;
import java.awt.Color;
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
	
	private JList list;
	private DefaultListModel model;
	private JCheckBox[] checkArray; //for projections
	private JComboBox selectionField; //for selections
	private JTextField selectionValue;
	private JCheckBox isInstructor;
	private JCheckBox isManager;
	
	public EmployeeView(){
		initPanel();
		this.setVisible(true);
	}
	
	/*
	 * Set up what the employee view should look like
	 * Functionality:
	 * project and select employees
	 */
	public void initPanel() {
		setUpCheckBoxes();
		setUpFilters();
		setUpResultsSpace();
    }
	
	private void setUpCheckBoxes(){
		int numAttr = 7;
		this.checkArray = new JCheckBox[numAttr];
		String[] 
			attrNames = {"sin", "name", "job title", "phone number", "address", "postal Code", "email"};
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
			        //a checkbox has changed value. need to requery
//			    	List<String> queryOn = new ArrayList<String>();
//			    	for(int i = 0; i < numAttr; i++){
//			    		if(checkArray[i].isSelected()){
//			    			queryOn.add(checkArray[i].getText());
//			    			System.out.println(checkArray[i].getText());
//			    		}
//			    	}
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
				attrNames = {"sin", "name", "job title", "phone number", "address", "postal code", "email"};
		this.selectionField = new JComboBox<String>(attrNames);
		this.add(selectionField);
		this.selectionField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
//				JComboBox<String> cb = (JComboBox<String>) e.getSource();
//		    	System.out.println(cb.getSelectedItem());
				UpdateQuery();
			}
		});
		
		JLabel nameLabel = new JLabel("starts with");
		nameLabel.setForeground(Color.DARK_GRAY);
		nameLabel.setBackground(Color.GRAY);
		nameLabel.setFont(new Font("Serif", Font.PLAIN, 24));
		this.add(nameLabel);
		
		this.selectionValue = new JTextField(24);
		this.add(selectionValue);
		
		this.selectionValue.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent arg0) {
//				String text;
//				try{
//					text = arg0.getDocument().getText(0, arg0.getDocument().getLength());
//					System.out.println(text);
//				}catch(Exception ex){
//					System.out.println(ex.getMessage());
//				}
				UpdateQuery();
			}
			@Override
			public void insertUpdate(DocumentEvent arg0) {
//				String text;
//				try{
//					text = arg0.getDocument().getText(0, arg0.getDocument().getLength());
//					System.out.println(text);
//				}catch(Exception ex){
//					System.out.println(ex.getMessage());
//				}
				UpdateQuery();
			}
			@Override
			public void removeUpdate(DocumentEvent arg0) {
//				String text;
//				try{
//					text = arg0.getDocument().getText(0, arg0.getDocument().getLength());
//					System.out.println(text);
//				}catch(Exception ex){
//					System.out.println(ex.getMessage());
//				}	
				UpdateQuery();
			}
		});

		this.selectionValue.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
//				JTextField tf = (JTextField) e.getSource();
//		    	System.out.println(tf.getText());
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
	
	private void setUpResultsSpace(){
		//get the projected fields
		List<String> colNames = new ArrayList<String>();
		for(int i = 0; i < 7; i++){
			if(checkArray[i].isSelected()){
				colNames.add(checkArray[i].getText());
			}
		}
		List<Employee> employees = DataAccess.getInstance().EmployeeDemoSelectProject(colNames, 
				(String) selectionField.getSelectedItem(), selectionValue.getText(), 
				isInstructor.isSelected(), isManager.isSelected());
		String rowData[][] = new String[employees.size()][colNames.size()];
		int numcols = 0;
		for(int i = 0; i < employees.size(); i++){
			for(int j = 0; j < colNames.size(); j++){
				switch(colNames.get(i)){
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
				case "address":
					rowData[i][j] = employees.get(i).address;
					break;
				case "postal code":
					rowData[i][j] = employees.get(i).postalCode;
					break;
				case "email":
					rowData[i][j] = employees.get(i).email;
					break;
				default:
					rowData[i][j] = "";
					break;
				}
			}
		}
	    JTable table = new JTable(rowData, colNames.toArray());
	    this.add(table);
	}
	
	private void UpdateQuery(){
		List<String> projectionFields = new ArrayList<String>();
		for(int i = 0; i < 7; i++){
    		if(checkArray[i].isSelected()){
    			projectionFields.add(checkArray[i].getText());
    			System.out.println(checkArray[i].getText());
    		}
    	}
		
		DataAccess.getInstance().EmployeeDemoSelectProject(projectionFields, 
				(String) selectionField.getSelectedItem(), selectionValue.getText(), 
				isInstructor.isSelected(), isManager.isSelected());
	}
}
