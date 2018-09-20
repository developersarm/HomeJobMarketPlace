package org.care.service;

import org.care.context.MyApplicationContext;
import org.care.dao.JobApplicationDAO;
import org.care.dao.JobDAO;
import org.care.dao.SitterDAO;
import org.care.dto.*;
import org.care.form.ProfileForm;
import org.care.form.RegistrationForm;
import org.care.model.Job;
import org.care.model.JobApplication;
import org.care.model.Member;
import org.care.model.Sitter;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SitterService {

    public static Integer register(RegistrationForm sitterForm) {
        SitterDAO sitterDAO = MyApplicationContext.getFactory(SitterDAO.class);
        String firstName = sitterForm.getFirstName();
        String lastName = sitterForm.getLastName();
        String phoneNo = sitterForm.getPhoneNo();
        String emailId = sitterForm.getEmailId();
        String password = sitterForm.getPassword();
        Member.MemberType memberType = Member.MemberType.valueOf(sitterForm.getType());
        String address = sitterForm.getAddress();
        int pincode = Integer.parseInt(sitterForm.getPincode());
        int experience = Integer.parseInt(sitterForm.getExperience());
        Sitter sitter = new Sitter(firstName, lastName, phoneNo, emailId, password, memberType, address, pincode, experience);
        return sitterDAO.create(sitter);
    }

    public static SitterProfileDTO getProfile(int userId) {
        SitterDAO sitterDAO = MyApplicationContext.getFactory(SitterDAO.class);
        Sitter sitter = sitterDAO.get(userId);
        String firstName = sitter.getFirstName();
        String lastName = sitter.getLastName();
        String phoneNo = sitter.getPhoneNo();
        String emailId = sitter.getEmailId();
        String address = sitter.getAddress();
        String pincode = String.valueOf(sitter.getPincode());
        String experience = String.valueOf(sitter.getExperience());
        return new SitterProfileDTO(firstName, lastName, phoneNo, emailId, address, pincode, experience);
    }

    public static void updateProfile(int userId, ProfileForm profileForm) {
        SitterDAO sitterDAO = MyApplicationContext.getFactory(SitterDAO.class);
        Sitter sitter = sitterDAO.get(userId);
        sitter.setFirstName(profileForm.getFirstName());
        sitter.setLastName(profileForm.getLastName());
        sitter.setPhoneNo(profileForm.getPhoneNo());
        sitter.setEmailId(profileForm.getEmailId());
        sitter.setAddress(profileForm.getAddress());
        sitter.setPincode(Integer.parseInt(profileForm.getPincode()));
        sitter.setExperience(Integer.parseInt(profileForm.getExperience()));

        sitterDAO.update(sitter);
    }

    public static List<SitterNAJobDTO> getNAJobsList(int userId) {
        JobApplicationDAO jobApplicationDAO = MyApplicationContext.getFactory(JobApplicationDAO.class);
        List<Job> sitterNAJobsList = jobApplicationDAO.getSitterNAJobsList(userId);
        List<SitterNAJobDTO> sitterNAJobDTOS = new LinkedList<>();

        for (Job tempJob :
                sitterNAJobsList) {
            int jobId = tempJob.getJobId();
            String title = tempJob.getTitle();
            Date startDate = tempJob.getStartDate();
            Double payPerHour = tempJob.getPayPerHour();
            sitterNAJobDTOS.add(new SitterNAJobDTO(jobId, title, payPerHour, startDate));
        }
        return sitterNAJobDTOS;
    }

    public static boolean applyJob(JobApplicationForm jobApplicationForm) {
        JobApplicationDAO jobApplicationDAO = MyApplicationContext.getFactory(JobApplicationDAO.class);

        int userId = jobApplicationForm.getUserId();
        SitterDAO sitterDAO = MyApplicationContext.getFactory(SitterDAO.class);
        Sitter sitter = sitterDAO.get(userId);

        int jobId = Integer.parseInt(jobApplicationForm.getJobId());
        JobDAO jobDAO = MyApplicationContext.getFactory(JobDAO.class);
        Job job = jobDAO.get(jobId);

        double expectedPay = Double.parseDouble(jobApplicationForm.getExpectedPay());
        JobApplication jobApplication = new JobApplication(-1, job, sitter, expectedPay);
        jobApplicationDAO.create(jobApplication);

        return jobApplication.getJobAppId() > 0;
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
        JobApplication jobApplication = jobApplicationDAO.get(jobAppId);
        return jobApplication.getJob().getTitle();
    }

    public static boolean deleteJobApplication(int jobAppId) {
        boolean isDeleted;
        JobApplicationDAO jobApplicationDAO = MyApplicationContext.getFactory(JobApplicationDAO.class);
        isDeleted = jobApplicationDAO.delete(jobAppId);
        return isDeleted;
    }

    public static boolean isJobInNAJobsList(int jobId, int userId) {
        List<SitterNAJobDTO> sitterNAJobDTOList = getNAJobsList(userId);
        for (SitterNAJobDTO tempJob :
                sitterNAJobDTOList) {
            if (tempJob.getJobId() == jobId) {
                return true;
            }
        }
        return false;
    }

    public static int getUserIdforJobAppId(int jobAppId) {
        JobApplicationDAO jobApplicationDAO = MyApplicationContext.getFactory(JobApplicationDAO.class);
        JobApplication jobApplication = jobApplicationDAO.get(jobAppId);
        return jobApplication.getSitter().getMemberId();
    }

    public static double getJobAppExpPay(int jobAppId) {
        JobApplicationDAO jobApplicationDAO = MyApplicationContext.getFactory(JobApplicationDAO.class);
        JobApplication jobApplication = jobApplicationDAO.get(jobAppId);
        return jobApplication.getExpectedPay();
    }
}
