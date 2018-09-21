package org.care.service;

import org.care.context.MyApplicationContext;
import org.care.dao.DAOException;
import org.care.dao.JobApplicationDAO;
import org.care.dao.JobDAO;
import org.care.dao.MemberDAO;
import org.care.dto.LoginDTO;
import org.care.model.Job;
import org.care.model.Member;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class MemberService {

    public static LoginDTO authenticateUser(String email, String password) throws ServiceException {
        Transaction tx = MyApplicationContext.getHibSession()
                .beginTransaction();
        try {

            MemberDAO memberDAO = MyApplicationContext.getFactory(MemberDAO.class);
            Map<String, Object> loginMap = memberDAO.get(email, password);
            int userId = (int) loginMap.get("UserId");
            Member.MemberType mType = (Member.MemberType) loginMap.get("MemberType");
            Member.Status status = (Member.Status) loginMap.get("Status");

            tx.commit();
            return new LoginDTO(userId, email, password, mType, status);

        } catch (Exception e) {
            try {
                tx.rollback();
            } catch (RuntimeException e1) {
                Logger.getLogger(MemberService.class.getName()).severe("Coudn't rollback transaction: " + e);
            }
            Logger.getLogger(MemberService.class.getName()).info("Authentication failed: " + e);
            throw new ServiceException();

        }
    }

    public static void deleteUser(int userId) throws ServiceException {
        Transaction tx = MyApplicationContext.getHibSession().beginTransaction();
        try {
            MemberDAO memberDAO = MyApplicationContext.getFactory(MemberDAO.class);
            JobDAO jobDAO = MyApplicationContext.getFactory(JobDAO.class);
            JobApplicationDAO jobApplicationDAO = MyApplicationContext.getFactory(JobApplicationDAO.class);

            Member.MemberType mType = memberDAO.get(userId).getType();

            if (mType == Member.MemberType.SEEKER) {
                List<Job> jobList = jobDAO.getJobsPostedBy(userId);
                for (Job tempJob :
                        jobList) {
                    int totalDeleted = jobApplicationDAO.deleteByJobId(tempJob.getJobId());
                    if (totalDeleted >= 0) {
                        jobDAO.delete(tempJob.getJobId());
                    }
                }
            } else {
                jobApplicationDAO.deleteByUserId(userId);
            }

            memberDAO.delete(userId);
            tx.commit();

        } catch (Exception e) {
            try {
                tx.rollback();
            } catch (RuntimeException e1) {
                Logger.getLogger(MemberService.class.getName()).severe("Coudn't rollback transaction: " + e);
            }
            Logger.getLogger(MemberService.class.getName()).info("User Deletion failed: " + e);
            throw new ServiceException();
        }
    }
    public static boolean isEmailIdRegistered(String emailId) throws ServiceException {
        Transaction tx = MyApplicationContext.getHibSession().beginTransaction();
        try {
            MemberDAO memberDAO = MyApplicationContext.getFactory(MemberDAO.class);
            int userId = memberDAO.getByEmailId(emailId);
            tx.commit();
            return userId > 0;
        } catch (Exception e) {
            try {
                tx.rollback();
            } catch (RuntimeException e1) {
                Logger.getLogger(MemberService.class.getName()).severe("Coudn't rollback transaction: " + e);
            }
            Logger.getLogger(MemberService.class.getName()).info("Email checking failed: " + e);
            throw new ServiceException();
        }
    }

    public static Member getMemberForId(Integer id) throws ServiceException {
        Transaction tx = MyApplicationContext.getHibSession().beginTransaction();
        try {
            Member member = MyApplicationContext.getFactory(MemberDAO.class).get(id);
            tx.commit();
            return member;
        } catch (Exception e) {
            try {
                tx.rollback();
            } catch (RuntimeException e1) {
                Logger.getLogger(MemberService.class.getName()).severe("Coudn't rollback transaction: " + e);
            }
            Logger.getLogger(MemberService.class.getName()).info("Member fetching failed: " + e);
            throw new ServiceException();
        }
    }
}
