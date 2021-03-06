/*
This is the list of all sql queries used in the assignment, asked for by the assginment
 */

/*  
Get Employee By Name
This query is simple and self explanatory
 */
SELECT * FROM employee WHERE name = ?;


/*
Employee Selection/Projection query
This query is built up in java based on user choices and is quite complex. It allows a user to select
specific fields from the employee table, and check whether they match specific patterns. It also allows for
the user to join on the instructor and manager table to check if the employee is a manager/instructor.
The logic that constructs it is fairly complex
Please see the EmployeeDemoSelectProject method in DataAccess.java
 */
 
 /* Insert a new Membership into the Membership table
 This was quite an intricate query that required to convert and parse different texts (including Date) 
 that are inputted by the user in the GUI text boxes in order to successfully add the new Membership in the Membership table.
 */
 "INSERT into Membership VALUES" + "(?,?,?,?)"
 
 /* Delete Employee by sin
 this is a cascade on delete situation to remove an employee and records associated with them in other tables.
 */
 DELETE FROM employee WHERE sin = ?;

/* Get All Classes with Counts 
This fills a dashboard-style table with information about classes. It aggregates up the customertakesclass table to count
the waitlist and enrollment numbers for each class
 */
Select c.classid, c."size", c.startTime, c.endTime,
 t.name , t.description, e.name, b.streetAddress, enrolled.ec, waitlist.wl 
 FROM Class c
 Inner Join ClassType t on c.type = t.name
 Inner Join Employee e on c.instructor = e.sin
 Inner Join BranchManaged b on c.branch = b.branchId
 Left Join (
   select tce.classID, COUNT(*) as ec
   FROM CustomerTakesClass tce
   WHERE tce.isOnWaitList = 0 OR tce.isOnWaitList is null
   GROUP BY tce.classID
 ) enrolled ON c.classid = enrolled.classID
 Left Join (
   select tcw.classID, COUNT(*) as wl
   FROM CustomerTakesClass tcw
   Where tcw.isOnWaitlist > 0
   GROUP BY tcw.classID
 ) waitlist ON c.classid = waitlist.classid

/*
  Division Operation
  This selects all classes taken by everybody
  It's a bit artificial (I'm not sure that a gym would actually have any such clases in real life) but it was
  required for the demo
  It uses the double negative technique to implement relational division in SQL
*/
SELECT UNIQUE a.ClassID, cl.Type FROM CustomerTakesClass a LEFT JOIN
Class cl ON a.ClassID = cl.ClassID
WHERE NOT EXISTS (
  SELECT customerID from
  Customer b
  WHERE NOT EXISTS (
      SELECT * from CustomerTakesClass c
      WHERE c.ClassID = a.ClassID
      AND c.CustomerID = b.CustomerID
    )
)



/*UPDATE operation with CHECK constraint
Using this customers can update their own customer info. They can update their info
provided they maintain the CHECK constraint of postal code and phone number.They have to provide
correct inpur in all the fields or there will be error popups.*/
update Customer set name = ?,phoneNumber = ?,streetAddress = ?,"
  + "postalCode = ?,emailAddress = ? where customerID = ? ;

/*Delete Operation ON-CASCADE-DELETE
Customers can quit classes by deleting their record from CustomerTakesClass
with their classid and customerid*/
delete from CustomerTakesClass where customerID = ? and classid = ? ;

/* SELECT,INSERT Operation
Customers signup to customers table if they dont already have an account using this query.
If they already exist in the customer table they cannot signup again.They have to login with their customer id.*/
SELECT customerID from Customer WHERE customerID = ?

INSERT INTO Customer(customerID, name, phoneNumber, streetaddress, postalcode,emailAddress )
  VALUES (?, ?, ?, ?, ?, ?);







