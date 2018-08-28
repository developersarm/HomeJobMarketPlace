package org.care.dao;

import org.care.model.Member;
import org.care.model.Sitter;

import java.io.Serializable;

public class SitterDAO extends MemberDAO {
    @Override
    public void create() {
        super.create();
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public Sitter delete(Serializable id) {
        return null;
    }

    @Override
    public Sitter get(Serializable id) {
        return null;
    }
}
