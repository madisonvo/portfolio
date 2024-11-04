package com.timesheet_management.spring_boot_timesheet.controller;

import com.timesheet_management.spring_boot_timesheet.service.HolidayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;

@RestController
public class HolidayController {
    @Autowired
    private HolidayService holidayService;

    @PostMapping(value = "/holiday")
    public void addHolidays() {
        holidayService.setPresidentsDay(DayOfWeek.MONDAY, 3, Month.FEBRUARY);
        holidayService.setMemorialDay(DayOfWeek.MONDAY, -1, Month.MAY);
        holidayService.setLaborDay(DayOfWeek.MONDAY, 1, Month.SEPTEMBER);
        holidayService.setThanksgivingDay(DayOfWeek.THURSDAY, 4, Month.NOVEMBER);
    }
}
