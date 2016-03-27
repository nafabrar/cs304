package Customers;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

public class Customers {
	/* Variables from the database model: */
	int cid ;
	String name ;
	String phone;
	String sAddress;
	String pCode;
	String email;



/* The connection to the database (necessary to execute queries): */
private Connection con;

public Customers(Connection con) {
	this.con = con;
}

public int getCid() {
	return cid;
}

public String getName() {
	return name;
}

public String getAddress() {
	return sAddress;
}

public String getPhone() {
	return phone;
}


//If a customer takes a class for first time then he/she have to signup for an account
//if he/she already have an account then he/she can login 
public boolean signup(int cid, String name, 
String phone,String sAddress,String pCode,String email)
{
	try {
	PreparedStatement  ps;
	
	//Statement stmt = con.createStatement();
	ResultSet rs;
	
	
	rs = ps.executeQuery("SELECT customerID" + "Customer(customerID, name, phoneNumber, streetaddress, postalcode,emailAddress )"
	+ "WHERE customerID = ?");
	ps.setInt(1, cid);
	if (rs.next()){
		return false ;}
		
	else
		{ps = con.prepareStatement("INSERT INTO " +
		   "Customer(customerID, name, phoneNumber, streetaddress, postalcode,emailAddress )" +
					" VALUES (?, ?, ?, ?, ?, ?)");
		ps.setInt(1, cid);
		ps.setString(2, name);
		ps.setString(3,  phone);
		ps.setString(4, (sAddress.length() == 0) ? null : sAddress);
		ps.setString(5,  pCode);
		ps.setString(6, email);
		ps.executeUpdate();
		con.commit();
		ps.close();
		
		//Register class for the new customer 
		ps = con.prepareStatement("INSERT INTO CustomerTakesClass VALUES " +
				"(?, ?, ?, ?)");
					
		ps.executeUpdate();
		con.commit();
		ps.close();}}
		
		

	 catch (SQLException e) {
		System.out.println("Could not register user: " + e.getMessage());
		rollback();
		return false;
	}
	return true;
}




/** @return true if the customer logs in successfully */
public boolean login(int cid, String email) {
	
	ResultSet  rs;
	PreparedStatement  ps;

	try
	{
		ps = con.prepareStatement("SELECT *," +
				"FROM Customer C,CustomerTakesClass Cl, Membership M " +
				"WHERE C.customerID = ? AND C.emailAddress = ? " );
		
		ps.setInt(1, cid);
		ps.setString(2, email);
		
		rs = ps.executeQuery();
		
		
		ps.close();
		return true;
	}
	catch (SQLException ex)
	{
		System.out.println("Could not login: " + ex.getMessage());
	}	
	
	return false;
}

//addclass @return true if customer is added to class and @returns false if the customer is on waitlist	
public boolean addClass (int cid1,int clid)
{ 	
		
	ResultSet  rs;
	PreparedStatement  ps;
	try{
String sql = "SELECT *" + "FROM Class" + "WHERE size < 40 AND classID = ?";
ps = con.prepareStatement(sql);
				 ps.setInt(1, clid);
				 
				 rs=ps.executeQuery();
				 if (rs.next()){
					 String insert = "INSERT" + "INTO CustomerTakesClass (customerID,classID,signuptime,isOnWaitlist)" +
				 "VALUES (?,?,?,false)";
					 Statement stmt1 = con.prepareStatement(insert);
		              ps.setInt(1, cid1);
		              ps.setInt(2, clid);
		              
		     	java.util.Date date = new java.util.Date();
				long time = date.getTime();
		              //Passed the milliseconds to constructor of Timestamp class 
		     	 Timestamp ts = new Timestamp(time);
		              ps.setTimestamp(3, ts); return true;
		              }
				 else{String insert = "INSERT" + "INTO CustomerTakesClass (customerID,classID,signuptime,isOnWaitlist)" +
						 "VALUES (?,?,?,true)";
					 Statement stmt1 = con.prepareStatement(insert);
		              ps.setInt(1, cid1);
		              ps.setInt(2, clid);
		              java.util.Date date;
						long time = date.getTime();
				              //Passed the milliseconds to constructor of Timestamp class 
				     	 Timestamp ts = new Timestamp(time);
				              ps.setTimestamp(3, ts);
		              ps.setTimestamp(3, ts);
		              	return false;
		              	}}
				 
				 catch (SQLException ex)
					{
						System.out.println("Could not login: " + ex.getMessage());
					}	
					
				 return false;}
//Drop the beat
public boolean dropClass (int cid1,int clid)
{ 	
		
	ResultSet  rs;
	PreparedStatement  ps;
	try{
String record = "SELECT classID" + "FROM CustomerTakesClass" + "WHERE customerID = ? AND clid = ?";
 ps = con.prepareStatement(record);
				 ps.setInt(1, cid1);
				 ps.setInt(2, clid);
				 rs=ps.executeQuery();
				 
				 if (rs.next()){
					 String delete = "DELETE" + "FROM CustomerTakesClass" +
				 "WHERE customerID = ? AND classID = ? ";
					 Statement stmt1 = con.prepareStatement(delete);
		              ps.setInt(1, cid1);
		              ps.setInt(2, clid);
		              /*done deleting.now update the waitlist
		              first find the customer ID of hte person with min timestamp*/
		             String removefromwaitlist = "SELECT customerID" + 
		              "FROM CustomerTakesClass" + "WHERE isOnWaitlist = 'True' AND signuptime = MIN(signuptime)";
		             Statement stmt2 = con.prepareStatement(removefromwaitlist);
		             rs = stmt2.executeQuery("SELECT *" + 
		              "FROM CustomerTakesClass" + "WHERE isOnWaitlist = True AND signuptime = MIN(signuptime)");
		             
		             int cidr = rs.getInt(1);
		             int clidr = rs.getInt(2);
		             Timestamp timer = rs.getTimestamp(3);
		             boolean isonwaitlist = true;
		             //Add that customer to the class
		             String update = "UPDATE CustomerTakesClass"+"SET customerID = ?,classID = ?,signupTime = ?, isOnWaitlist = 'False'";
		             PreparedStatement ps2;
		             ps2 = con.prepareStatement(update);
		             ps2.setInt(1, cidr);
		             ps2.setInt(2, clidr);
		             ps2.setTimestamp(3, timer);
				     rs = ps2.executeQuery(update);
				     return true;
	              }
				 else{
					 	return false;}}
					 			
				     
				 catch (SQLException ex)
					{
						System.out.println("Could not login: " + ex.getMessage());
					}
	return false;	
	}








 

















}