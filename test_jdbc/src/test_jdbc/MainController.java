package test_jdbc;

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
import views.EmployeeView;

public class MainController extends JFrame{

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
		} catch (IOException e) {
			System.exit(-1);
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

	        JMenuBar menubar = new JMenuBar();
	        JMenu exit = new JMenu("Exit");
	        JMenu classes = new JMenu("Classes");
	        JMenu customers = new JMenu("Customers");
	        JMenu employees = new JMenu("Employees");

	        menubar.add(exit);
	        menubar.add(classes);
	        menubar.add(customers);
	        menubar.add(employees);
	        setJMenuBar(menubar);

	        
	        //want it to be a view
	        add(new EmployeeView(), BorderLayout.CENTER);

	        setSize(1200, 700);
	        setTitle("CPSC304 - GYM DATABASE APP");
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setLocationRelativeTo(null);
	    }
	
}
