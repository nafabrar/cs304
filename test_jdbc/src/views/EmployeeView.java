package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.*;

public class EmployeeView extends JPanel{
	
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
    }
	
	private void setUpCheckBoxes(){
		int numAttr = 7;
		JCheckBox[] checkArray = new JCheckBox[numAttr];
		String[] 
			attrNames = {"sin", "name", "job title", "phone number", "address", "postal Code", "email"};
		JLabel projectText = new JLabel("Select possible projections for employees:");
		projectText.setForeground(Color.DARK_GRAY);
		projectText.setBackground(Color.GRAY);
		projectText.setSize(1000, 200);
		projectText.setFont(new Font("Serif", Font.PLAIN, 24));
		this.add(projectText);
		for(int i = 0; i < numAttr; i++){
			checkArray[i] = new JCheckBox(attrNames[i]);
			this.add(checkArray[i]);
			checkArray[i].setFont(new Font("Serif", Font.PLAIN, 24));
			checkArray[i].setSelected(true);
		}
		
	}
}
