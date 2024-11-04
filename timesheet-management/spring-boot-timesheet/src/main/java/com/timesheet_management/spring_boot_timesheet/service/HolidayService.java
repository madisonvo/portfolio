package com.timesheet_management.spring_boot_timesheet.service;

import com.timesheet_management.spring_boot_timesheet.repository.HolidayRepository;
import org.springframework.stereotype.Service;

@Service
public class HolidayService {

    private HolidayRepository holidayRepository;

    public HolidayService(HolidayRepository holidayRepository) {
        this.holidayRepository = holidayRepository;
    }

    // TODO: Somehow find a way to get dates for holidays without a set date
    public void getHolidays() {

    }
}
