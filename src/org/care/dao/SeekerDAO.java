package org.care.dao;

import org.care.context.MyApplicationContext;
import org.care.model.Seeker;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SeekerDAO extends MemberDAO<Seeker> {

    @Override
    public void create(Seeker obj) {
        //inserting data into member first
        super.create(obj);
        Connection myConn = MyApplicationContext.getJdbcConnection();
        PreparedStatement myStmt = null;

        try {
            String sql = "insert into seeker "
                    + "(seeker_id, total_children, spouse_name)"
                    + "values(?,?,?)";

            myStmt = myConn.prepareStatement(sql);
            myStmt.setString(1, String.valueOf(obj.getId()));
            myStmt.setString(2, String.valueOf(obj.getTotalChildren()));
            myStmt.setString(3, String.valueOf(obj.getSpouseName()));

            int affectedRows = myStmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

        } catch (SQLException e) {
            Logger logger = Logger.getLogger(SeekerDAO.class.getName());
            logger.log(Level.SEVERE, "exception while performing insert operation" + e);
        }
    }

    @Override
    public void update(Seeker obj) {
        super.update(obj);
    }

    @Override
    public Seeker delete(Serializable id) {
        return super.delete(id);
    }

    @Override
    public Seeker get(Serializable id) {
        return super.get(id);
    }
}
