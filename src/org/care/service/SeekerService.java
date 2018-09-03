package org.care.service;

import org.apache.catalina.core.ApplicationContext;
import org.care.context.MyApplicationContext;
import org.care.dao.JobApplicationDAO;
import org.care.dao.JobDAO;
import org.care.dao.MemberDAO;
import org.care.dao.SeekerDAO;
import org.care.dto.ProfileDTO;
import org.care.dto.SeekerJobsListDTO;
import org.care.dto.SeekerProfileDTO;
import org.care.listeners.ApplicationListener;
import org.care.model.*;

import java.sql.Timestamp;
import java.util.LinkedList;
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

    public static List<SeekerJobsListDTO> getJobsList(int userId) {
        List<SeekerJobsListDTO> seekerJobsListDTOS = new LinkedList<>();
        List<Job> jobsList;
        JobDAO jobDAO = MyApplicationContext.getFactory(JobDAO.class);
        jobsList = jobDAO.getJobsPostedBy(userId);
        for (Job tempJob :
                jobsList) {
            String title = tempJob.getTitle();
            Job.Status status = tempJob.getStatus();
            Timestamp startDate = tempJob.getStartDate();
            Timestamp endDate = tempJob.getEndDate();
            seekerJobsListDTOS.add(new SeekerJobsListDTO(title, status, startDate, endDate));
        }
        return seekerJobsListDTOS;
    }

    public void selectApplication(Sitter s) {

    }
}
