package com.timesheet_management.spring_boot_timesheet.model;

import lombok.Data;
import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "TimesheetEntry")
@Data
public class TimesheetEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "timesheetEntryId")
    private int timesheetEntryId;

    @Column(name = "userId")
    private int userId;

    @Column(name = "projectId")
    private int projectId;

    @Column(name = "date")
    private Date date;

    @Column(name = "hours")
    private int hours;

    @Column(name = "isHoliday")
    private boolean isHoliday;

    public boolean isHoliday() {
        return isHoliday;
    }

    public void setHoliday(boolean holiday) {
        isHoliday = holiday;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTimesheetEntryId() {
        return timesheetEntryId;
    }

    public void setTimesheetEntryId(int timesheetEntryId) {
        this.timesheetEntryId = timesheetEntryId;
    }
}
