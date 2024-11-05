DROP TABLE IF EXISTS Holiday;
DROP TABLE IF EXISTS TimesheetEntry;
DROP TABLE IF EXISTS "User";
-- DROP TYPE IF EXISTS UserType;
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
    userType VARCHAR(8) NOT NULL, -- change to UserType enum
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
	'Employee',
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
    holidayName VARCHAR(50) NOT NULL UNIQUE, -- Ensure holidayName is unique for conflict handling
    holdayDate DATE NOT NULL
);

CREATE OR REPLACE FUNCTION update_annual_holidays(p_year INT DEFAULT EXTRACT(YEAR FROM CURRENT_DATE)::INT) RETURNS VOID AS $$
DECLARE
    new_years DATE := make_date(p_year, 1, 1);
    presidents_day DATE;
    memorial_day DATE;
    independence_day DATE := make_date(p_year, 7, 4);
    labor_day DATE;
    juneteenth DATE := make_date(p_year, 6, 19);
    veterans_day DATE := make_date(p_year, 11, 11);
    thanksgiving DATE;
    christmas DATE := make_date(p_year, 12, 25);
BEGIN
    presidents_day := date_trunc('month', make_date(p_year, 2, 1)) + 
                      ((1 - EXTRACT(DOW FROM date_trunc('month', make_date(p_year, 2, 1))))::INT % 7 + 7) * interval '1 day' + interval '2 weeks';

    memorial_day := date_trunc('month', make_date(p_year, 6, 1)) - interval '1 day';
    WHILE EXTRACT(DOW FROM memorial_day) <> 1 LOOP
        memorial_day := memorial_day - interval '1 day';
    END LOOP;

    labor_day := date_trunc('month', make_date(p_year, 9, 1));
    IF EXTRACT(DOW FROM labor_day) <> 1 THEN
        labor_day := labor_day + ((1 - EXTRACT(DOW FROM labor_day))::INT + 7) % 7 * interval '1 day';
    END IF;

    thanksgiving := date_trunc('month', make_date(p_year, 11, 1)) 
                    + ((4 - EXTRACT(DOW FROM make_date(p_year, 11, 1))) + 7) % 7 * interval '1 day'
                    + interval '3 weeks';

    INSERT INTO Holiday (holidayName, holdayDate) VALUES
        ('New Year''s Day', new_years),
        ('Presidents'' Day', presidents_day),
        ('Memorial Day', memorial_day),
        ('Independence Day', independence_day),
        ('Labor Day', labor_day),
        ('Juneteenth', juneteenth),
        ('Veterans Day', veterans_day),
        ('Thanksgiving', thanksgiving),
        ('Christmas', christmas)
    ON CONFLICT (holidayName) 
    DO UPDATE SET holdayDate = EXCLUDED.holdayDate;

END;
$$ LANGUAGE plpgsql;

SELECT update_annual_holidays();
