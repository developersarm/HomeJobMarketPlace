package org.care.dao;

import org.care.model.Member;

import java.io.Serializable;

public class MemberDAO<T extends Member> implements DAO<T> {

    @Override
    public void create(T obj) {

    }

    @Override
    public void update(T obj) {

    }

    @Override
    public T delete(Serializable id) {
        return null;
    }

    @Override
    public T get(Serializable id) {
        return null;
    }
}
