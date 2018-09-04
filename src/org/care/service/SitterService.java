package org.care.service;

import org.care.context.MyApplicationContext;
import org.care.dao.JobApplicationDAO;
import org.care.dao.JobDAO;
import org.care.dao.SitterDAO;
import org.care.dto.*;
import org.care.model.Job;
import org.care.model.JobApplication;
import org.care.model.Sitter;

import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SitterService {

    public static void register(Sitter sitter) {
        MyApplicationContext.getFactory(SitterDAO.class).create(sitter);
    }

    public static List<SitterAppliedJobDTO> getAppliedJobsList(int userId) {
        JobApplicationDAO jobApplicationDAO = MyApplicationContext.getFactory(JobApplicationDAO.class);
        List<Map<String, Object>> sitterJobsList = jobApplicationDAO.getSitterJobsList(userId);
        List<SitterAppliedJobDTO> sitterAppliedJobDTO = new LinkedList<>();

        for (Map<String, Object> tempMap :
                sitterJobsList) {
            String title = (String) tempMap.get("title");
            Timestamp startDate = (Timestamp) tempMap.get("startDate");
            Double expectedPay = (Double) tempMap.get("expectedPay");
            sitterAppliedJobDTO.add(new SitterAppliedJobDTO(title, startDate, expectedPay));
        }
        return sitterAppliedJobDTO;
    }

    public static SitterProfileDTO getProfile(int userId) {
        SitterDAO sitterDAO = MyApplicationContext.getFactory(SitterDAO.class);
        Sitter sitter = sitterDAO.get(userId);
        String firstName = sitter.getFirstName();
        String lastName = sitter.getLastName();
        String phoneNo = sitter.getPhoneNo();
        String emailId = sitter.getEmailId();
        String address = sitter.getAddress();
        int pincode = sitter.getPincode();
        int experience = sitter.getExperience();
        return new SitterProfileDTO(firstName, lastName, phoneNo, emailId, address, pincode, experience);
    }

    public static void updateProfile(int userId, SitterProfileDTO sitterProfileDTO) {
        SitterDAO sitterDAO = MyApplicationContext.getFactory(SitterDAO.class);
        Sitter sitter = sitterDAO.get(userId);
        sitter.setFirstName(sitterProfileDTO.getFirstName());
        sitter.setLastName(sitterProfileDTO.getLastName());
        sitter.setPhoneNo(sitterProfileDTO.getPhoneNo());
        sitter.setEmailId(sitterProfileDTO.getEmailId());
        sitter.setAddress(sitterProfileDTO.getAddress());
        sitter.setPincode(sitterProfileDTO.getPincode());
        sitter.setExperience(sitterProfileDTO.getExperience());

        sitterDAO.update(sitter);
    }

    public static List<SitterNAJobDTO> getNAJobsList(int userId) {
        JobApplicationDAO jobApplicationDAO = MyApplicationContext.getFactory(JobApplicationDAO.class);
        List<Map<String, Object>> sitterNAJobsList = jobApplicationDAO.getSitterNAJobsList(userId);
        List<SitterNAJobDTO> sitterNAJobDTOS = new LinkedList<>();

        for (Map<String, Object> tempMap :
                sitterNAJobsList) {
            int jobId = (int) tempMap.get("jobId");
            String title = (String) tempMap.get("title");
            Date startDate = (Timestamp) tempMap.get("startDate");
            Double payPerHour = (Double) tempMap.get("payPerHour");
            sitterNAJobDTOS.add(new SitterNAJobDTO(jobId, title, payPerHour, startDate));
        }
        return sitterNAJobDTOS;
    }

    public static boolean applyJob(JobApplicationDTO jobApplicationDTO) {
        JobApplicationDAO jobApplicationDAO = MyApplicationContext.getFactory(JobApplicationDAO.class);
        int userId = jobApplicationDTO.getUserId();
        int jobId = jobApplicationDTO.getJobId();
        double expectedPay = jobApplicationDTO.getExpectedPay();
        JobApplication jobApplication = new JobApplication(-1, jobId, userId, expectedPay);
        jobApplicationDAO.create(jobApplication);

        return jobApplication.getId()>0;
    }

    public static String getJobTitle(int jobId) {
        JobDAO jobDAO = MyApplicationContext.getFactory(JobDAO.class);
        Job job = jobDAO.get(jobId);
        return job.getTitle();
    }

    public static List<SitterJobApplicationDTO> getJobApplications(int userId) {
        JobApplicationDAO jobApplicationDAO = MyApplicationContext.getFactory(JobApplicationDAO.class);
        List<Map<String, Object>> jobApplications = jobApplicationDAO.getAllByUserId(userId);
        List<SitterJobApplicationDTO> sitterJobApplicationDTOS = new LinkedList<>();
        for (Map<String, Object> tempApp :
                jobApplications) {
            int id = (int) tempApp.get("id");
            String title = (String) tempApp.get("title");
            double expectedPay = (double) tempApp.get("expectedPay");
            double payPerHour = (double) tempApp.get("payPerHour");
            JobApplication.Status status = (JobApplication.Status) tempApp.get("status");
            sitterJobApplicationDTOS.add(new SitterJobApplicationDTO(id, title, expectedPay, payPerHour, status));
        }
        return sitterJobApplicationDTOS;
    }

    public static boolean updateJobApplication(int jobAppId, double expectedPay) {
        JobApplicationDAO jobApplicationDAO = MyApplicationContext.getFactory(JobApplicationDAO.class);
        return jobApplicationDAO.update(jobAppId, expectedPay);
    }

    public static String getJobApplicationTitle(int jobAppId) {
        JobApplicationDAO jobApplicationDAO = MyApplicationContext.getFactory(JobApplicationDAO.class);
        JobApplication jobApplication = jobApplicationDAO.get((Integer) jobAppId);
        JobDAO jobDAO = MyApplicationContext.getFactory(JobDAO.class);
        Job job = jobDAO.get(jobApplication.getJobId());
        return job.getTitle();
    }

    public static boolean deleteJobApplication(int jobAppId) {
        boolean isDeleted = false;
        JobApplicationDAO jobApplicationDAO = MyApplicationContext.getFactory(JobApplicationDAO.class);
        isDeleted = jobApplicationDAO.delete(jobAppId);
        return isDeleted;
    }
}
