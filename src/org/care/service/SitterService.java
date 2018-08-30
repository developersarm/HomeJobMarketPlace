package org.care.service;

import org.care.context.MyApplicationContext;
import org.care.dao.SitterDAO;
import org.care.model.Sitter;

public class SitterService {

    public static void register(Sitter sitter) {
        MyApplicationContext.getFactory(SitterDAO.class).create(sitter);
    }

    public static int validateUser(String email, String password) {
        //todo hash the password
        return MyApplicationContext.getFactory(SitterDAO.class).get(email, password);
    }
}
