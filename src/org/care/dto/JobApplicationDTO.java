package org.care.dto;

public class JobApplicationDTO {
    private int userId;
    private int jobId;
    private Double expectedPay;

    public JobApplicationDTO(int userId, int jobId, Double expectedPay) {
        this.userId = userId;
        this.jobId = jobId;
        this.expectedPay = expectedPay;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public Double getExpectedPay() {
        return expectedPay;
    }

    public void setExpectedPay(Double expectedPay) {
        this.expectedPay = expectedPay;
    }
}
