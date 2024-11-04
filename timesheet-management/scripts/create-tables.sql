DROP TABLE IF EXISTS Holiday;
DROP TABLE IF EXISTS TimesheetEntry;
DROP TABLE IF EXISTS "User";
DROP TYPE IF EXISTS UserType;
DROP TABLE IF EXISTS Project;
DROP TABLE IF EXISTS Client;

CREATE TABLE Client (
	clientId SERIAL PRIMARY KEY,
    firstName VARCHAR(50) NOT NULL,
    lastName VARCHAR(50) NOT NULL
);

INSERT INTO Client (firstName, lastName)
VALUES (
	'Madison',
	'Vo'
);

CREATE TABLE Project (
	projectId SERIAL PRIMARY KEY,
    projectName VARCHAR(255) NOT NULL,
    clientId INT NOT NULL,
    startDate DATE NOT NULL,
    endDate DATE NOT NULL,
	isActive boolean NOT NULL,
    CONSTRAINT fk_Client FOREIGN KEY(clientId) REFERENCES Client(clientId)
);

INSERT INTO Project (projectName, clientId, startDate, endDate, isActive)
VALUES (
	'project 1',
	1,
	'2024-10-01',
	'2024-10-31',
	true
);

-- CREATE TYPE UserType AS ENUM ('Admin', 'Employee');

CREATE TABLE "User" (
	userId SERIAL PRIMARY KEY,
    userType UserType NOT NULL,
    firstName VARCHAR(50) NOT NULL,
    lastName VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL,
    "password" VARCHAR(100) NOT NULL,
    clientId INT,
    projectId INT,
    isActive BOOLEAN NOT NULL,
    CONSTRAINT fk_Client FOREIGN KEY(clientId) REFERENCES Client(clientId),
    CONSTRAINT fk_Project FOREIGN KEY(projectId) REFERENCES Project(projectId)
);

INSERT INTO "User" (userType, firstName, lastName, email, "password", clientId, projectId, isActive)
VALUES (
	'Employee'::UserType,
	'Jenan',
	'Meri',
	'jenanmeri@gmail.com',
	'test123',
	null,
	null,
	false
);

CREATE TABLE TimesheetEntry (
	timesheetEntryId SERIAL PRIMARY KEY,
    userId INT NOT NULL,
    projectId INT NOT NULL,
    "date" DATE NOT NULL,
    hours INT NOT NULL,
    isHoliday BOOLEAN NOT NULL,
    CONSTRAINT fk_Project FOREIGN KEY(projectId) REFERENCES Project(projectId)
);

INSERT INTO TimesheetEntry (userId, projectId, "date", hours, isHoliday)
VALUES (
	1,
	1,
	'2024-10-30',
	8,
	false
);

CREATE TABLE Holiday (
	holidayId SERIAL PRIMARY KEY,
    holidayName VARCHAR(50) NOT NULL,
    holidayMonth INT NOT NULL,
	holidayDay INT NOT NULL
);

INSERT INTO Holiday (holidayName, holidayMonth, holidayDay)
VALUES (
	'New Years',
	1,
	1
);

INSERT INTO Holiday (holidayName, holidayMonth, holidayDay)
VALUES (
	'Presidents Day',
	2,
	0
);

INSERT INTO Holiday (holidayName, holidayMonth, holidayDay)
VALUES (
	'Memorial Day',
	5,
	0
);

INSERT INTO Holiday (holidayName, holidayMonth, holidayDay)
VALUES (
	'Independence Day',
	7,
	4
);

INSERT INTO Holiday (holidayName, holidayMonth, holidayDay)
VALUES (
	'Labor Day',
	9,
	0
);

INSERT INTO Holiday (holidayName, holidayMonth, holidayDay)
VALUES (
	'Veterans Day',
	11,
	11
);

INSERT INTO Holiday (holidayName, holidayMonth, holidayDay)
VALUES (
	'Thanksgiving',
	11,
	0
);

INSERT INTO Holiday (holidayName, holidayMonth, holidayDay)
VALUES (
	'Christmas',
	12,
	25
);
