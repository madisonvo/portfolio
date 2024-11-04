package com.timesheet_management.spring_boot_timesheet.service;

import com.timesheet_management.spring_boot_timesheet.model.Holiday;
import com.timesheet_management.spring_boot_timesheet.repository.HolidayRepository;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.temporal.TemporalAdjusters;
import java.util.Optional;

@Service
public class HolidayService {

    private HolidayRepository holidayRepository;

    public HolidayService(HolidayRepository holidayRepository) {
        this.holidayRepository = holidayRepository;
    }

    private LocalDate getNthDateOfMonth(DayOfWeek dayOfWeek, int n, Month month) {
        int year = Year.now().getValue();
        LocalDate date = LocalDate.of(year, month, 1);
        if (n > 0) {
            date = date.with(TemporalAdjusters.dayOfWeekInMonth(n, dayOfWeek));
        } else {
            date = date.with(TemporalAdjusters.lastInMonth(dayOfWeek));
        }

        return date;
    }

    public void setPresidentsDay(DayOfWeek dayOfWeek, int n, Month month) {
        Optional<Holiday> holiday = holidayRepository.findById(2);
        if (holiday.isPresent()) {
            Holiday h = holiday.get();
            h.setHolidayDay(getNthDateOfMonth(dayOfWeek, n, month).getDayOfMonth());
        } else {
            System.out.println("Holiday not found!");
        }
    }

    public void setMemorialDay(DayOfWeek dayOfWeek, int n, Month month) {
        Optional<Holiday> holiday = holidayRepository.findById(3);
        if (holiday.isPresent()) {
            Holiday h = holiday.get();
            h.setHolidayDay(getNthDateOfMonth(dayOfWeek, n, month).getDayOfMonth());
        } else {
            System.out.println("Holiday not found!");
        }
    }

    public void setLaborDay(DayOfWeek dayOfWeek, int n, Month month) {
        Optional<Holiday> holiday = holidayRepository.findById(5);
        if (holiday.isPresent()) {
            Holiday h = holiday.get();
            h.setHolidayDay(getNthDateOfMonth(dayOfWeek, n, month).getDayOfMonth());
        } else {
            System.out.println("Holiday not found!");
        }
    }

    public void setThanksgivingDay(DayOfWeek dayOfWeek, int n, Month month) {
        Optional<Holiday> holiday = holidayRepository.findById(7);
        if (holiday.isPresent()) {
            Holiday h = holiday.get();
            h.setHolidayDay(getNthDateOfMonth(dayOfWeek, n, month).getDayOfMonth());
        } else {
            System.out.println("Holiday not found!");
        }
    }
}
