package com.timesheet_management.spring_boot_timesheet.repository;

import com.timesheet_management.spring_boot_timesheet.model.TimesheetEntry;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;

@Repository
public interface TimesheetEntryRepository extends CrudRepository<TimesheetEntry, Integer> {
    @Query(value = "SELECT COUNT(*) = 1 FROM Holiday WHERE holidayDate = :date", nativeQuery = true)
    boolean isHoliday(Date date);
}
