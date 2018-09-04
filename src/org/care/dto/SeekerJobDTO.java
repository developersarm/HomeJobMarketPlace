package org.care.dto;

import org.care.model.Job;

import java.util.Date;

public class SeekerJobDTO {
    private String title;
    private Job.Status status;
    private Date startDate;
    private Date endDate;

    public SeekerJobDTO(String title, Job.Status status, Date startDate, Date endDate) {
        this.title = title;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Job.Status getStatus() {
        return status;
    }

    public void setStatus(Job.Status status) {
        this.status = status;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
