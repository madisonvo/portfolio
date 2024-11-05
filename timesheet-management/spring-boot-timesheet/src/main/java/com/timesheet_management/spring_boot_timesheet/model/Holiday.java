package com.timesheet_management.spring_boot_timesheet.model;

import lombok.Data;
import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "Holiday")
@Data
public class Holiday {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "holidayId")
    private int holidayId;

    @Column(name = "holidayName")
    private String holidayName;

    @Column(name = "holidayDate")
    private Date holidayDate;

    public int getHolidayInt() {
        return holidayId;
    }

    public void setHolidayInt(int holidayId) {
        this.holidayId = holidayId;
    }

    public String getHolidayName() {
        return holidayName;
    }

    public void setHolidayName(String holidayName) {
        this.holidayName = holidayName;
    }

    public Date getHolidayDate() {
        return holidayDate;
    }

    public void setHolidayDate(Date holidayDate) {
        this.holidayDate = this.holidayDate;
    }
}
