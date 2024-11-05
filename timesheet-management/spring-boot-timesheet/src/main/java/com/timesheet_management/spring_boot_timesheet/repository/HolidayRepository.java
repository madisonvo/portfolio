package com.timesheet_management.spring_boot_timesheet.repository;

import com.timesheet_management.spring_boot_timesheet.model.Holiday;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HolidayRepository extends CrudRepository<Holiday, Integer> {
    @Query(value = "SELECT update_annual_holidays()", nativeQuery = true)
    void updateAnnualHolidays();
}
