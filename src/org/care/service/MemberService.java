package org.care.service;

import org.care.context.MyApplicationContext;
import org.care.dao.JobApplicationDAO;
import org.care.dao.JobDAO;
import org.care.dao.MemberDAO;
import org.care.dto.LoginDTO;
import org.care.model.Job;
import org.care.model.Member;

import java.util.List;
import java.util.Map;

public class MemberService {

    public static LoginDTO authenticateUser(String email, String password) {
        MemberDAO memberDAO = MyApplicationContext.getFactory(MemberDAO.class);
        Map<String, Object> loginMap = memberDAO.get(email, password);
        int userId = (int) loginMap.get("UserId");
        Member.MemberType mType = (Member.MemberType) loginMap.get("MemberType");
        Member.Status status = (Member.Status) loginMap.get("Status");
        return new LoginDTO(userId, email, password, mType, status);
    }

    public static boolean deleteUser(int userId) {
        MemberDAO memberDAO = MyApplicationContext.getFactory(MemberDAO.class);
        JobDAO jobDAO = MyApplicationContext.getFactory(JobDAO.class);
        JobApplicationDAO jobApplicationDAO = MyApplicationContext.getFactory(JobApplicationDAO.class);

        Member.MemberType mType = memberDAO.get(userId).getType();

        if (mType == Member.MemberType.SEEKER) {
            List<Job> jobList = jobDAO.getJobsPostedBy(userId);
            for (Job tempJob :
                    jobList) {
                int totalDeleted = jobApplicationDAO.deleteByJobId(tempJob.getId());
                if (totalDeleted >= 0) {
                    jobDAO.delete(tempJob.getId());
                }
            }
        } else {
            jobApplicationDAO.deleteByUserId(userId);
        }

        return memberDAO.delete(userId);
    }
}
