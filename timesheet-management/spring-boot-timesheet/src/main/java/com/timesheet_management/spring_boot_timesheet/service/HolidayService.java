package com.timesheet_management.spring_boot_timesheet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class HolidayService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Scheduled(cron = "0 0 0 1 1 *")
    public void executeUpdateHolidays() {
        String query = "SELECT update_annual_holidays()";
        jdbcTemplate.queryForList(query);
    }
}
