package repository;
//We need to import the java.sql package to use JDBC
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import pojos.Employee;

//for reading from the command line
import java.io.*;

public class DataAccess {
	 private static DataAccess instance = null;
	 private Connection con;
	 
	 protected DataAccess() {
	      // Exists only to defeat instantiation.
	   }
	 public static DataAccess getInstance() {
	      if(instance == null) {
	         instance = new DataAccess();
	         instance.initialize();
	      }
	      return instance;
	 }
	
	private boolean initialize(){
		//THIS WILL BE DIFFERENT FOR EACH PERSON
		//eventually will want to move this to a config file that we each have a diff 
		//local copy of
		String connectURL = "jdbc:oracle:thin:@localhost:1522:ug"; 
		String username = "ora_i0l8";
		String password = "a33435124";
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
			Employee emp = new Employee();
			return emp;
		}catch (SQLException ex){
			System.out.println("Message: " + ex.getMessage());
			return null;
		}
	}
	
	public List<Employee> EmployeeDemoSelectProject(List<String> projectionFields, 
			String selectionField, String selectionValue, Boolean isInstructor, Boolean isManager){
	
		List<Employee> matchingEmployees = new ArrayList<Employee>();
		StringBuilder query = 
				new StringBuilder("SELECT " + String.join(",", projectionFields).replaceAll(" ","")
						.replaceAll(",",", ") + " FROM employees e");
		if(isInstructor){
			query.append(" INNER JOIN instructors i ON i.id = e.id");
		}
		if(isManager){
			query.append(" INNER JOIN managers m ON m.id = e.id");
		}
		if(!selectionValue.isEmpty()){
			if(selectionField.equals("sin")){
				query.append(" WHERE CAST(e.sin as VARCHAR(25)) LIKE \'%" + selectionValue + "\'");
			}else{
				selectionValue.replaceAll(" ","");
				query.append(" WHERE UPPER(e." + selectionField.replaceAll(" ","") + ") LIKE lower(" 
				+ selectionValue + ") || \'%\'");
			}
		}
		
		System.out.println(query.toString());
		try{
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query.toString());
			//NEED TO TRANSLATE RESULT TO SET OF EMPLOYEES
			while(rs.next())
			{
				Employee emp = new Employee();
				emp.name = rs.getString("name");
				emp.email = rs.getString("email");
				emp.sin = rs.getInt("sin");
				emp.jobtitle = rs.getString("jobtitle");
				emp.phoneNumber = rs.getString("phonenumber");
				emp.postalCode = rs.getString("postalcode");
				matchingEmployees.add(emp);
			}
		}catch (SQLException ex){
			System.out.println("Message: " + ex.getMessage());
			return null;
		}
		
		return matchingEmployees;
	}
	
	
}
