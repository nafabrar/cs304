package pojos;
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
		
	//	ResultSet rs = stmt.executeQuery("SELECT C.name from Customer C, Membership"
	//			+ "M where C.customerID = M.customerID"); // RETRIEVE CUSTOMERIDS FROM CUSTOMERS
			
		// INSERT INTO MEMBERSHIP TABLE
				
					Statement stmt2 = con.createStatement();
					int rs2 = stmt2.executeUpdate("INSERT INTO Membership " +
			                   "VALUES('customerId', 'type', 'validFrom', 'validTo', 'amountpaid', 'fees')");
				
					//	System.out.println(rs2.getString(1));
					
					stmt.close();
					
					stmt2.close();
					con.close();
					
					
				
	
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} }
	
	public void cancelMembership(int CustomerId, char type, int validFrom, int validTo, double amountPaid,
			double fees, boolean matchedID) {
		
		try {
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
		
			con = DriverManager.getConnection( "jdbc:oracle:thin:@dbhost.ugrad.cs.ubc.ca:1522:ug",
					"ora_s4t8", "a38993127");
	 
		Statement stmt = con.createStatement();
		Statement stmt1 = con.createStatement();
		
		ResultSet rs = stmt.executeQuery("SELECT customerID FROM Customers"); // RETRIEVE CUSTOMERIDS FROM CUSTOMERS
		
		ResultSet rs1 = stmt1.executeQuery("SELECT CustomerID FROM Membership"); // RETRIEVE CUSTOMERIDS FROM MEMBERSHIP
		while (rs.next()) {
			while (rs1.next()) {
			//	matchedID = (rs.getArray(customerID) == rs1.getArray(customerID ));
			if (rs.getInt(1) == rs1.getInt(1)) {
				matchedID = true;
				ResultSet rs2 = stmt.executeQuery("DELETE FROM Membership WHERE matchedID = 'true'");
//				System.out.println(rs2.getString(1));
				
			
			}
			}
			
		}
		
		stmt.close();
		stmt1.close();
		con.close();			
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
    }
}
    
