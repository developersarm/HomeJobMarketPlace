package org.care.service;

import org.care.context.MyApplicationContext;
import org.care.dao.SeekerDAO;
import org.care.model.Seeker;
import org.care.model.Sitter;

public class SeekerService {

    public static void register(Seeker seeker) {
        MyApplicationContext.getFactory(SeekerDAO.class).create(seeker);
    }

    public static int validateUser(String email, String password) {
        /*
        TODO hash the password
         */
        return MyApplicationContext.getFactory(SeekerDAO.class).get(email, password);
    }

    public void selectApplication(Sitter s) {

    }
}
