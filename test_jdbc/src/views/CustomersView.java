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
			login.addActionListener( handler);
			
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
	  //signup.setText("Signinup!");
	  String	string = " ";
	  System.out.println("Button clicked");
	  
	  //TO Signup form
	    JFrame frame2 = new JFrame("Signup Form");
	    JPanel panel2 = new JPanel();

	    JTextField cid = new JTextField("Enter cid");
	    
	    JTextField classid = new JTextField("Enter classid");

	    JTextField name = new JTextField("Enter class name");
	    JTextField phone = new JTextField("Enter phone");
	    JTextField sAddress = new JTextField("Enter Address");
	    JTextField pCode = new JTextField("Enter postalcode");
	    JTextField email = new JTextField("Enter email");
	   
	    panel2.add(cid);
	    panel2.add(name);
	    panel2.add(phone);
	    panel2.add(sAddress);
	    panel2.add(pCode);
	    panel2.add(email);
	    panel2.add(classid);
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
				    int classid1 = Integer.parseInt(classid.getText());
				    int phone1 = Integer.parseInt(phone.getText());
				    int answer = cid1 + classid1 + phone1;
				    ;}
				catch (Exception e1) {
					
				    System.out.println("Wrong number.Try Again!");
					JOptionPane.showMessageDialog(null, "Try Again!Wrong Input");
				}

			        String name1 = name.getText();
				    String sAddress1 = sAddress.getText();
		    		String pCode1 = pCode.getText();

				    	if(pCode1.isEmpty()){
							JOptionPane.showMessageDialog(null, "Try Again!Wrong PostalCode");

				    	}
				    	else{	
				      String email1 = email.getText();
				   // int classid1 = Integer.parseInt(classid.getText());
				      JOptionPane.showMessageDialog(null, "Registered");        }
				// TODO Auto-generated method stub
				
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
					           JOptionPane.showMessageDialog(null, "No");}  
				    //String cname1 = instance.getname(cid);
				    //int answer1 = Integer.parseInt(answer);
				  //  if(answer.isEmpty())
				    //{JOptionPane.showMessageDialog(null, "No id exists");}
				    //else{		*/
					//int cids = Integer.parseInt(cid); 

				    //String answer = instance.login(cids);
					// JOptionPane.showMessageDialog(null, answer);

					/* if(answer.isEmpty()){
						 
					
							// TODO Auto-generated catch block
							 JOptionPane.showMessageDialog(null, "Answer length cant be 0");
						}*/
					 //else {	
			           //JOptionPane.showMessageDialog(null, answer);
						 
						  System.out.println("Button clicked");   
						  
						  JButton Updateinfo = new JButton("UPDATE CUSTOMER INFO");
						  JButton addclass = new JButton("ADD CLASS");
						  JButton deleteclass = new JButton("DELETE CLASS");
						  //JButton  = new JButton();
						  JFrame frame3 = new JFrame("Signup Form");
						  JPanel panel3 = new JPanel();
						  panel3.setLayout(new BoxLayout(panel3,BoxLayout.Y_AXIS));
					    
						panel3.add(BorderLayout.AFTER_LAST_LINE, Updateinfo);
						panel3.add(BorderLayout.AFTER_LAST_LINE, addclass);
						panel3.add(BorderLayout.AFTER_LAST_LINE, deleteclass);


						frame3.add(panel3);
						frame3.setSize(300, 300);
						frame3.setVisible(true);
						
//updatebutton
					Updateinfo.addActionListener(new ActionListener(){
					    
						@Override
							public void actionPerformed(ActionEvent e) {
								// TODO Auto-generated method stub JFrame frame2 = new JFrame("Signup Form");
							    if(e.getSource() == Updateinfo){
							    JFrame frame4 = new JFrame("Update Info");

								JPanel panel4 = new JPanel();

							    
							   
								
								JTextField cid = new JTextField("Enter cid");
								JTextField name = new JTextField("Enter Customer name");
							    JTextField phone = new JTextField("Enter phone");
							    JTextField sAddress = new JTextField("Enter Address");
							    JTextField pCode = new JTextField("Enter postalcode");
							    JTextField email = new JTextField("Enter email");
								JButton Done = new JButton("Done");

							   // panel4.add(cid);
							   panel4.add(cid);
								panel4.add(name);
							    panel4.add(phone);
							    panel4.add(sAddress);
							    panel4.add(pCode);
							    panel4.add(email);
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
										    int phone1 = Integer.parseInt(phone.getText());
										     String Address = sAddress.getText();
										     String pc = pCode.getText();
										     String emailA = email.getText();}
                                             
										    
										catch (Exception e1) {
											
										    System.out.println("Try Again!");
											JOptionPane.showMessageDialog(null, "Try Again!Wrong Input");
										}
											int cid1 =Integer.parseInt(cid.getText());	
										    String phone1 = (phone.getText());
									        String name1 = name.getText();
										    String saddress1 = sAddress.getText();
								    		String pCode1 = pCode.getText();
								    		String emailA = email.getText();
										    	if(pCode1.isEmpty()){
													JOptionPane.showMessageDialog(null, "Try Again!Wrong PostalCode");

										    	}
										    	//else{	
										      String email1 = email.getText();
										   // int classid1 = Integer.parseInt(classid.getText());
										      String answer =instance.Updatecustomer(name1, phone1, saddress1, pCode1, emailA,cid1);
										      JOptionPane.showMessageDialog(null, "Done"); }});       
										
									}}});
								
								
								
					//add button

				 addclass.addActionListener(new ActionListener(){
							@Override
							public void actionPerformed(ActionEvent e) {
								// TODO Auto-generated method stub
								JOptionPane.showMessageDialog(null, "Try Again!Wrong Input");
								}});
						
				//delete button
				     deleteclass.addActionListener(new ActionListener(){
						 @Override
 
				   public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						 JFrame frame4 = new JFrame("Delete Info");

							JPanel panel4 = new JPanel();

						    
						   
							
							JTextField cid = new JTextField("Enter cid");
							
						    JTextField clid = new JTextField("Enter classid");
						   
							JButton Delete = new JButton("Delete");
							
							try{
								int cid1 =Integer.parseInt(cid.getText());	
							    int clid1 = Integer.parseInt(clid.getText());}
							    
                                 
							    
							catch (Exception e1) {
								
							    System.out.println("Try Again!");
								JOptionPane.showMessageDialog(null, "Try Again!Wrong Input");
							}
							int cid1 =Integer.parseInt(cid.getText());	
						    int clid1 = Integer.parseInt(clid.getText());
						   // panel4.add(cid);
						   panel4.add(cid);
							panel4.add(clid);
						    panel4.setLayout(new BoxLayout(panel4,BoxLayout.Y_AXIS));
						    
							panel4.add(BorderLayout.AFTER_LAST_LINE, Delete);
							frame4.add(panel4);
							frame4.setSize(300, 300);
							frame4.setVisible(true); 
						    
							Delete.addActionListener(new ActionListener(){

								public void actionPerformed(ActionEvent e3) {
										// TODO Auto-generated method stub JFrame frame2 = new JFrame("Signup Form");
									    if(e.getSource() == Delete){	
									    	String answer = instance.deleteCustomer(cid1, clid1);
											JOptionPane.showMessageDialog(null, answer);

					 
									    }}});}
					 
				 
					});

					 		
				
				 
				 
				 }}};
}}
							    
				       
						
						
					
		    
				    
			



				   
				   
			   
				
			






	
	


