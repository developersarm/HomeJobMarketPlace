package org.care.dto;

import org.care.model.Job;

import java.util.Date;

public class SeekerJobDTO {
    private int id;
    private String title;
    private Job.Status status;
    private Date startDate;
    private Date endDate;
    private double payPerHour;

    public SeekerJobDTO(int id, String title, Job.Status status, Date startDate, Date endDate) {
        this.id = id;
        this.title = title;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public SeekerJobDTO(String title, Date startDate, Date endDate, double payPerHour) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.payPerHour = payPerHour;
    }

    public SeekerJobDTO(String title, Job.Status status, Date startDate, Date endDate) {
        this.title = title;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public SeekerJobDTO(int id, String title, Date startDate, Date endDate, double payPerHour) {
        this.id = id;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.payPerHour = payPerHour;
    }

    public SeekerJobDTO(int id, String title, Job.Status status, Date startDate, Date endDate, double payPerHour) {
        this.id = id;
        this.title = title;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
        this.payPerHour = payPerHour;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public double getPayPerHour() {
        return payPerHour;
    }

    public void setPayPerHour(double payPerHour) {
        this.payPerHour = payPerHour;
    }
}
