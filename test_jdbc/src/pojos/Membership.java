package pojos;

import java.sql.Connection;
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
	} }
    
