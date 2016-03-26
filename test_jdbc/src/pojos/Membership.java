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

	public Membership(Connection con) {
		this.con = con;
	}
	
	public int getCid() {
		return customerID;
	}

	public char getType() {
		return type;
	}

	public int validFrom() {
		return validFrom;
	}

	public int validTo() {
		return validTo;
	}
	
	public double amountPaid() {
		return amountPaid;
	}
	
	public double fees() {
		return fees;
	}
	

	public void addMemberShip(int customerID, char type, int validFrom, int validTo,
			double amountPaid, double fees){
		try {
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
		
		
			con = DriverManager.getConnection( "jdbc:oracle:thin:@dbhost.ugrad.cs.ubc.ca:1522:ug",
					"ora_s4t8", "a38993127");
	 
		Statement stmt = con.createStatement();
		Statement stmt1 = con.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT CustomerID FROM Customers"); // RETRIEVE CUSTOMERIDS FROM CUSTOMERS
		ResultSet rs1 = stmt.executeQuery("Select CustomerID FROM Membership"); // RETRIEVE CUSTOMERIDS FROM MEMBERSHIP
			
		// INSERT INTO MEMBERSHIP TABLE
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
			
	
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} }
	
	/*
	public boolean cancelMembership(int CustomerId, char type, int validFrom, int validTo, double amountPaid,
			double fees) {
				return false	
		
	}
	
	*/
    
	
	/*
      public void addMembership(int customerID, char type,int validFrom, int validTo, double amountPaid,
    		 double fees) {
    	  
    	  Customers cust = new Customers(con);
    	//  if (customerID =)
    	  
    	*/ 
    }
    
    
