

--drop the tables if they exist
DROP TABLE Membership;
DROP TABLE MembershipType;
DROP TABLE MembershipDate;
DROP TABLE CustomerTakesClass;
DROP TABLE Class;
DROP TABLE ClassType;
DROP TABLE Customer;
DROP TABLE BranchManaged;
DROP TABLE Manager;
DROP TABLE Instructor;
DROP TABLE Employee;
DROP TABLE PostalCodeLocation;


--create tables
CREATE TABLE ClassType(
    name CHAR(50),
    description CHAR(500),
    PRIMARY KEY (name)
);

grant select on ClassType to public;

CREATE TABLE MembershipType(
    type Char(20),
    fees REAL,
    PRIMARY KEY(type)
);

grant select on MembershipType to public;
 
CREATE TABLE MembershipDate(
    type Char(20),
    validFrom DATE,
    validTo Date,
    PRIMARY KEY(type, validFrom)
);

grant select on MembershipDate to public;

CREATE TABLE PostalCodeLocation(
    postalCode CHAR(6),
    province CHAR(50),
    city CHAR(50),
    PRIMARY KEY (postalCode)
);

grant select on PostalCodeLocation to public;

CREATE TABLE Customer(
    customerID INTEGER,
    name CHAR(50),
    phoneNumber CHAR(12) UNIQUE, --CHECK (phoneNumber LIKE '[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]'),
    streetAddress CHAR(50),
    postalCode CHAR(6) NOT NULL, --CHECK (postalCode LIKE '[A-Y][0-9][A-Z][0-9][A-Z][0-9]'),
    emailAddress CHAR(50) UNIQUE,
    PRIMARY KEY(customerId),
    FOREIGN KEY(postalCode) REFERENCES PostalCodeLocation(postalCode),
    CONSTRAINT phoneNumber_contraint CHECK (REGEXP_LIKE(phoneNumber,'[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]')),
    CONSTRAINT postalCode_constraint CHECK (REGEXP_LIKE(postalCode,'[A-Y][0-9][A-Z][0-9][A-Z][0-9]'))
);

grant select on Customer to public;    

CREATE TABLE Membership(
    type CHAR(20),
    validFrom  DATE,
    amountPaid REAL,  
    customerId INTEGER,
    PRIMARY KEY (type, validFrom, customerId),
    FOREIGN KEY (type) REFERENCES membershipType,
    FOREIGN KEY (type, validFrom) REFERENCES membershipDate,
    FOREIGN KEY (CustomerId) REFERENCES Customer
                        ON DELETE CASCADE
);

grant select on Membership to public;

CREATE TABLE Employee(
    sin INTEGER,
    name CHAR(50),
    jobTitle CHAR(50),
    phoneNumber CHAR(15),
    streetAddress CHAR(50),
    postalCode CHAR(6),
    emailAddress CHAR(100),
    Primary Key (sin),
    Foreign Key (postalCode) REFERENCES PostalCodeLocation
);

grant select on Employee to public;

CREATE TABLE Instructor(
    sin INTEGER,
    PRIMARY KEY (sin),
    FOREIGN KEY (sin)
        REFERENCES Employee(sin)
        ON DELETE CASCADE
);

grant select on Instructor to public;

CREATE TABLE Manager(
    sin INTEGER,
    PRIMARY KEY (sin),
    FOREIGN KEY (sin) REFERENCES employee
);

grant select on Manager to public;


CREATE TABLE BranchManaged(
            branchId INTEGER,
            streetAddress CHAR(50) UNIQUE,
            postalCode CHAR(6) NOT NULL,
            phoneNumber CHAR(25) UNIQUE,
            manager INTEGER UNIQUE NOT NULL,
            PRIMARY KEY(branchId),
            FOREIGN KEY(manager) REFERENCES Manager(sin),
            FOREIGN KEY(postalCode) REFERENCES PostalCodeLocation(postalCode)
);

grant select on BranchManaged to public;

 
CREATE TABLE Class(
    classId INTEGER,
    "size" INTEGER,
    startTime TIMESTAMP,
    endTime TIMESTAMP,
    type CHAR(50) NOT NULL,
    instructor INTEGER NOT NULL,
    branch INTEGER NOT NULL,
    isFull Int,
    currentlyRun Int,
    PRIMARY KEY (classId),
    FOREIGN KEY (type) 
        REFERENCES ClassType(name)
        ON DELETE CASCADE,
    FOREIGN KEY (instructor) 
        REFERENCES Instructor(sin)
        ON DELETE CASCADE,
    FOREIGN KEY (branch) 
        REFERENCES BranchManaged(branchId)
        ON DELETE CASCADE
);

grant select on Class to public;
 
 
CREATE TABLE CustomerTakesClass(
    customerID INTEGER,
    classID INTEGER,
    signupTime TIMESTAMP,
    isOnWaitlist INT,
    PRIMARY KEY (customerID, classID),
    FOREIGN KEY (customerID) REFERENCES Customer(customerID)
    ON DELETE CASCADE,
    FOREIGN KEY (classID) REFERENCES Class(classID)
                        	ON DELETE CASCADE
);

grant select on CustomerTakesClass to public;
 



--insert the tuples
INSERT ALL
    INTO ClassType(name, description) VALUES ('Yoga', 'A relaxing, meditative class for a slow burn')
    INTO ClassType(name, description) VALUES ('Retro Spin', 'Intense stationary biking to top hits from the 80s')
    INTO ClassType(name, description) VALUES ('Muay Thai', 'The famous combat sport from Thailand!')
    INTO ClassType(name, description) VALUES ('Kick boxing', 'Get your adrenaline pumping with a full body workout')
    INTO ClassType(name, description) VALUES ('Dance', 'Burn calories while having fun in this upbeat class')
    INTO ClassType(name, description) VALUES ('Basic Safety', 'A required Safety Course that everyone must take')
SELECT 1 FROM DUAL;



INSERT ALL
    INTO MembershipType(type, fees) VALUES ('Monthly', 115.00)
    INTO MembershipType(type, fees) VALUES ('Annual', 1100.00)
    INTO MembershipType(type, fees) VALUES ('Drop-in', 20.00)
    INTO MembershipType(type, fees) VALUES ('Weekly', 50.00)
    INTO MembershipType(type, fees) VALUES ('Half-year', 600.00)
SELECT 1 FROM DUAL;

INSERT ALL
    INTO MembershipDate(type, validFrom, ValidTo) VALUES ('Monthly', to_date('2015-10-07', 'YYYY-MM-DD'), '2015-11-08')
    INTO MembershipDate(type, validFrom, ValidTo) VALUES ('Annual', to_date('2016-01-01', 'YYYY-MM-DD'), '2017-01-01')
    INTO MembershipDate(type, validFrom, ValidTo) VALUES ('Drop-in', to_date('2015-12-23', 'YYYY-MM-DD'), '2015-12-29')
SELECT 1 FROM DUAL;
 
INSERT ALL
    INTO PostalCodeLocation(postalCode, province, city) VALUES ('V6E9J0', 'Vancouver', 'BC')
    INTO PostalCodeLocation(postalCode, province, city) VALUES ('V5F6G7', 'Vancouver', 'BC')
    INTO PostalCodeLocation(postalCode, province, city) VALUES ('V9U1MC', 'Vancouver', 'BC')
    INTO PostalCodeLocation(postalCode, province, city) VALUES ('B5R3E2', 'Burnaby', 'BC')
    INTO PostalCodeLocation(postalCode, province, city) VALUES ('B3S1W4', 'Burnaby', 'BC')
    INTO PostalCodeLocation(postalCode, province, city) VALUES ('VL9M2N', 'Vancouver', 'BC')
    INTO PostalCodeLocation(postalCode, province, city) VALUES ('V4D0B4', 'Vancouver', 'BC')
    INTO PostalCodeLocation(postalCode, province, city) VALUES ('V4K1T0', 'Vancouver', 'BC')
    INTO PostalCodeLocation(postalCode, province, city) VALUES ('B7R8L9', 'Burnaby', 'BC')
    INTO PostalCodeLocation(postalCode, province, city) VALUES ('V5H1Q2', 'Vancouver', 'BC')
    INTO PostalCodeLocation(postalCode, province, city) VALUES ('V5K2W1', 'Vancouver', 'BC')
    INTO PostalCodeLocation(postalCode, province, city) VALUES ('V4R1Q1', 'Vancouver', 'BC')
    INTO PostalCodeLocation(postalCode, province, city) VALUES ('V4E3T5', 'Vancouver', 'BC')
    INTO PostalCodeLocation(postalCode, province, city) VALUES ('V54Q31', 'Vancouver', 'BC')
    INTO PostalCodeLocation(postalCode, province, city) VALUES ('V2V5GT', 'Vancouver', 'BC')
    INTO PostalCodeLocation(postalCode, province, city) VALUES ('V9M1Y0', 'Vancouver', 'BC')
    INTO PostalCodeLocation(postalCode, province, city) VALUES ('B5K1L9', 'Burnaby', 'BC')
    INTO PostalCodeLocation(postalCode, province, city) VALUES ('B6J9L6', 'Burnaby', 'BC')
    INTO PostalCodeLocation(postalCode, province, city) VALUES ('B5K1L5', 'Burnaby', 'BC')
SELECT 1 FROM DUAL;

INSERT ALL
    INTO Customer(customerID, name, phoneNumber, streetAddress, postalCode, emailAddress) VALUES (1, 'Louise Lane','6049876543', '124 Eldar st', 'V5H1Q2', 'louise.lane@gmail.com')
    INTO Customer(customerID, name, phoneNumber, streetAddress, postalCode, emailAddress) VALUES (2, 'Tom Carpenter','7789034356', '125 Rocky Road', 'B7R8L9', 'tom_carpenter78@gmail.com')
    INTO Customer(customerID, name, phoneNumber, streetAddress, postalCode, emailAddress) VALUES (3, 'Man Ray','6045678912', '3333 Marion Drive', 'V4K1T0', 'man.ray@hotmail.com')
    INTO Customer(customerID, name, phoneNumber, streetAddress, postalCode, emailAddress) VALUES (4, 'Susanne Cook','7784531234', '4568 W Broadway', 'V4D0B4', 's.cook@gmail.com')
    INTO Customer(customerID, name, phoneNumber, streetAddress, postalCode, emailAddress) VALUES (5, 'Nancy Loue','6045212343', '2123 E Broadway', 'B5K1L5', 'n.lulu@hotmail.com')
SELECT 1 FROM DUAL;

INSERT ALL
    INTO Membership(type, validFrom, amountPaid, customerID) VALUES ('Monthly', to_date('2015-10-07', 'YYYY-MM-DD'), 115.00, 1)
    INTO Membership(type, validFrom, amountPaid, customerID) VALUES ('Annual', to_date('2016-01-01', 'YYYY-MM-DD'), 1100.00, 2)
    INTO Membership(type, validFrom, amountPaid, customerID) VALUES ('Drop-in', to_date('2015-12-23', 'YYYY-MM-DD'), 20.00, 3)
SELECT 1 FROM DUAL;

INSERT ALL
    INTO Employee(sin, name, jobTitle, phoneNumber, streetAddress, postalCode, emailAddress) VALUES (12345, 'Amelia Smith', 'Lead instructor', '7782222222', '#101 55 Main St', 'V5K2W1', 'amelia.smith@gmail.com')
    INTO Employee(sin, name, jobTitle, phoneNumber, streetAddress, postalCode, emailAddress) VALUES (54321, 'Sarah Summer', 'Lead instructor', '6044335465', '2145 W 27th', 'V4R1Q1', 's.summer@hotmail.com')
    INTO Employee(sin, name, jobTitle, phoneNumber, streetAddress, postalCode, emailAddress) VALUES (23232, 'Brad Hunter', 'Instructor/Manager', '7789023456', '143 E 10th', 'V4E3T5', 'brad.hunter@goodgym.com')
    INTO Employee(sin, name, jobTitle, phoneNumber, streetAddress, postalCode, emailAddress) VALUES (43215, 'John Addams', 'junior instructor', '6042324455', '#102 55 Main St', 'V5K2W1', 'johhny_94@hotmail.com')
    INTO Employee(sin, name, jobTitle, phoneNumber, streetAddress, postalCode, emailAddress) VALUES (12121, 'Suzy Quentin', 'front desk', '7789091212', '2345 Yew st', 'V54Q31', 'suzy_Q@gmail.com')
    INTO Employee(sin, name, jobTitle, phoneNumber, streetAddress, postalCode, emailAddress) VALUES (23451, 'Anna John', 'spin coach', '604545981', '777 Terrance Lane', 'V2V5GT', 'anna.john2343@gmail.com')
    INTO Employee(sin, name, jobTitle, phoneNumber, streetAddress, postalCode, emailAddress) VALUES (98765, 'Anna John', 'Manager', '778433011', '685 Louis Row', 'V9M1Y0', 'anna.john@goodgym.com')
    INTO Employee(sin, name, jobTitle, phoneNumber, streetAddress, postalCode, emailAddress) VALUES (87654, 'Bob OHare', 'Ultra manager', '7789990876', '123 Fake st', 'B5K1L9', 'bob.ohare@goodgym.com')
    INTO Employee(sin, name, jobTitle, phoneNumber, streetAddress, postalCode, emailAddress) VALUES (65432, 'Liam Chow', 'Regional Manager', '60498599944', '95 Templeton ave', 'B6J9L6', 'l.chow@goodgym.com')
    INTO Employee(sin, name, jobTitle, phoneNumber, streetAddress, postalCode, emailAddress) VALUES (97531, 'Tom Collins', 'manager', '7781123432', '145 Fake St', 'B5K1L5', 'tom.collins@goodgym.com')
SELECT 1 FROM DUAL;

INSERT ALL 
    INTO Instructor(sin) VALUES (12345)
    INTO Instructor(sin) VALUES (54321)
    INTO Instructor(sin) VALUES (23232)
    INTO Instructor(sin) VALUES (43215)
    INTO Instructor(sin) VALUES (23451)
SELECT 1 FROM DUAL;


INSERT ALL
    INTO Manager(sin) VALUES (23232)
    INTO Manager(sin) VALUES (98765)
    INTO Manager(sin) VALUES (87654)
    INTO Manager(sin) VALUES (65432)
    INTO Manager(sin) VALUES (97531)
SELECT 1 FROM DUAL;

INSERT ALL
    INTO BranchManaged(branchId, streetAddress, postalCode, phoneNumber, manager) VALUES (1, '74 Fake St', 'V6E9J0', '6043334545', 23232)
    INTO BranchManaged(branchId, streetAddress, postalCode, phoneNumber, manager) VALUES (2, '543 Venibles Dr', 'V5F6G7', '6043356677', 98765)
    INTO BranchManaged(branchId, streetAddress, postalCode, phoneNumber, manager) VALUES (3, '435 Tomphson Road', 'V9U1MC', '6047789546', 87654)
    INTO BranchManaged(branchId, streetAddress, postalCode, phoneNumber, manager) VALUES (4, '6555 Lucky Lane', 'B5R3E2', '6045431245', 65432)
    INTO BranchManaged(branchId, streetAddress, postalCode, phoneNumber, manager) VALUES (5, '4321 Randolph Road', 'B3S1W4', '6043567804', 97531)
SELECT 1 FROM DUAL;



INSERT ALL
    INTO Class(classId, "size", startTime, endTime, type, instructor, branch, isFull, currentlyRun) VALUES (1, 30, to_timestamp('2016-02-10 16:00:00', 'YYYY-MM-DD HH24:MI:SS'), to_timestamp('2016-02-10 17:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Retro Spin', 12345, 1, 0, 1)
    INTO Class(classId, "size", startTime, endTime, type, instructor, branch, isFull, currentlyRun) VALUES (2, 15, to_timestamp('2016-02-11 18:00:00', 'YYYY-MM-DD HH24:MI:SS'), to_timestamp('2016-02-11 19:15:00', 'YYYY-MM-DD HH24:MI:SS'), 'Yoga', 54321, 2, 1, 1)
    INTO Class(classId, "size", startTime, endTime, type, instructor, branch, isFull, currentlyRun) VALUES (3, 20, to_timestamp('2016-02-11 18:00:00', 'YYYY-MM-DD HH24:MI:SS'), to_timestamp('2016-02-11 19:15:00', 'YYYY-MM-DD HH24:MI:SS'), 'Yoga', 23232, 1, 0, 0)
    INTO Class(classId, "size", startTime, endTime, type, instructor, branch, isFull, currentlyRun) VALUES (4, 30, to_timestamp('2016-02-12 06:00:00', 'YYYY-MM-DD HH24:MI:SS'), to_timestamp('2016-02-12 07:15:00', 'YYYY-MM-DD HH24:MI:SS'), 'Muay Thai', 43215, 3, 0, 1)
    INTO Class(classId, "size", startTime, endTime, type, instructor, branch, isFull, currentlyRun) VALUES (5, 25, to_timestamp('2016-02-12 20:00:00', 'YYYY-MM-DD HH24:MI:SS'), to_timestamp('2016-02-12 21:30:00', 'YYYY-MM-DD HH24:MI:SS'), 'Dance', 43215, 2, 1, 1)
    INTO Class(classId, "size", startTime, endTime, type, instructor, branch, isFull, currentlyRun) VALUES (6, 1000, to_timestamp('2016-02-15 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), to_timestamp('2016-02-15 10:30:00', 'YYYY-MM-DD HH24:MI:SS'), 'Basic Safety', 12345, 2, 1, 1)
 SELECT 1 FROM DUAL;
 



 
INSERT ALL
    INTO CustomerTakesClass(customerID, classID, signupTime, isOnWaitlist) VALUES (1, 2, to_timestamp('2016-02-07 16:00:00', 'YYYY-MM-DD HH24:MI:SS'), 0)
    INTO CustomerTakesClass(customerID, classID, signupTime, isOnWaitlist) VALUES (2, 2, to_timestamp('2016-02-07 16:00:30', 'YYYY-MM-DD HH24:MI:SS'), 0)
    INTO CustomerTakesClass(customerID, classID, signupTime, isOnWaitlist) VALUES (3, 2, to_timestamp('2016-02-07 21:00:00', 'YYYY-MM-DD HH24:MI:SS'), 1)
    INTO CustomerTakesClass(customerID, classID, signupTime, isOnWaitlist) VALUES (4, 2, to_timestamp('2016-02-08 16:00:00', 'YYYY-MM-DD HH24:MI:SS'), 1)
    INTO CustomerTakesClass(customerID, classID, signupTime, isOnWaitlist) VALUES (1, 4, to_timestamp('2016-02-07 16:00:00', 'YYYY-MM-DD HH24:MI:SS'), 0)
    INTO CustomerTakesClass(customerID, classID, signupTime, isOnWaitlist) VALUES (1, 6, to_timestamp('2016-02-07 16:00:00', 'YYYY-MM-DD HH24:MI:SS'), 0)
    INTO CustomerTakesClass(customerID, classID, signupTime, isOnWaitlist) VALUES (2, 6, to_timestamp('2016-02-07 16:00:00', 'YYYY-MM-DD HH24:MI:SS'), 0)
    INTO CustomerTakesClass(customerID, classID, signupTime, isOnWaitlist) VALUES (3, 6, to_timestamp('2016-02-07 16:00:00', 'YYYY-MM-DD HH24:MI:SS'), 0)
    INTO CustomerTakesClass(customerID, classID, signupTime, isOnWaitlist) VALUES (4, 6, to_timestamp('2016-02-07 16:00:00', 'YYYY-MM-DD HH24:MI:SS'), 0)
    INTO CustomerTakesClass(customerID, classID, signupTime, isOnWaitlist) VALUES (5, 6, to_timestamp('2016-02-07 16:00:00', 'YYYY-MM-DD HH24:MI:SS'), 0)
SELECT 1 FROM DUAL;
 





