package org.care.dao;

import org.care.context.MyApplicationContext;
import org.care.model.Sitter;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.Serializable;
import java.util.logging.Logger;

public class SitterDAO extends MemberDAO<Sitter> {

    @Override
    public Integer create(Sitter obj) throws DAOException {
        Integer id;
        try {
            Session session = MyApplicationContext.getHibSession();
            id = (Integer) session.save(obj);

        } catch (Exception e) {
            Logger.getLogger(SitterDAO.class.getName()).severe("Can't insert sitter: " + e);
            throw new DAOException();
        }
        return id;
    }

    @Override
    public void update(Sitter obj) throws DAOException {
        try {
            Session session = MyApplicationContext.getHibSession();
            session.update(obj);

        } catch (Exception e) {
            Logger.getLogger(SitterDAO.class.getName()).severe("Can't update sitter: " + e);
            throw new DAOException();
        }
    }

    @Override
    public void delete(Serializable id) throws DAOException {
        super.delete(id);
    }

    @Override
    public Sitter get(Serializable id) throws DAOException {
        Sitter sitter;

        try {
            Session session = MyApplicationContext.getHibSession();
            sitter = session.get(Sitter.class, id);

        } catch (Exception e) {
            Logger.getLogger(SitterDAO.class.getName()).info("Can't retrieve sitter: " + e);
            throw new DAOException();
        }
        return sitter;
    }
}
