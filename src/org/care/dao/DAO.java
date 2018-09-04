package org.care.dao;

import java.io.Serializable;
import java.sql.SQLException;

public interface DAO<T> {

    public void create(T obj) throws SQLException;

    public void update(T obj);

    public boolean delete(Serializable id);

    public T get(Serializable id);
}
