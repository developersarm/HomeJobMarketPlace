package org.care.dao;

import org.care.context.MyApplicationContext;
import org.care.model.Seeker;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.Serializable;
import java.util.logging.Logger;

public class SeekerDAO extends MemberDAO<Seeker> {

    @Override
    public Integer create(Seeker obj) throws DAOException {
        Integer id;
        try {
            Session session = MyApplicationContext.getHibSession();
            id = (Integer) session.save(obj);
        } catch (Exception e) {
            Logger.getLogger(SeekerDAO.class.getName()).severe("Can't insert seeker: " + e);
            throw new DAOException();
        }
        return id;
    }

    @Override
    public void update(Seeker obj) throws DAOException {
        try {
            Session session = MyApplicationContext.getHibSession();
            session.update(obj);
        } catch (Exception e) {
            Logger.getLogger(SeekerDAO.class.getName()).severe("Can't update seeker: " + e);
            throw new DAOException();
        }
    }

    @Override
    public void delete(Serializable id) throws DAOException {
        super.delete(id);
    }

    @Override
    public Seeker get(Serializable id) throws DAOException {
        Seeker seeker;
        try {
            Session session = MyApplicationContext.getHibSession();
            seeker = session.get(Seeker.class, id);

        } catch (Exception e) {
            Logger.getLogger(SeekerDAO.class.getName()).severe("Can't retrieve seeker: " + e);
            throw new DAOException();
        }

        return seeker;

    }
}
