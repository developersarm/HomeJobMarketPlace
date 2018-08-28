package org.care.dao;

import java.io.Serializable;

public interface DAO<T> {

    public void create(T obj);

    public void update(T obj);

    public T delete(Serializable id);

    public T get(Serializable id);
}
