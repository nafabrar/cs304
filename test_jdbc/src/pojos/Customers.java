package pojos;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

public class Customers {
	/* Variables from the database model: */
	public int cid ;
	public String name ;
	public String phone;
	public String sAddress;
	public String pCode;
	public String email;


/* The connection to the database (necessary to execute queries): */
private Connection con;

public Customers() {}}
