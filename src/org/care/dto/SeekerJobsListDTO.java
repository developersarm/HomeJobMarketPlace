package org.care.dto;

import org.care.model.Job;

import java.sql.Timestamp;

public class SeekerJobsListDTO {
    private String title;
    private Job.Status status;
    private Timestamp startDate;
    private Timestamp endDate;

    public SeekerJobsListDTO(String title, Job.Status status, Timestamp startDate, Timestamp endDate) {
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

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }
}
