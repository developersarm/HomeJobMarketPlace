package org.care.dao;

import org.care.model.Seeker;

import java.io.Serializable;

public class SeekerDAO extends MemberDAO<Seeker> {

    @Override
    public void create(Seeker obj) {
        super.create(obj);
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
