package repository;
//We need to import the java.sql package to use JDBC
import java.sql.*;

import pojos.Employee;

//for reading from the command line
import java.io.*;
//File i/o
import java.io.*;

public class DataAccess {
	
	 private static DataAccess instance = null;
	 private Connection con;
	 
	 protected DataAccess() {
	      // Exists only to defeat instantiation.
	   }
	 public static DataAccess getInstance() throws FileNotFoundException, IOException {
	      if(instance == null) {
	         instance = new DataAccess();
	         instance.initialize();
	      }
	      return instance;
	 }
	
	// This will create an ordered array of 3 strings,
	// containing the text of the top 3 lines of "secrets.txt"
	// These will be, in this order:
	// 1. The local port to use (should be tunneled to port 1522 on a
	// ugrad server)
	// 2. The username to use
	// 3. The password to use
	// This will always throw if it can't get that info, since
	// We can't do anything if we don't have a login
	 private String[] readSecrets() throws IOException, FileNotFoundException {

		 String[] result = new String[3];
		 File secretsFile = new File("../secrets.txt");
		 BufferedReader secretReader;
		try {
			secretReader = new BufferedReader(new FileReader(secretsFile));
		} catch (FileNotFoundException e) {
			System.out.println("Can't find Secrets File");
			System.out.println("Put secrets.txt in the root directory of the project");
			System.out.println("And make the sure current working directory test_jdbc");
			System.out.println("CWD: " + new File(".").getCanonicalPath());
			throw e;
		}
		 try {
			 for (int i = 0; i < 3; i++) {
				 result[i] = secretReader.readLine();
			 }
			 return result;
		 } finally {
			 secretReader.close();
		 }
	 }
	
	private boolean initialize() throws FileNotFoundException, IOException{
		//THIS WILL BE DIFFERENT FOR EACH PERSON
		//eventually will want to move this to a config file that we each have a diff 
		//local copy of
		
		String[] parameters = readSecrets();
		String connectURL = "jdbc:oracle:thin:@localhost:" + parameters[0] + ":ug";
		String username = parameters[1];
		String password = parameters[2];
		try {
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			con = DriverManager.getConnection(connectURL,username,password);
			System.out.println("\nConnected to Oracle!");
			return true;
		} catch (SQLException ex){
			System.out.println("Message: " + ex.getMessage());
			return false;
	   }
	}
	
	public Employee GetEmployeeByName(String employeeName){
		try{
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM employees WHERE name = "+ employeeName);
			int employeeId = Integer.parseInt(rs.getString("employeeId"));
			Employee emp = new Employee(employeeId);
			return emp;
		}catch (SQLException ex){
			System.out.println("Message: " + ex.getMessage());
			return null;
		}
		
	}
	
	
}
