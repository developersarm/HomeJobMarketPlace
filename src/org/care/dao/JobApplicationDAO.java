package org.care.dao;

import org.care.context.MyApplicationContext;
import org.care.model.JobApplication;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JobApplicationDAO implements DAO<JobApplication> {

    @Override
    public void create(JobApplication obj) {
    }

    @Override
    public void update(JobApplication obj) {

    }

    @Override
    public JobApplication delete(Serializable id) {
        return null;
    }

    @Override
    public JobApplication get(Serializable id) {
        return null;
    }

    public List<JobApplication> get(int userId, int noOfResults) {
        Connection myConn = MyApplicationContext.getJdbcConnection();
        List<JobApplication> resultList = new LinkedList<>();
        try{
            PreparedStatement myStmt = null;
            ResultSet myRs = null;

            String sql = "SELECT * FROM job_application WHERE member_id=? LIMIT ?";
            myStmt = myConn.prepareStatement(sql);
            myStmt.setInt(1, userId);
            myStmt.setInt(2, noOfResults);
            myRs = myStmt.executeQuery();
            while (myRs.next())
            {
                int id = myRs.getInt("id");
                int jobId = myRs.getInt("job_id");
                int expectedPay = myRs.getInt("expected_pay");
                JobApplication.Status status = JobApplication.Status.valueOf(myRs.getString("status"));
                JobApplication tempJobApplication = new JobApplication(id, jobId, userId, expectedPay, status);
                resultList.add(tempJobApplication);
            }
        } catch (Exception e) {
            Logger logger = Logger.getLogger(MemberDAO.class.getName());
            logger.log(Level.SEVERE, "exception while performing retrieve operation using " +
                    "member_id and LIMIT: " + e);
        }
        return resultList;
    }

    public List<JobApplication> get(int userId) {
        Connection myConn = MyApplicationContext.getJdbcConnection();
        List<JobApplication> resultList = new LinkedList<>();
        try{
            PreparedStatement myStmt = null;
            ResultSet myRs = null;

            String sql = "SELECT * FROM job_application WHERE member_id=?";
            myStmt = myConn.prepareStatement(sql);
            myStmt.setInt(1, userId);
            myRs = myStmt.executeQuery();
            while (myRs.next())
            {
                int id = myRs.getInt("id");
                int jobId = myRs.getInt("job_id");
                int expectedPay = myRs.getInt("expected_pay");
                JobApplication.Status status = JobApplication.Status.valueOf(myRs.getString("status"));
                JobApplication tempJobApplication = new JobApplication(id, jobId, userId, expectedPay, status);
                resultList.add(tempJobApplication);
            }
        } catch (Exception e) {
            Logger logger = Logger.getLogger(MemberDAO.class.getName());
            logger.log(Level.SEVERE, "exception while performing retrieve operation using " +
                    "member_id: " + e);
        }
        return resultList;
    }

    public List<JobApplication> get(int userId, JobApplication.Status status) {
        Connection myConn = MyApplicationContext.getJdbcConnection();
        List<JobApplication> resultList = new LinkedList<>();
        try{
            PreparedStatement myStmt = null;
            ResultSet myRs = null;

            String sql = "SELECT * FROM job_application WHERE member_id=? and status=?";
            myStmt = myConn.prepareStatement(sql);
            myStmt.setInt(1, userId);
            myStmt.setString(2, String.valueOf(status));
            myRs = myStmt.executeQuery();
            while (myRs.next())
            {
                int id = myRs.getInt("id");
                int jobId = myRs.getInt("job_id");
                int expectedPay = myRs.getInt("expected_pay");
                JobApplication tempJobApplication = new JobApplication(id, jobId, userId, expectedPay, status);
                resultList.add(tempJobApplication);
            }
        } catch (Exception e) {
            Logger logger = Logger.getLogger(MemberDAO.class.getName());
            logger.log(Level.SEVERE, "exception while performing retrieve operation using " +
                    "member_id and status: " + e);
        }
        return resultList;
    }
}
