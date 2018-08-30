package org.care.dao;

import org.care.context.MyApplicationContext;
import org.care.model.Sitter;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SitterDAO extends MemberDAO<Sitter> {

    @Override
    public void create(Sitter obj) {
        //inserting data into member first
        super.create(obj);

        Connection myConn = MyApplicationContext.getJdbcConnection();
        PreparedStatement myStmt = null;

        try {
            String sql = "insert into sitter "
                    + "(sitter_id, experience)"
                    + "values(?,?)";

            myStmt = myConn.prepareStatement(sql);
            myStmt.setString(1, String.valueOf(obj.getId()));
            myStmt.setString(2, String.valueOf(obj.getExperience()));

            int affectedRows = myStmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

        } catch (SQLException e) {
            Logger logger = Logger.getLogger(SitterDAO.class.getName());
            logger.log(Level.SEVERE, "exception while performing insert operation" + e);
        }
    }

    @Override
    public void update(Sitter obj) {
        super.update(obj);
    }

    @Override
    public Sitter delete(Serializable id) {
        return super.delete(id);
    }

    @Override
    public Sitter get(Serializable id) {
        return super.get(id);
    }
}
