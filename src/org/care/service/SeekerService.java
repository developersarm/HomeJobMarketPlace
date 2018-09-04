package org.care.service;

import org.care.context.MyApplicationContext;
import org.care.dao.JobApplicationDAO;
import org.care.dao.JobDAO;
import org.care.dao.SeekerDAO;
import org.care.dto.SeekerJobDTO;
import org.care.dto.SeekerProfileDTO;
import org.care.model.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class SeekerService {

    public static void register(Seeker seeker) {

        MyApplicationContext.getFactory(SeekerDAO.class).create(seeker);
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

    public static List<SeekerJobDTO> getJobsList(int userId) {
        List<SeekerJobDTO> seekerJobDTOS = new LinkedList<>();
        List<Job> jobsList;
        JobDAO jobDAO = MyApplicationContext.getFactory(JobDAO.class);
        jobsList = jobDAO.getJobsPostedBy(userId);
        for (Job tempJob :
                jobsList) {
            int id = tempJob.getId();
            String title = tempJob.getTitle();
            Job.Status status = tempJob.getStatus();
            Date startDate = tempJob.getStartDate();
            Date endDate = tempJob.getEndDate();
            seekerJobDTOS.add(new SeekerJobDTO(id, title, status, startDate, endDate));
        }
        return seekerJobDTOS;
    }

    public static void updateProfile(int userId, SeekerProfileDTO seekerProfileDTO) {
        SeekerDAO seekerDAO = MyApplicationContext.getFactory(SeekerDAO.class);
        Seeker seeker = seekerDAO.get(userId);
        seeker.setFirstName(seekerProfileDTO.getFirstName());
        seeker.setLastName(seekerProfileDTO.getLastName());
        seeker.setPhoneNo(seekerProfileDTO.getPhoneNo());
        seeker.setEmailId(seekerProfileDTO.getEmailId());
        seeker.setAddress(seekerProfileDTO.getAddress());
        seeker.setPincode(seekerProfileDTO.getPincode());
        seeker.setTotalChildren(seekerProfileDTO.getTotalChildren());
        seeker.setSpouseName(seekerProfileDTO.getSpouseName());

        seekerDAO.update(seeker);
    }

    public static SeekerJobDTO getJob(int jobId) {
        JobDAO jobDAO = MyApplicationContext.getFactory(JobDAO.class);
        Job job = jobDAO.get(jobId);
        int id = job.getId();
        String title = job.getTitle();
        Job.Status status = job.getStatus();
        Date startDate = job.getStartDate();
        Date endDate = job.getEndDate();
        double payPerHour = job.getPayPerHour();
        return new SeekerJobDTO(id, title, status, startDate, endDate, payPerHour);
    }

    public static boolean updateJob(int userId, SeekerJobDTO jobDTO) {
        JobDAO jobDAO = MyApplicationContext.getFactory(JobDAO.class);
        int id = jobDTO.getId();
        String title = jobDTO.getTitle();
        Date startDate = jobDTO.getStartDate();
        Timestamp startDateTS = new Timestamp(startDate.getTime());
        Date endDate = jobDTO.getEndDate();
        Timestamp endDateTS = new Timestamp(endDate.getTime());
        double payPerHour = jobDTO.getPayPerHour();
        jobDAO.update(new Job(id, title, userId, startDateTS, endDateTS, payPerHour));
        return true;
    }

    public static boolean deleteJob(int jobId) {
        JobDAO jobDAO = MyApplicationContext.getFactory(JobDAO.class);
        return jobDAO.delete(jobId);
    }

    public void selectApplication(Sitter s) {

    }
}
