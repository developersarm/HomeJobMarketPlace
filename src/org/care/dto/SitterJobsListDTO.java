package org.care.dto;

import java.sql.Timestamp;

public class SitterJobsListDTO {
    private String title;
    private Timestamp startDate;
    private double experience;

    public SitterJobsListDTO(String title, Timestamp startDate, double experience) {
        this.title = title;
        this.startDate = startDate;
        this.experience = experience;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public double getExperience() {
        return experience;
    }

    public void setExperience(double experience) {
        this.experience = experience;
    }
}
