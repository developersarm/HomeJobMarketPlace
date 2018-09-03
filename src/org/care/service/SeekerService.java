package org.care.service;

import org.care.context.MyApplicationContext;
import org.care.dao.JobApplicationDAO;
import org.care.dao.JobDAO;
import org.care.dao.MemberDAO;
import org.care.dao.SeekerDAO;
import org.care.dto.ProfileDTO;
import org.care.dto.SeekerProfileDTO;
import org.care.model.*;

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

    public static SeekerProfileDTO getProfile(int userId) {
        SeekerDAO seekerDAO = MyApplicationContext.getFactory(SeekerDAO.class);
        Seeker seeker = seekerDAO.get(userId);

        String firstName = seeker.getFirstName();
        String lastName = seeker.getLastName();
        String phoneNo = seeker.getPhoneNo();
        String emailId = seeker.getEmailId();
        String address = seeker.getAddress();
        int pincode = seeker.getPincode();
        int totalChildren = seeker.getTotalChildren();
        String spouseName = seeker.getSpouseName();

        return new SeekerProfileDTO(firstName, lastName, phoneNo, emailId, address, pincode, totalChildren, spouseName);
    }

    public void selectApplication(Sitter s) {

    }
}
