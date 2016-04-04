package views;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.*;

import pojos.Membership;
import repository.DataAccess;



public class MembershipView extends JPanel implements ActionListener {
	
	private DataAccess instance = null;
	//private JButton AddMembership;
	private JButton AddMembership = new JButton("Add Membership");
	protected JTextField customerID;
	protected JTextField type;
	protected JTextField validFrom;
//	private JTextField validTo;
	protected JTextField amountPaid;
	//private JTextField fees;
	
	private Connection con;
	

	
	public MembershipView() { 
		
		instance = DataAccess.getInstance();
		con = instance.getConnection();
		go();
		
}
	


	protected void go() {
		JFrame frame = new JFrame();
		
	//	thehandler handler = new thehandler();
		JPanel panelA = new JPanel();
		
		this.type = new JTextField("type:");
		this.validFrom  = new JTextField("validFrom:");
		this.amountPaid = new JTextField("amountPaid:");
		
	
		this.customerID = new JTextField("customerID:");
		
		//JTextField validTo = new JTextField("validTo:");
		// JTextField fees = new JTextField("fees:");
		
		panelA.add(type);
		panelA.add(validFrom);
		panelA.add(amountPaid);
		panelA.add(customerID);
		
		//panelA.add(validTo);
		
		//panelA.add(fees);
		panelA.setLayout(new BoxLayout(panelA,BoxLayout.Y_AXIS));
	
		panelA.add(BorderLayout.AFTER_LAST_LINE, AddMembership);
		frame.add(panelA);
		frame.setSize(300, 300);
		frame.setVisible(true);
		
		
		
	AddMembership.addActionListener(this);
	
	
		
		
		
	}



	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		//AddMembership.setText("hello");
		
		if (e.getSource() == AddMembership)
			try{
				System.out.println("inside \n");
				System.out.println(customerID);
		    int cid = Integer.parseInt(customerID.getText());
		    System.out.println(cid);
		    SimpleDateFormat formatter1 = new SimpleDateFormat("MM/dd/yyyy");
		    System.out.println(validFrom.getText());
		   java.util.Date d = formatter1.parse(validFrom.getText());
		   System.out.println(formatter1.format(d));
		   java.sql.Date d1 = new java.sql.Date(d.getTime());
		    System.out.println("inside1 \n");
		  //  int vt1 = Integer.parseInt(validTo.getText());
		    double ap = Double.parseDouble(amountPaid.getText());
		//    double fees1 = Double.parseDouble(fees.getText());
		    String type1 = type.getText();
		    
		    String cid1 = Integer.toString(cid);
		   // String vf2 = Integer.toString(d);
		  //  String vt2 = Integer.toString(vt1);
		    String ap1 = Double.toString(ap);
		  //  String fees2 = Double.toString(fees1);
		    
		  
		    System.out.println("inside2 \n");
		    PreparedStatement ps;
		    String answer = "INSERT into Membership VALUES" + "(?,?,?,?)";
		    ps = con.prepareStatement(answer);
		    
		    System.out.println("inside3 \n");
		    ps.setString(1, type1);
		    ps.setDate(2, d1);
		    ps.setString(3, ap1);
		    ps.setString(4, cid1);
		    System.out.println("inside4 \n");
		    ps.executeUpdate();
          //  ps.setString(4, vt2);
            
       //     ps.setString(5, fees2);
          
		    
            
		   
		    
		   // String sqldata = ""
		    
		    }
		catch (NumberFormatException | SQLException | ParseException e1) {
			
		    System.out.println("Wrong number.Try Again!");
		    System.out.println(e1.getMessage());
			JOptionPane.showMessageDialog(null, "Try Again!Wrong Input");
		}
		
		
	}



}
