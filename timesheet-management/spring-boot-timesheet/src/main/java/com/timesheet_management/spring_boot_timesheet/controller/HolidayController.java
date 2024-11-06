package com.timesheet_management.spring_boot_timesheet.controller;

import com.timesheet_management.spring_boot_timesheet.service.HolidayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HolidayController {
    @Autowired
    private HolidayService holidayService;

    // FOR TESTING PURPOSES
    @GetMapping(value = "/holidays")
    public void updateHolidays() {
        holidayService.executeUpdateHolidays();
    }
}
