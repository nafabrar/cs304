package src.pojos;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Membership {
	int customerID;
	char type;
	int validFrom;
	int validTo;
	double amountPaid;
	double fees;
	private Connection con;

	public static void main(String[] args) throws SQLException {
		DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
		Connection con = DriverManager.getConnection( "jdbc:oracle:thin:@dbhost.ugrad.cs.ubc.ca:1522:ug",
				"ora_s4t8", "a38993127");
		Statement stmt = con.createStatement();
		Statement stmt1 = con.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT CustomerID FROM Customers"); // RETRIEVE CUSTOMERIDS FROM CUSTOMERS
		ResultSet rs1 = stmt.executeQuery("Select CustomerID FROM Membership"); // RETRIEVE CUSTOMERIDS FROM MEMBERSHIP
			
		// ADD MEMBERSHIP TYPE TO CUSTOMER TABLE
				while (rs.next()) {
					if (rs.equals(rs1)); 
					Statement stmt2 = con.createStatement();
					int rs2 = stmt2.executeUpdate("INSERT INTO Membership " +
			                   "VALUES('customerId', 'type', 'validFrom', 'validTo', 'amountpaid', 'fees')");
				
					//	System.out.println(rs2.getString(1));
					
					stmt.close();
					stmt1.close();
					stmt2.close();
					con.close();
					}
	}
    
	
	/*
      public void addMembership(int customerID, char type,int validFrom, int validTo, double amountPaid,
    		 double fees) {
    	  
    	  Customers cust = new Customers(con);
    	//  if (customerID =)
    	  
    	*/ 
    }
    
    
