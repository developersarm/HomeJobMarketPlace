package org.care.service;

import org.care.context.MyApplicationContext;
import org.care.dao.JobApplicationDAO;
import org.care.dao.JobDAO;
import org.care.dao.SeekerDAO;
import org.care.model.Job;
import org.care.model.JobApplication;
import org.care.model.Seeker;
import org.care.model.Sitter;

import java.util.List;

public class SeekerService {

    public static void register(Seeker seeker) {

        MyApplicationContext.getFactory(SeekerDAO.class).create(seeker);
    }

    public static int validateUser(String email, String password) {
        /*
        TODO hash the password
         */
        return MyApplicationContext.getFactory(SeekerDAO.class).get(email, password);
    }

    public static List<JobApplication> getJobApplications(int userId, int noOfResults) {

        JobApplicationDAO jobApplicationDAO = MyApplicationContext.getFactory(JobApplicationDAO.class);
        return jobApplicationDAO.get(userId, noOfResults);
    }

    public static List<JobApplication> getJobApplications(int userId) {
        JobApplicationDAO jobApplicationDAO = MyApplicationContext.getFactory(JobApplicationDAO.class);
        return jobApplicationDAO.get(userId);
    }

    public static List<JobApplication> getJobApplications(int userId, JobApplication.Status status) {
        JobApplicationDAO jobApplicationDAO = MyApplicationContext.getFactory(JobApplicationDAO.class);
        return jobApplicationDAO.get(userId, status);
    }

    public static void postJob(Job job) {
        JobDAO jobDAO = MyApplicationContext.getFactory(JobDAO.class);
        jobDAO.create(job);
    }

    public void selectApplication(Sitter s) {

    }
}
