/*
This is the list of all sql queries used in the assignment, asked for by the assginment
 */

/*  
Get Employee By Name
This query is simple and self explanatory
 */
SELECT * FROM employees WHERE name = ?;

/*
Employee Selection/Projection query
This query is built up in java based on user choices and is quite complex. It allows a user to select
specific fields from the employee table, and check whether they match specific patterns.
The logic that constructs it is fairly complex
Please see the EmployeeDemoSelectProject method in DataAccess.java
 */

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
