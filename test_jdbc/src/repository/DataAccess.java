package repository;
//We need to import the java.sql package to use JDBC
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import pojos.AverageWaitlistResult;
import pojos.DivisionResult;
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
	
	public int EmployeeDelete(String sin){
		try{
			Statement stmt = con.createStatement();
			int numDeleted = stmt.executeUpdate("DELETE FROM employee WHERE sin = " + sin);
			System.out.println(numDeleted);
			return numDeleted;
		}catch(Exception ex){
			System.out.println("Delete employee message failed " + ex.getMessage());
			return -1;
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
		if((!eqType1.isEmpty() && !selectionValue1.isEmpty()) || eqType1.equals("is null")){
			if(eqType1.equals("starts with")){
				if(selectionField1.equals("sin")){
					query.append(" WHERE CAST(e.sin as VARCHAR(25)) LIKE \'" + selectionValue1 + "%\'");
				}else{
					query.append(" WHERE LOWER(e." + selectionField1.replaceAll(" ","") + ") LIKE lower(\'" 
					+ selectionValue1 + "\') || \'%\'");
				}
			}else if(eqType1.equals("is null")){
				query.append(" WHERE e." + selectionField1.replaceAll(" ","") + " is NULL");
			}else{
				query.append(" WHERE e." + selectionField1.replaceAll(" ","") + eqType1 + "\'" + selectionValue1 + "\'");
			}
		}
		if(!combinator.isEmpty() && ((!eqType2.isEmpty() && !selectionValue2.isEmpty()) || eqType2.equals("is null"))){
			query.append(" " + combinator);
			if(eqType2.equals("starts with")){
				if(selectionField2.equals("sin")){
					query.append(" CAST(e.sin as VARCHAR(25)) LIKE \'" + selectionValue2 + "%\'");
				}else{
					query.append(" LOWER(e." + selectionField2.replaceAll(" ","") + ") LIKE lower(\'" 
					+ selectionValue2 + "\') || \'%\'");
				}
			}else if(eqType2.equals("is null")){
				query.append(" e." + selectionField2.replaceAll(" ","") + " is NULL");
			}else{
				query.append(" e." + selectionField2.replaceAll(" ","") + eqType2 + "\'" + selectionValue2 + "\'");
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
				item.waitList = rs.getInt(10);
				
				result.add(item);
			}
			return result;
		} catch (SQLException ex){
			System.out.println("Message: " + ex.getMessage());
			return new ArrayList<GymClassListItem>();
		}
	}
	
	// This is the division operator - it finds all classes taken by everyone
	// There should be one in our initial data set, ClassID 6, the basic safety clas
	public List<DivisionResult> classesEveryoneTakes() {
		Statement stmt;
		try {
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT UNIQUE a.ClassID, cl.Type FROM CustomerTakesClass a LEFT JOIN"
					+ "  Class cl ON a.ClassID = cl.ClassID"
					+ "  WHERE NOT EXISTS ("
					+ "    SELECT customerID from"
					+ "    Customer b"
					+ "    WHERE NOT EXISTS (" 
					+ "      SELECT * from CustomerTakesClass c"
					+ "      WHERE c.ClassID = a.ClassID"
					+ "      AND c.CustomerID = b.CustomerID"
					+ "    )"
					+ "  )"
					);

			List<DivisionResult> result = new ArrayList<DivisionResult>();
			while(rs.next()) {
				DivisionResult item = new DivisionResult();
				item.classID = rs.getInt(1);
				item.typeName = rs.getString(2);
				result.add(item); //ClassID
			}
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ArrayList<DivisionResult>();
		}
	}
	
	/*
	 *  Select the class type with the minimum or maximum waitlist size
	 *  @param doMin: true if it should select the min, otherwise it should select max
	 */
	public AverageWaitlistResult averageWaitlistMinORMax(boolean doMin) {
		Statement stmt;
		try {
			stmt = con.createStatement();
			String desc = " DESC ";
			if (doMin) {
				desc = " ASC ";
			}

			ResultSet rs = stmt.executeQuery(
						"SELECT tname, avg FROM ( "
						+ " SELECT t.name as tname, (COALESCE(AVG(waitlist.wl), 0)) as avg "
							+ "  FROM ClassType t  "
							+ "  LEFT JOIN Class c on t.name = c.type "
							+ "  LEFT JOIN ( "
							+ "    SELECT tcw.classID, COALESCE(COUNT(*), 0) AS wl "
							+ "    FROM CustomerTakesClass tcw "
							+ "    WHERE tcw.isOnWaitlist > 0 "
							+ "    GROUP BY tcw.classID "
							+ "  ) waitlist ON c.classid = waitlist.classid "
							+ "  GROUP BY t.name "
							+ " ORDER BY avg" + desc
							+ ") "
							+ " WHERE ROWNUM = 1"
					);

			
			AverageWaitlistResult result = new AverageWaitlistResult();
			result.classType = "";
			result.averageWaitlist = 0;
			if (rs.next()) {
				result.classType = rs.getString(1);
				result.averageWaitlist = rs.getInt(2);
			}
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			AverageWaitlistResult result = new AverageWaitlistResult();
			result.classType = "";
			result.averageWaitlist = 0;
			return result;
		}
	}


}
