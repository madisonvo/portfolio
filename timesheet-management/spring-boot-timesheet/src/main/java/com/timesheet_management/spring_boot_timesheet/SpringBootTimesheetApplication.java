package com.timesheet_management.spring_boot_timesheet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class SpringBootTimesheetApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootTimesheetApplication.class, args);
	}

}
