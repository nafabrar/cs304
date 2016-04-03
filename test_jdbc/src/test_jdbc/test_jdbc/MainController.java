package test_jdbc.test_jdbc;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import repository.DataAccess;
import views.ClassesView;
import views.CustomersView;
import views.EmployeeView;

public class MainController extends JFrame {

	/*
	 * Main method to kick off any initialization that needs to be done
	 * before jumping into the program
	 */
	public static void main(String args[])
	{
		//does the necessary initialization
	     try {
	    	DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			DataAccess dbSingleton = DataAccess.getInstance();
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		 EventQueue.invokeLater(new Runnable() {
		        
	            @Override
	            public void run() {
	                MainController ex = new MainController();
	                ex.setVisible(true);
	            }
	        });
		System.out.println("HELLO \n");
	   
	 }
	
	 public MainController() {

	        initUI();
	    }

	 public final void initUI() {
		 JTabbedPane tabbedPane = new JTabbedPane();
		 tabbedPane.add("Employees", new EmployeeView());
		 tabbedPane.add("Classes", new ClassesView());
		 //TODO create view for customers
		 tabbedPane.add("Customers", new CustomersView());
		 add(tabbedPane, BorderLayout.CENTER);

		 setSize(1200, 700);
		 setTitle("CPSC304 - GYM DATABASE APP");
		 setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 setLocationRelativeTo(null);
	 }
	
}
