package org.care.service;

import org.care.context.MyApplicationContext;
import org.care.dao.JobApplicationDAO;
import org.care.dao.JobDAO;
import org.care.dao.SeekerDAO;
import org.care.dto.SeekerJobApplicationDTO;
import org.care.dto.SeekerJobDTO;
import org.care.dto.SeekerProfileDTO;
import org.care.form.JobForm;
import org.care.form.ProfileForm;
import org.care.form.RegistrationForm;
import org.care.model.Job;
import org.care.model.JobApplication;
import org.care.model.Seeker;
import org.care.utils.CommonUtil;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SeekerService {

    public static Integer register(RegistrationForm seekerForm) {
        SeekerDAO seekerDAO = MyApplicationContext.getFactory(SeekerDAO.class);
        String firstName = seekerForm.getFirstName();
        String lastName = seekerForm.getLastName();
        String phoneNo = seekerForm.getPhoneNo();
        String emailId = seekerForm.getEmailId();
        String password = CommonUtil.getHashedPassword(seekerForm.getPassword());
        String address = seekerForm.getAddress();
        int pincode = Integer.parseInt(seekerForm.getPincode());
        int totalChildren = Integer.parseInt(seekerForm.getTotalChildren());
        String spouseName = seekerForm.getSpouseName();
        Seeker seeker = new Seeker(firstName, lastName, phoneNo, emailId, password, address, pincode, totalChildren, spouseName);
        return seekerDAO.create(seeker);
    }

    public static boolean postJob(JobForm jobForm) {
        try {
            JobDAO jobDAO = MyApplicationContext.getFactory(JobDAO.class);
            String title = jobForm.getTitle();

            int postedBy = jobForm.getPostedBy();
            SeekerDAO seekerDAO = MyApplicationContext.getFactory(SeekerDAO.class);
            Seeker seeker = seekerDAO.get(postedBy);

            Date sDate = new SimpleDateFormat("yyyy-MM-dd").parse(jobForm.getStartDate());
            Timestamp startDate = new Timestamp(sDate.getTime());
            Date eDate = new SimpleDateFormat("yyyy-MM-dd").parse(jobForm.getEndDate());
            Timestamp endDate = new Timestamp(eDate.getTime());
            Double payPerHour = Double.parseDouble(jobForm.getPayPerHour());
            Job job = new Job(title, seeker, startDate, endDate, payPerHour);
            jobDAO.create(job);
            return true;
        } catch (ParseException e) {
            Logger logger = Logger.getLogger(SeekerService.class.getName());
            logger.log(Level.SEVERE, "Cannot convert html date to java date format. ");
            return false;
        }
    }

    public static SeekerProfileDTO getProfile(int userId) {
        SeekerDAO seekerDAO = MyApplicationContext.getFactory(SeekerDAO.class);
        Seeker seeker = seekerDAO.get(userId);

        String firstName = seeker.getFirstName();
        String lastName = seeker.getLastName();
        String phoneNo = seeker.getPhoneNo();
        String emailId = seeker.getEmailId();
        String address = seeker.getAddress();
        String pincode = String.valueOf(seeker.getPincode());
        String totalChildren = String.valueOf(seeker.getTotalChildren());
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
            String id = String.valueOf(tempJob.getJobId());
            String title = tempJob.getTitle();
            Job.Status status = tempJob.getStatus();
            String startDate = String.valueOf(tempJob.getStartDate());
            String endDate = String.valueOf(tempJob.getEndDate());
            seekerJobDTOS.add(new SeekerJobDTO(id, title, status, startDate, endDate));
        }
        return seekerJobDTOS;
    }

    public static void updateProfile(int userId, ProfileForm profileForm) {
        SeekerDAO seekerDAO = MyApplicationContext.getFactory(SeekerDAO.class);
        Seeker seeker = seekerDAO.get(userId);
        seeker.setFirstName(profileForm.getFirstName());
        seeker.setLastName(profileForm.getLastName());
        seeker.setPhoneNo(profileForm.getPhoneNo());
        seeker.setEmailId(profileForm.getEmailId());
        seeker.setAddress(profileForm.getAddress());
        seeker.setPincode(Integer.parseInt(profileForm.getPincode()));
        seeker.setTotalChildren(Integer.parseInt(profileForm.getTotalChildren()));
        seeker.setSpouseName(profileForm.getSpouseName());

        seekerDAO.update(seeker);
    }

    public static SeekerJobDTO getJob(int jobId) {
        JobDAO jobDAO = MyApplicationContext.getFactory(JobDAO.class);
        Job job = jobDAO.get(jobId);
        String id = String.valueOf(job.getJobId());
        String title = job.getTitle();
        Job.Status status = job.getStatus();
        String startDate = new SimpleDateFormat("yyyy-MM-dd").format(job.getStartDate());
        String endDate = new SimpleDateFormat("yyyy-MM-dd").format(job.getEndDate());
        String payPerHour = String.valueOf(job.getPayPerHour());
        return new SeekerJobDTO(id, title, status, startDate, endDate, payPerHour);
    }

    public static boolean updateJob(int userId, JobForm jobForm) {
        try {
            JobDAO jobDAO = MyApplicationContext.getFactory(JobDAO.class);
            int id = Integer.parseInt(jobForm.getId());
            Job job = jobDAO.get(id);

            SeekerDAO seekerDAO = MyApplicationContext.getFactory(SeekerDAO.class);
            job.setSeeker(seekerDAO.get(userId));

            job.setTitle(jobForm.getTitle());

            Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(jobForm.getStartDate());
            job.setStartDate(new Timestamp(startDate.getTime()));

            Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(jobForm.getEndDate());
            job.setEndDate(new Timestamp(endDate.getTime()));

            job.setPayPerHour(Double.parseDouble(jobForm.getPayPerHour()));

            jobDAO.update(job);
            return true;

        } catch (ParseException e) {
            Logger logger = Logger.getLogger(SeekerService.class.getName());
            logger.log(Level.SEVERE, "Cannot convert html date to java date format. ");
            return false;
        }
    }

    public static boolean deleteJob(int jobId) {
        JobApplicationDAO jobApplicationDAO = MyApplicationContext.getFactory(JobApplicationDAO.class);
        int totalDeletedApplicaitons = jobApplicationDAO.deleteByJobId(jobId);

        boolean isJobDeleted = false;
        if (totalDeletedApplicaitons >= 0) {
            JobDAO jobDAO = MyApplicationContext.getFactory(JobDAO.class);
            isJobDeleted = jobDAO.delete(jobId);
        }

        return isJobDeleted;
    }

    public static List<SeekerJobApplicationDTO> getJobApplicationsByJobId(int jobId) {
        JobApplicationDAO jobApplicationDAO = MyApplicationContext.getFactory(JobApplicationDAO.class);
        List<Map<String, Object>> jobApplicaitons = jobApplicationDAO.getAllByJobId(jobId);
        List<SeekerJobApplicationDTO> seekerJobApplicationDTOS = new LinkedList<>();
        for (Map<String, Object> tempApp :
                jobApplicaitons) {
            int id = (int) tempApp.get("id");
            String firstName = (String) tempApp.get("firstName");
            JobApplication.Status status = (JobApplication.Status) tempApp.get("status");
            double expectedPay = (double) tempApp.get("expectedPay");
            seekerJobApplicationDTOS.add(new SeekerJobApplicationDTO(id, firstName, status, expectedPay));
        }
        return seekerJobApplicationDTOS;
    }

    public static String getJobTitle(int jobId) {
        JobDAO jobDAO = MyApplicationContext.getFactory(JobDAO.class);
        Job job = jobDAO.get(jobId);
        return job.getTitle();
    }

    public static int getUserIdforJobId(int jobId) {
        JobDAO jobDAO = MyApplicationContext.getFactory(JobDAO.class);
        Job job = jobDAO.get(jobId);
        return job.getSeeker().getMemberId();
    }
}
