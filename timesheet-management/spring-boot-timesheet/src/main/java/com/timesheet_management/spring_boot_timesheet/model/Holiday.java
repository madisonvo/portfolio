package com.timesheet_management.spring_boot_timesheet.model;

import lombok.Data;
import javax.persistence.*;

@Entity
@Table(name = "Holiday")
@Data
public class Holiday {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "holidayId")
    private int holidayInt;

    @Column(name = "holidayName")
    private String holidayName;

    @Column(name = "holidayMonth")
    private int holidayMonth;

    @Column(name = "holidayDay")
    private int holidayDay;

    public int getHolidayInt() {
        return holidayInt;
    }

    public void setHolidayInt(int holidayInt) {
        this.holidayInt = holidayInt;
    }

    public String getHolidayName() {
        return holidayName;
    }

    public void setHolidayName(String holidayName) {
        this.holidayName = holidayName;
    }

    public int getHolidayMonth() {
        return holidayMonth;
    }

    public void setHolidayMonth(int holidayMonth) {
        this.holidayMonth = holidayMonth;
    }

    public int getHolidayDay() {
        return holidayDay;
    }

    public void setHolidayDay(int holidayDay) {
        this.holidayDay = holidayDay;
    }
}
