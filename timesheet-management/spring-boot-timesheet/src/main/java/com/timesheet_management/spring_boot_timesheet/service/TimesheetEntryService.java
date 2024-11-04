package com.timesheet_management.spring_boot_timesheet.service;

import com.timesheet_management.spring_boot_timesheet.model.TimesheetEntry;
import com.timesheet_management.spring_boot_timesheet.repository.TimesheetEntryRepository;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Calendar;

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
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        System.out.println("Month: " + month + " Day: " + day);
        return timesheetEntryRepository.isHoliday(month, day);
    }
}
