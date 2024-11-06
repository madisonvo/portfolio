package com.timesheet_management.spring_boot_timesheet.service;

import com.timesheet_management.spring_boot_timesheet.model.TimesheetEntry;
import com.timesheet_management.spring_boot_timesheet.repository.TimesheetEntryRepository;
import org.springframework.stereotype.Service;

import java.sql.Date;

@Service
public class TimesheetEntryService {

    private TimesheetEntryRepository timesheetEntryRepository;

    public TimesheetEntryService(TimesheetEntryRepository timesheetEntryRepository) {
        this.timesheetEntryRepository = timesheetEntryRepository;
    }

    public boolean addNewTimesheetEntry(
            int userId,
            int projectId,
            Date date,
            int hours) {
        if (isHoliday(date)) {
            System.out.println("You cannot enter time for a holiday");
            return false;
        } else {
            TimesheetEntry timesheetEntry = new TimesheetEntry();
            timesheetEntry.setUserId(userId);
            timesheetEntry.setProjectId(projectId);
            timesheetEntry.setDate(date);
            timesheetEntry.setHours(hours);
            timesheetEntry.setHoliday(true);

            timesheetEntryRepository.save(timesheetEntry);
            return true;
        }
    }

    private boolean isHoliday(Date date) {
        return timesheetEntryRepository.isHoliday(date);
    }
}
