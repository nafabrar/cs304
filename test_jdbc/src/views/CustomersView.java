package views;
import pojos.Customers;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import pojos.Employee;
import repository.DataAccess;
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
public class CustomersView extends JPanel {
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

	private JButton signup;
	private JButton login;
	private DataAccess instance = null;
	private JButton Register = new JButton("Register");

	public CustomersView() { 
	
			instance = DataAccess.getInstance();
			go();
			
	}
	
	public void go(){ 
			
			//JFrame frame = new JFrame("CustomersView");
			signup = new JButton("Signup");
		    login = new JButton("Login");
			
			//signup.addActionListener(this);
			thehandler handler = new thehandler();
			
			signup.addActionListener(handler);
			login.addActionListener(handler);
			
			JPanel panelA = new JPanel();
			panelA.setLayout(new BoxLayout(panelA,BoxLayout.Y_AXIS));
			panelA.add(BorderLayout.NORTH, signup);
			panelA.add(BorderLayout.AFTER_LAST_LINE, login);
			this.add(panelA);
			this.setSize(300, 300);
			this.setVisible(true);
			
			
			}

			
			
public void info(int cid){
	JFrame frame = new JFrame();
	signup = new JButton("Signup");
    login = new JButton("Login");
	//signup.addActionListener(this);
	thehandler handler = new thehandler();
	
	signup.addActionListener(handler);
	login.addActionListener(handler);
	
	JPanel panelA = new JPanel();
	panelA.setLayout(new BoxLayout(panelA,BoxLayout.Y_AXIS));
	panelA.add(BorderLayout.NORTH, signup);
	panelA.add(BorderLayout.AFTER_LAST_LINE, login);
	frame.add(panelA);
	frame.setSize(300, 300);
	frame.setVisible(true);
	
}			
private void signup(){
	  System.out.println("Button clicked");
	  
	  //TO Signup form
	    JFrame frame2 = new JFrame("Signup Form");
	    JPanel panel2 = new JPanel();


	    JLabel myLabel = new JLabel("Enter cid");
	    JTextField cid = new JTextField();

	    panel2.add(myLabel,BorderLayout.WEST);
	    panel2.add(cid,BorderLayout.CENTER);

	   
	    JLabel myLabel1 = new JLabel("Enter name");
	    JTextField name = new JTextField();
	    panel2.add(myLabel1,BorderLayout.WEST);
	    panel2.add(name,BorderLayout.CENTER);

	    JTextField phone = new JTextField();
	    JLabel myLabel2 = new JLabel("Enter phone");
	    panel2.add(myLabel2,BorderLayout.WEST);
	    panel2.add(phone,BorderLayout.CENTER);
	   
	    JTextField sAddress = new JTextField();
	    JLabel myLabel3 = new JLabel("Enter Address");
	    panel2.add(myLabel3,BorderLayout.WEST);
	    panel2.add(sAddress,BorderLayout.CENTER);
	    
	    
	   
	   JLabel myLabel4 = new JLabel("Enter PostalCode");
	   JTextField pCode = new JTextField();
	    panel2.add(myLabel4,BorderLayout.WEST);
	    panel2.add(pCode,BorderLayout.CENTER);
	    
		JLabel myLabel5 = new JLabel("Enter Email");
		JTextField email = new JTextField();
		panel2.add(myLabel5,BorderLayout.WEST);
		panel2.add(email,BorderLayout.WEST);

	    
	  
	    panel2.setLayout(new BoxLayout(panel2,BoxLayout.Y_AXIS));
	    
		panel2.add(BorderLayout.AFTER_LAST_LINE, Register);
		frame2.add(panel2);
		frame2.setSize(300, 300);
		frame2.setVisible(true);
	  
	    Register.addActionListener(new ActionListener(){


	    

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == Register)
					try{
						int cid1 = Integer.parseInt(cid.getText());
				    }
				catch (Exception e1) {
				    System.out.println("make sure cid is a number");
					JOptionPane.showMessageDialog(null, "make sure cid is a number");
				}
				if(instance.signup(Integer.parseInt(cid.getText()), name.getText(), phone.getText(), 
						sAddress.getText(), pCode.getText(), email.getText())){
					JOptionPane.showMessageDialog(null, "Registered"); 
					frame2.dispose();
				}else{
					JOptionPane.showMessageDialog(null, "Failed. "
							+ "Try a different cid and make sure you've entered a valid phone number and postal code"); 

				}
				
			}
	    });
		

}
private class thehandler implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
			   if(e.getSource() == signup){
				   signup();
		              }

			   
			   else{
				   if(e.getSource() == login){
					try{
					   String cid = JOptionPane.showInputDialog("enter cid");
						int cids = Integer.parseInt(cid); 

					    String answer = instance.login(cids);
						JOptionPane.showMessageDialog(null, answer);

					   
					   
						
					        Integer.parseInt( cid );
					    }
					    catch( Exception e4 ) {
					           JOptionPane.showMessageDialog(null, "Please provide a correct customerID");
					    return;       
					    }  
		
						 
						  System.out.println("Button clicked");   
						  
						  JButton Updateinfo = new JButton("UPDATE CUSTOMER INFO");
						  JButton deleteclass = new JButton("DELETE CLASS");
						  //JButton  = new JButton();
						  JFrame frame3 = new JFrame("Welcome!!");
						  JPanel panel3 = new JPanel();
						  panel3.setLayout(new BoxLayout(panel3,BoxLayout.Y_AXIS));
					    
						panel3.add(BorderLayout.AFTER_LAST_LINE, Updateinfo);
						panel3.add(BorderLayout.AFTER_LAST_LINE, deleteclass);


						frame3.add(panel3);
						frame3.setSize(300, 300);
						frame3.setVisible(true);
						
//updatebutton
						Updateinfo.addActionListener(new ActionListener(){

							@Override
							public void actionPerformed(ActionEvent e) {
								if(e.getSource() == Updateinfo){
									JFrame frame4 = new JFrame("Update Info");
									JPanel panel4 = new JPanel();
									
									
									
									 JLabel myLabel1 = new JLabel("Enter Your CustomerID");
										JTextField cid = new JTextField();
									    panel4.add(myLabel1,BorderLayout.WEST);
									    panel4.add(cid,BorderLayout.CENTER);
									

										    JLabel myLabel2 = new JLabel("Enter Your name");
											JTextField name = new JTextField();
										    panel4.add(myLabel2,BorderLayout.WEST);
										    panel4.add(name,BorderLayout.CENTER);
									
										   
								    JLabel myLabel3 = new JLabel("Enter Your phone");
								    JTextField phone = new JTextField();
								    panel4.add(myLabel3,BorderLayout.WEST);
								    panel4.add(phone,BorderLayout.CENTER);
							
									

									 JLabel myLabel4 = new JLabel("Enter Your StreetAddress");
										JTextField sAddress = new JTextField();
									    panel4.add(myLabel4,BorderLayout.WEST);
									    panel4.add(sAddress,BorderLayout.CENTER);
									
									    JLabel myLabel5 = new JLabel("Enter Your Postalcode");
										JTextField pCode = new JTextField();
									    panel4.add(myLabel5,BorderLayout.WEST);
									    panel4.add(pCode,BorderLayout.CENTER);
									
									
									    JLabel myLabel6 = new JLabel("Enter Your Email");
									    JTextField email = new JTextField();
									    panel4.add(myLabel6,BorderLayout.WEST);
									    panel4.add(email,BorderLayout.CENTER);
									
									
									
									
									
									JButton Done = new JButton("Done");

									
									panel4.setLayout(new BoxLayout(panel4,BoxLayout.Y_AXIS));

									panel4.add(BorderLayout.AFTER_LAST_LINE, Done);
									frame4.add(panel4);
									frame4.setSize(300, 300);
									frame4.setVisible(true);

									//DOING DONE BUTTON NOW
									Done.addActionListener(new ActionListener(){
										@Override
										public void actionPerformed(ActionEvent e) {
											//if (e.getSource() == Done){
											try{
												int cid1 =Integer.parseInt(cid.getText());	
												
												}
											catch (NumberFormatException ne) {
												JOptionPane.showMessageDialog(null, "Could not parse: " + ne.getMessage());
											}
											int cid1 =Integer.parseInt(cid.getText());	
											String phone1 = phone.getText();
											String name1 = name.getText();
											String saddress1 = sAddress.getText();
											String pCode1 = pCode.getText();
											String emailA = email.getText();
											if(pCode1.isEmpty()){
												JOptionPane.showMessageDialog(null, "Please Enter a Postal Code");

											}
											
											String email1 = email.getText();
											String answer = instance.Updatecustomer(name1, phone1, saddress1, pCode1, emailA,cid1);
											JOptionPane.showMessageDialog(null, answer); 
											}
										}
									);       

								}}});

								
			
						
				//delete button
					//delete class button
				 deleteclass.addActionListener(new ActionListener(){
						 @Override
						 public void actionPerformed(ActionEvent e9) {
							 
						    JFrame frame12 = new JFrame("Delete Info");

							JPanel panel12 = new JPanel();

						    
						   
							 JLabel myLabel1 = new JLabel("Enter Your CustomerID");
								JTextField cid2 = new JTextField();
								cid2.setColumns(10);

								panel12.add(myLabel1,BorderLayout.WEST);
							    panel12.add(cid2,BorderLayout.CENTER);
							    
							    JLabel myLabel2 = new JLabel("Enter the classid you want to quit");
							    JTextField clid2 = new JTextField();
								clid2.setColumns(10);

							    panel12.add(myLabel2,BorderLayout.WEST);
							    panel12.add(clid2,BorderLayout.CENTER);
							
						   
							JButton Delete = new JButton("Delete");
							panel12.add(BorderLayout.AFTER_LAST_LINE, Delete);
							frame12.add(panel12);
							frame12.setSize(500, 500);
							frame12.setVisible(true);
							 
							
		
				   
			 Delete.addActionListener(new ActionListener(){

				public void actionPerformed(ActionEvent e66) {
					int cid1 =Integer.parseInt(cid2.getText());	
				    int clid1 = Integer.parseInt(clid2.getText());
				    	String answer = instance.deleteCustomer(cid1, clid1);
						JOptionPane.showMessageDialog(null, answer);
 }});}});
					 
									
					 
				 
					};

					 		
				
				 
				 
				 }}};
}
							    
				       
						
						
					
		    
				    
			



				   
				   
			   
				
			






	
	


