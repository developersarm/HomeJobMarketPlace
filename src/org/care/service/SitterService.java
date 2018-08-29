package org.care.service;

import org.care.context.MyApplicationContext;
import org.care.dao.SitterDAO;
import org.care.model.Sitter;

import java.util.logging.Level;
import java.util.logging.Logger;

public class SitterService {

    public static void register(Sitter sitter) {
        MyApplicationContext.getFactory(SitterDAO.class).create(sitter);
    }
}
