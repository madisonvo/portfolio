Nov 4, 2024
  * Add entities/tables, repositories, services, controllers
    - User, Client, Project, TimesheetEntry, Holiday
  * Implement setting holiday dates for current year for holidays with no set date (e.g. Thanksgiving on fourth Thursday of November)

Nov 5, 2024
 * Add dependencies/properties to allow for pub/sub - pom.xml, application.properties
 * Create new sql script to update holiday dates - update-holidays.sql
 * Update Holiday entity by removing int month and int day and replacing with Date holidayDate - Holiday.java
 * Implement functionality to execute sql query every January 1st to update holiday dates for every year
 * Initialize spring boot application for Employee Management

Nov 6, 2024
 * Edit TimesheetEntry service and repository to accommodate to changes made to Holiday table

Nov 8, 2024
 * Implement models, repositories, services, and controllers for each entity in spring_boot_employee
   - User, Client, Project, Request
 * Implement simple pub/sub services to have spring_boot_timesheet and spring_boot_employee communicate with each other
 * Implement being able to add skills for a certain user
