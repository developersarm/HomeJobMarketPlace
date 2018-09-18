package org.care.dao;

import org.care.context.MyApplicationContext;
import org.care.model.Member;
import org.care.model.Seeker;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SeekerDAO extends MemberDAO<Seeker> {

    @Override
    public Integer create(Seeker obj) {
        Integer id = null;
        try {
            Session session = MyApplicationContext.getHibSession();
            Transaction transaction = session.beginTransaction();

            id = (Integer) session.save(obj);

            transaction.commit();
        } catch (Exception e) {
            Logger.getLogger(SeekerDAO.class.getName()).severe("Can't insert seeker: " + e);
        }
        return id;
    }

    @Override
    public void update(Seeker obj) {
        try {
            Session session = MyApplicationContext.getHibSession();
            Transaction transaction = session.beginTransaction();

            session.update(obj);

            transaction.commit();
        } catch (Exception e) {
            Logger.getLogger(SeekerDAO.class.getName()).severe("Can't update seeker: " + e);
        }
    }

    @Override
    public boolean delete(Serializable id) {
        return super.delete(id);
    }

    @Override
    public Seeker get(Serializable id) {
        Seeker seeker = null;
        try {
            Session session = MyApplicationContext.getHibSession();
            seeker = session.get(Seeker.class, id);

        } catch (Exception e) {
            Logger.getLogger(SeekerDAO.class.getName()).severe("Can't retrieve seeker: " + e);
        }

        return seeker;

    }
}
