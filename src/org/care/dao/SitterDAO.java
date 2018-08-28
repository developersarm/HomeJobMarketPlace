package org.care.dao;

import org.care.model.Member;
import org.care.model.Sitter;

import java.io.Serializable;

public class SitterDAO extends MemberDAO<Sitter> {

    @Override
    public void create(Sitter obj) {
        super.create(obj);
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
