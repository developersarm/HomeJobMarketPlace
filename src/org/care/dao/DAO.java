package org.care.dao;

import java.io.Serializable;
import java.sql.SQLException;

public interface DAO<T> {

    Serializable create(T obj) throws DAOException;

    void update(T obj) throws DAOException;

    void delete(Serializable id) throws DAOException;

    T get(Serializable id) throws DAOException;
}
