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
			String selectionField1, String selectionValue1, String eqType1, String combinator,
			String selectionField2, String selectionValue2, String eqType2, 
			Boolean isInstructor, Boolean isManager){
		
		List<String> queryPFields = new ArrayList<String>();
		for(int i = 0; i < projectionFields.size(); i++){
			queryPFields.add("e." + projectionFields.get(i).replaceAll(" ",""));
		}
	
		List<Employee> matchingEmployees = new ArrayList<Employee>();
		StringBuilder query = 
				new StringBuilder("SELECT " + String.join(", ", queryPFields) + " FROM employee e");
		if(isInstructor){
			query.append(" INNER JOIN instructor i ON i.sin = e.sin");
		}
		if(isManager){
			query.append(" INNER JOIN manager m ON m.sin = e.sin");
		}
		if(!eqType1.isEmpty() && !selectionValue1.isEmpty()){
			if(eqType1.equals("starts with")){
				if(selectionField1.equals("sin")){
					query.append(" WHERE CAST(e.sin as VARCHAR(25)) LIKE \'" + selectionValue1 + "%\'");
				}else{
					selectionValue1.replaceAll(" ","");
					query.append(" WHERE LOWER(e." + selectionField1.replaceAll(" ","") + ") LIKE lower(\'" 
					+ selectionValue1 + "\') || \'%\'");
				}
			}else if(eqType1.equals("is null")){
				query.append(" WHERE e." + selectionField1 + " is NULL");
			}else{
				query.append(" WHERE e." + selectionField1 + eqType1 + "\'" + selectionValue1 + "\'");
			}
		}
		if(!combinator.isEmpty() && !eqType2.isEmpty() && !selectionValue2.isEmpty()){
			query.append(" " + combinator);
			if(eqType2.equals("starts with")){
				if(selectionField2.equals("sin")){
					query.append(" CAST(e.sin as VARCHAR(25)) LIKE \'" + selectionValue2 + "%\'");
				}else{
					selectionValue2.replaceAll(" ","");
					query.append(" LOWER(e." + selectionField2.replaceAll(" ","") + ") LIKE lower(\'" 
					+ selectionValue2 + "\') || \'%\'");
				}
			}else if(eqType1.equals("is null")){
				query.append(" e." + selectionField2 + " is NULL");
			}else{
				query.append(" e." + selectionField2 + eqType2 + "\'" + selectionValue2 + "\'");
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
				for(int i = 0; i < projectionFields.size(); i++){
					switch(projectionFields.get(i)){
						case "name":
							emp.name = rs.getString("name");
							break;
						case "sin":
							emp.sin = rs.getInt("sin");
							break;
						case "email address":
							emp.email = rs.getString("emailaddress");
							break;
						case "job title":
							emp.jobtitle = rs.getString("jobtitle");
							break;
						case "phone number":
							emp.phoneNumber = rs.getString("phonenumber");
							break;
						case "postal code":
							emp.postalCode = rs.getString("postalcode");
							break;
						case "street address":
							emp.address = rs.getString("streetaddress");
							break;
						default:
							System.out.println("column not found");
							break;
					}
				}
				
				matchingEmployees.add(emp);
			}
		}catch (SQLException ex){
			System.out.println("Message: " + ex.getMessage());
			return null;
		}
		
		System.out.println("Employees selected" + matchingEmployees.size());
		return matchingEmployees;
	}
	
	
}
