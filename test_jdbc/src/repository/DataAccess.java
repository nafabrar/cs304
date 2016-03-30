package repository;
//We need to import the java.sql package to use JDBC
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import pojos.Employee;
import pojos.GymClassListItem;

//for reading from the command line
import java.io.*;

public class DataAccess {
	
	 private static DataAccess instance = null;
	 private Connection con;
	 
	 protected DataAccess() {
	      // Exists only to defeat instantiation.
	   }
	 public static DataAccess getInstance()  {
	      if(instance == null) {
	         instance = new DataAccess();
	         try {
				instance.initialize();
			} catch (FileNotFoundException e) {
				System.out.println("COULD NOT CONNECT TO DB");
				e.printStackTrace();
			} catch (IOException e) {
				System.out.println("COULD NOT CONNECT TO DB");
				e.printStackTrace();
			}
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
	
	public List<GymClassListItem> getAllClassesWithCounts() {
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(
					"Select c.classid, c.\"size\", c.startTime, c.endTime, "
							+ " t.name , t.description, e.name, b.streetAddress, enrolled.ec, waitlist.wl "
							+ " FROM Class c"
							+ " Inner Join ClassType t on c.type = t.name"
							+ " Inner Join Employee e on c.instructor = e.sin"
							+ " Inner Join BranchManaged b on c.branch = b.branchId"
							+ " Left Join ("
							+ "   select tce.classID, COUNT(*) as ec"
							+ "   FROM CustomerTakesClass tce"
							+ "   WHERE tce.isOnWaitList = 0 OR tce.isOnWaitList is null"
							+ "   GROUP BY tce.classID"
							+ " ) enrolled ON c.classid = enrolled.classID"
							+ " Left Join ("
							+ "   select tcw.classID, COUNT(*) as wl"
							+ "   FROM CustomerTakesClass tcw"
							+ "   Where tcw.isOnWaitlist > 0"
							+ "   GROUP BY tcw.classID"
							+ " ) waitlist ON c.classid = waitlist.classid"
					);
			
			List<GymClassListItem> result = new ArrayList<GymClassListItem>();

			while(rs.next()) {
				GymClassListItem item = new GymClassListItem();
				item.classID = rs.getInt(1);
				item.size = rs.getInt(2);
				item.startTime = rs.getDate(3);
				item.endTime = rs.getDate(4);
				item.classType = rs.getString(5);
				item.description = rs.getString(6);
				item.teacherName = rs.getString(7);
				item.address = rs.getString(8);
				item.inClass = rs.getInt(9);
				item.inClass = rs.getInt(10);
				
				result.add(item);
			}
			return result;
		} catch (SQLException ex){
			System.out.println("Message: " + ex.getMessage());
			return null;
		}
	}
	
	
}
