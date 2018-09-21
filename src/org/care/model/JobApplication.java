package org.care.model;

public class JobApplication {
    private int jobAppId;
    private double expectedPay;
    private Status status;
    private Job job;
    private Sitter sitter;

    public JobApplication() {
    }

    public JobApplication(int jobAppId, Job job, Sitter sitter, double expectedPay) {
        this.jobAppId = jobAppId;
        this.job = job;
        this.sitter = sitter;
        this.expectedPay = expectedPay;
        this.status = Status.ACTIVE;
    }

    public JobApplication(int jobAppId, Job job, Sitter sitter, double expectedPay, Status status) {
        this.jobAppId = jobAppId;
        this.job = job;
        this.sitter = sitter;
        this.expectedPay = expectedPay;
        this.status = status;
    }

    public JobApplication(Job job, Sitter sitter, double expectedPay) {
        this.expectedPay = expectedPay;
        this.job = job;
        this.sitter = sitter;
        this.status = Status.ACTIVE;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public Sitter getSitter() {
        return sitter;
    }

    public void setSitter(Sitter sitter) {
        this.sitter = sitter;
    }

    public int getJobAppId() {
        return jobAppId;
    }

    public void setJobAppId(int jobAppId) {
        this.jobAppId = jobAppId;
    }

    public double getExpectedPay() {
        return expectedPay;
    }

    public void setExpectedPay(double expectedPay) {
        this.expectedPay = expectedPay;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public enum Status {ACTIVE, INACTIVE}
}
