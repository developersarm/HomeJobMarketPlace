package org.care.dao;

import org.care.context.MyApplicationContext;
import org.care.model.Job;

import java.io.Serializable;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JobDAO implements DAO<Job> {

    @Override
    public void create(Job obj) {
        Connection myConn = MyApplicationContext.getJdbcConnection();
        String sql = "insert into job "
                + "(title, posted_by, start_date, end_date, pay_per_hour)"
                + "values(?,?,?,?,?)";
        try (PreparedStatement myStmt = myConn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            myStmt.setString(1, obj.getTitle());
            myStmt.setInt(2, obj.getPostedBy());
            myStmt.setTimestamp(3, obj.getStartDate());
            myStmt.setTimestamp(4, obj.getEndDate());
            myStmt.setDouble(5, obj.getPayPerHour());

            int affectedRows = myStmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Job creation failed, no rows affected.");
            }

            try (ResultSet generatedKeys = myStmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    obj.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Job creation failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            Logger logger = Logger.getLogger(MemberDAO.class.getName());
            logger.log(Level.SEVERE, "exception while performing insert operation: " + e);
        }
    }

    @Override
    public void update(Job obj) {

    }

    @Override
    public Job delete(Serializable id) {
        return null;
    }

    @Override
    public Job get(Serializable id) {
        return null;
    }

    public List<Job> getJobsPostedBy(int userId) {
        Connection myConn = MyApplicationContext.getJdbcConnection();
        List<Job> resultList = new LinkedList<>();

        String sql = "SELECT * FROM job WHERE posted_by=?";
        try (PreparedStatement myStmt = myConn.prepareStatement(sql)) {
            myStmt.setInt(1, userId);
            try (ResultSet myRs = myStmt.executeQuery()) {
                while (myRs.next()) {
                    int id = myRs.getInt("id");
                    String title = myRs.getString("title");
                    Timestamp startDate = myRs.getTimestamp("start_date");
                    Timestamp endDate = myRs.getTimestamp("end_date");
                    double payPerHour = myRs.getDouble("pay_per_hour");
                    Job.Status status = Job.Status.valueOf(myRs.getString("status"));
                    Job tempJob = new Job(id, title, userId, startDate, endDate, payPerHour, status);
                    resultList.add(tempJob);
                }
            }
        } catch (Exception e) {
            Logger logger = Logger.getLogger(JobDAO.class.getName());
            logger.log(Level.SEVERE, "exception while performing retrieve operation using " +
                    "posted_by: " + e);
        }
        return resultList;
    }
}
