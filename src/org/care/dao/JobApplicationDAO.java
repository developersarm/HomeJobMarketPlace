package org.care.dao;

import org.care.context.MyApplicationContext;
import org.care.model.JobApplication;

import java.io.Serializable;
import java.sql.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JobApplicationDAO implements DAO<JobApplication> {

    @Override
    public void create(JobApplication obj) {
        Connection myConn = MyApplicationContext.getJdbcConnection();
        String sql = "insert into job_application "
                + "(job_id, member_id, expected_pay)"
                + "values(?,?,?)";
        try (PreparedStatement myStmt = myConn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            myStmt.setInt(1, obj.getJobId());
            myStmt.setInt(2, obj.getMemberId());
            myStmt.setDouble(3, obj.getExpectedPay());
            int affectedRows = myStmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            try (ResultSet generatedKeys = myStmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    obj.setId(generatedKeys.getInt(1));
                }
                else {
                    throw new SQLException("Job Application creation failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            Logger logger = Logger.getLogger(JobApplicationDAO.class.getName());
            logger.log(Level.SEVERE, "exception while performing insert operation: " + e);
        }
    }

    @Override
    public void update(JobApplication obj) {
        Connection myConn = MyApplicationContext.getJdbcConnection();
        String sql = "UPDATE job_application "
                + "SET expected_pay=? "
                + "WHERE id=?";
        try (PreparedStatement myStmt = myConn.prepareStatement(sql)){
            myStmt.setDouble(1, obj.getExpectedPay());
            myStmt.setInt(2, obj.getId());
            int affectedRows = myStmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Updating job application failed, no rows affected.");
            }
        } catch (SQLException e) {
            Logger logger = Logger.getLogger(MemberDAO.class.getName());
            logger.log(Level.SEVERE, "exception while performing update operation: " + e);
        }
    }

    @Override
    public boolean delete(Serializable id) {
        boolean isDeleted = false;

        Connection myConn = MyApplicationContext.getJdbcConnection();
        String sql = "UPDATE job_application "
                + "SET status='INACTIVE' "
                + "WHERE id=?";
        try (PreparedStatement myStmt = myConn.prepareStatement(sql)){
            myStmt.setInt(1, (Integer) id);
            int affectedRows = myStmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Deleting job application failed, no rows affected.");
            }

            isDeleted = true;
        } catch (SQLException e) {
            Logger logger = Logger.getLogger(MemberDAO.class.getName());
            logger.log(Level.SEVERE, "exception while performing delete operation: " + e);
        }
        return isDeleted;
    }

    @Override
    public JobApplication get(Serializable id) {
        return null;
    }

    public List<JobApplication> get(int userId, int noOfResults) {
        Connection myConn = MyApplicationContext.getJdbcConnection();
        List<JobApplication> resultList = new LinkedList<>();
        //todo: use try-with-resourses
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
                Double expectedPay = myRs.getDouble("expected_pay");
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
                double expectedPay = myRs.getDouble("expected_pay");
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
                double expectedPay = myRs.getDouble("expected_pay");
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

    public List<Map<String, Object>> getSitterJobsList(int userId) {
        List<Map<String, Object>> resultList = new LinkedList<>();
        Connection myConn = MyApplicationContext.getJdbcConnection();
        String sql = "select title, start_date, expected_pay from job_application app, job jb " +
                "where app.job_id = jb.id and app.member_id=? ";
        try (PreparedStatement myStmt = myConn.prepareStatement(sql)) {
            myStmt.setInt(1, userId);
            try (ResultSet myRs = myStmt.executeQuery()) {
                while (myRs.next())
                {
                    Map<String, Object> tempMap = new HashMap<>();
                    tempMap.put("title", myRs.getString("title"));
                    tempMap.put("startDate", myRs.getTimestamp("start_date"));
                    tempMap.put("expectedPay", myRs.getDouble("expected_pay"));
                    resultList.add(tempMap);
                }
            }
        } catch (Exception e) {
            Logger logger = Logger.getLogger(JobApplicationDAO.class.getName());
            logger.log(Level.SEVERE, "exception while performing retrieve operation using " +
                    "member_id : " + e);
        }
        return resultList;
    }

    public List<Map<String, Object>> getSitterNAJobsList(int userId) {
        List<Map<String, Object>> resultList = new LinkedList<>();
        Connection myConn = MyApplicationContext.getJdbcConnection();
        String sql = "SELECT id, title, start_date, pay_per_hour FROM job WHERE id NOT IN " +
                "(SELECT job_id FROM job_application WHERE member_id = ?) AND status='ACTIVE'";
        try (PreparedStatement myStmt = myConn.prepareStatement(sql)) {
            myStmt.setInt(1, userId);
            try (ResultSet myRs = myStmt.executeQuery()) {
                while (myRs.next())
                {
                    Map<String, Object> tempMap = new HashMap<>();
                    tempMap.put("jobId", myRs.getInt("id"));
                    tempMap.put("title", myRs.getString("title"));
                    tempMap.put("startDate", myRs.getTimestamp("start_date"));
                    tempMap.put("payPerHour", myRs.getDouble("pay_per_hour"));
                    resultList.add(tempMap);
                }
            }
        } catch (Exception e) {
            Logger logger = Logger.getLogger(JobApplicationDAO.class.getName());
            logger.log(Level.SEVERE, "exception while performing retrieve operation using " +
                    "member_id : " + e);
        }
        return resultList;
    }
}
