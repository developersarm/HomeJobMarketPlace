package org.care.service;

import org.care.context.MyApplicationContext;
import org.care.dao.JobApplicationDAO;
import org.care.dao.JobDAO;
import org.care.dao.SitterDAO;
import org.care.dto.SitterJobApplicationDTO;
import org.care.dto.SitterNAJobDTO;
import org.care.dto.SitterProfileDTO;
import org.care.form.JobAppForm;
import org.care.form.ProfileForm;
import org.care.form.RegistrationForm;
import org.care.model.Job;
import org.care.model.JobApplication;
import org.care.model.Member;
import org.care.model.Sitter;
import org.care.utils.CommonUtil;
import org.hibernate.Transaction;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class SitterService {

    public static Integer register(RegistrationForm sitterForm) throws ServiceException {
        Transaction tx = MyApplicationContext.getHibSession().beginTransaction();
        try {
            SitterDAO sitterDAO = MyApplicationContext.getFactory(SitterDAO.class);
            String firstName = sitterForm.getFirstName();
            String lastName = sitterForm.getLastName();
            String phoneNo = sitterForm.getPhoneNo();
            String emailId = sitterForm.getEmailId();
            String password = CommonUtil.getHashedPassword(sitterForm.getPassword());
            Member.MemberType memberType = Member.MemberType.valueOf(sitterForm.getType());
            String address = sitterForm.getAddress();
            int pincode = Integer.parseInt(sitterForm.getPincode());
            int experience = Integer.parseInt(sitterForm.getExperience());
            Sitter sitter = new Sitter(firstName, lastName, phoneNo, emailId, password, memberType, address, pincode, experience);
            Integer userId = sitterDAO.create(sitter);

            tx.commit();
            return userId;
        } catch (Exception e) {
            try {
                tx.rollback();
            } catch (RuntimeException e1) {
                Logger.getLogger(SitterService.class.getName()).severe("Coudn't rollback transaction: " + e);
            }
            Logger.getLogger(SitterService.class.getName()).info("Sitter registration failed: " + e);
            throw new ServiceException();
        }
    }

    public static SitterProfileDTO getProfile(int userId) throws ServiceException {
        Transaction tx = MyApplicationContext.getHibSession().beginTransaction();
        try {
            SitterDAO sitterDAO = MyApplicationContext.getFactory(SitterDAO.class);
            Sitter sitter = sitterDAO.get(userId);
            String firstName = sitter.getFirstName();
            String lastName = sitter.getLastName();
            String phoneNo = sitter.getPhoneNo();
            String emailId = sitter.getEmailId();
            String address = sitter.getAddress();
            String pincode = String.valueOf(sitter.getPincode());
            String experience = String.valueOf(sitter.getExperience());

            tx.commit();
            return new SitterProfileDTO(firstName, lastName, phoneNo, emailId, address, pincode, experience);
        } catch (Exception e) {
            try {
                tx.rollback();
            } catch (RuntimeException e1) {
                Logger.getLogger(SitterService.class.getName()).severe("Coudn't rollback transaction: " + e);
            }
            Logger.getLogger(SitterService.class.getName()).info("Sitter fetching failed: " + e);
            throw new ServiceException();
        }
    }

    public static void updateProfile(int userId, ProfileForm profileForm) throws ServiceException {
        Transaction tx = MyApplicationContext.getHibSession().beginTransaction();
        try {
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
            tx.commit();
        } catch (Exception e) {
            try {
                tx.rollback();
            } catch (RuntimeException e1) {
                Logger.getLogger(SitterService.class.getName()).severe("Coudn't rollback transaction: " + e);
            }
            Logger.getLogger(SitterService.class.getName()).info("Profile updation failed: " + e);
            throw new ServiceException();
        }
    }

    public static List<SitterNAJobDTO> getNAJobsList(int userId) throws ServiceException {
        Transaction tx = MyApplicationContext.getHibSession().beginTransaction();
        try {
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
            tx.commit();
            return sitterNAJobDTOS;
        } catch (Exception e) {
            try {
                tx.rollback();
            } catch (RuntimeException e1) {
                Logger.getLogger(SitterService.class.getName()).severe("Coudn't rollback transaction: " + e);
            }
            Logger.getLogger(SitterService.class.getName()).info("Job fetching failed: " + e);
            throw new ServiceException();
        }
    }
    public static void applyJob(JobAppForm jobAppForm) throws ServiceException {
        Transaction tx = MyApplicationContext.getHibSession().beginTransaction();
        try {
            JobApplicationDAO jobApplicationDAO = MyApplicationContext.getFactory(JobApplicationDAO.class);

            int userId = jobAppForm.getUserId();
            SitterDAO sitterDAO = MyApplicationContext.getFactory(SitterDAO.class);
            Sitter sitter = sitterDAO.get(userId);

            int jobId = Integer.parseInt(jobAppForm.getJobId());
            JobDAO jobDAO = MyApplicationContext.getFactory(JobDAO.class);
            Job job = jobDAO.get(jobId);

            double expectedPay = Double.parseDouble(jobAppForm.getExpectedPay());
            JobApplication jobApplication = new JobApplication(job, sitter, expectedPay);
            jobApplicationDAO.create(jobApplication);

            tx.commit();
        } catch (Exception e) {
            try {
                tx.rollback();
            } catch (RuntimeException e1) {
                Logger.getLogger(SitterService.class.getName()).severe("Coudn't rollback transaction: " + e);
            }
            Logger.getLogger(SitterService.class.getName()).info("Job application registration failed: " + e);
            throw new ServiceException();
        }
    }

    public static String getJobTitle(int jobId) throws ServiceException {
        Transaction tx = MyApplicationContext.getHibSession().beginTransaction();
        try {
            JobDAO jobDAO = MyApplicationContext.getFactory(JobDAO.class);
            Job job = jobDAO.get(jobId);

            tx.commit();
            return job.getTitle();
        } catch (Exception e) {
            try {
                tx.rollback();
            } catch (RuntimeException e1) {
                Logger.getLogger(SitterService.class.getName()).severe("Coudn't rollback transaction: " + e);
            }
            Logger.getLogger(SitterService.class.getName()).info("Job fetching failed: " + e);
            throw new ServiceException();
        }
    }

    public static List<SitterJobApplicationDTO> getJobApplications(int userId) throws ServiceException {
        Transaction tx = MyApplicationContext.getHibSession().beginTransaction();
        try {
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
            tx.commit();
            return sitterJobApplicationDTOS;
        } catch (Exception e) {
            try {
                tx.rollback();
            } catch (RuntimeException e1) {
                Logger.getLogger(SitterService.class.getName()).severe("Coudn't rollback transaction: " + e);
            }
            Logger.getLogger(SitterService.class.getName()).info("Job application fetching failed: " + e);
            throw new ServiceException();
        }
    }

    public static void updateJobApplication(int jobAppId, double expectedPay) throws ServiceException {
        Transaction tx = MyApplicationContext.getHibSession().beginTransaction();
        try {
            JobApplicationDAO jobApplicationDAO = MyApplicationContext.getFactory(JobApplicationDAO.class);
            jobApplicationDAO.update(jobAppId, expectedPay);
            tx.commit();
        } catch (Exception e) {
            try {
                tx.rollback();
            } catch (RuntimeException e1) {
                Logger.getLogger(SitterService.class.getName()).severe("Coudn't rollback transaction: " + e);
            }
            Logger.getLogger(SitterService.class.getName()).info("Job application updation failed: " + e);
            throw new ServiceException();
        }
    }

    public static void deleteJobApplication(int jobAppId) throws ServiceException {
        Transaction tx = MyApplicationContext.getHibSession().beginTransaction();
        try {
            JobApplicationDAO jobApplicationDAO = MyApplicationContext.getFactory(JobApplicationDAO.class);
            jobApplicationDAO.delete(jobAppId);
            tx.commit();
        } catch (Exception e) {
            try {
                tx.rollback();
            } catch (RuntimeException e1) {
                Logger.getLogger(SitterService.class.getName()).severe("Coudn't rollback transaction: " + e);
            }
            Logger.getLogger(SitterService.class.getName()).info("Job application deletion failed: " + e);
            throw new ServiceException();
        }
    }

    public static boolean isJobInNAJobsList(int jobId, int userId) throws ServiceException {
        try {
            boolean isPresent = false;
            List<SitterNAJobDTO> sitterNAJobDTOList = getNAJobsList(userId);
            for (SitterNAJobDTO tempJob :
                    sitterNAJobDTOList) {
                if (tempJob.getJobId() == jobId) {
                    isPresent = true;
                }
            }
            return isPresent;
        } catch (Exception e) {

            Logger.getLogger(SitterService.class.getName()).info("Job in NAJobList check failed: " + e);
            throw new ServiceException();
        }
    }

    public static int getUserIdforJobAppId(int jobAppId) throws ServiceException {
        Transaction tx = MyApplicationContext.getHibSession().beginTransaction();
        try {
            JobApplicationDAO jobApplicationDAO = MyApplicationContext.getFactory(JobApplicationDAO.class);
            JobApplication jobApplication = jobApplicationDAO.get(jobAppId);
            tx.commit();
            return jobApplication.getSitter().getMemberId();
        } catch (Exception e) {
            try {
                tx.rollback();
            } catch (RuntimeException e1) {
                Logger.getLogger(SitterService.class.getName()).severe("Coudn't rollback transaction: " + e);
            }
            Logger.getLogger(SitterService.class.getName()).info("Job application fetching failed: " + e);
            throw new ServiceException();
        }
    }

    public static JobApplication getJobApplication(int jobAppId) throws ServiceException {
        Transaction tx = MyApplicationContext.getHibSession().beginTransaction();
        try {
            JobApplicationDAO jobApplicationDAO = MyApplicationContext.getFactory(JobApplicationDAO.class);
            JobApplication jobApplication = jobApplicationDAO.get(jobAppId);
            tx.commit();
            return jobApplication;
        } catch (Exception e) {
            try {
                tx.rollback();
            } catch (RuntimeException e1) {
                Logger.getLogger(SitterService.class.getName()).severe("Coudn't rollback transaction: " + e);
            }
            Logger.getLogger(SitterService.class.getName()).info("Job application fetching failed: " + e);
            throw new ServiceException();
        }
    }
}
