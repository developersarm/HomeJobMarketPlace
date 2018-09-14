package org.care.context;

import org.care.dao.DAO;
import org.care.model.Member;
import org.care.service.MemberService;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyApplicationContext {

    private static ThreadLocal<MyApplicationContext> appContextThreadLocal = new ThreadLocal<>();
    private static DAOFactory daoFactory = new DAOFactory();
    private static SessionFactory sessionFactory;
    private HttpServletRequest httpRequest;
    private Session hibSession;

    private MyApplicationContext() {
    }

    public static MyApplicationContext get() {
        return appContextThreadLocal.get();
    }

    public static void initContext() throws HibernateException {
        Configuration cfg = new Configuration();
        cfg.configure("config/hibernate.cfg.xml");
        sessionFactory = cfg.buildSessionFactory();
    }

    public static void destroyContext() throws HibernateException {
        sessionFactory.close();
    }

    public static void create(HttpServletRequest httpServletRequest) {
        if (appContextThreadLocal.get() != null) {
            throw new IllegalStateException("Thread local application context already exists!");
        }
        MyApplicationContext myContext = new MyApplicationContext();
        appContextThreadLocal.set(myContext);
        myContext.httpRequest = httpServletRequest;

        try {
            myContext.hibSession = sessionFactory.openSession();
        } catch (HibernateException e) {
            Logger.getLogger(MyApplicationContext.class.getName())
                    .log(Level.SEVERE, "Failed to open hibernate session", e);
            throw new RuntimeException("Failed to hibernate session", e);
        }
    }

    public static void destroy() {
        MyApplicationContext myApplicationContext = appContextThreadLocal.get();
        if (myApplicationContext == null) {
            throw new IllegalStateException("Thread local application context is already destroyed!");
        }
        try {
            myApplicationContext.hibSession.close();
        } catch (Exception e) {
            Logger.getLogger(MyApplicationContext.class.getName())
                    .log(Level.SEVERE, "Failed to close hibernate session", e);
            throw new RuntimeException("Failed to close hibernate session", e);
        }
        appContextThreadLocal.remove();
    }

    public static <T extends DAO> T getFactory(Class<T> clazz) {
        //todo: include null check
        return daoFactory.getDAO(clazz);
    }

    public static Session getHibSession() {
        return get().getHibernateSession();
    }

    private Session getHibernateSession() {
        return hibSession;
    }


    public Member getMember() {
        Integer memberId = (Integer) httpRequest.getSession().getAttribute("UserId");
        if (memberId != null) {
            return MemberService.getMemberForId(memberId);
        }
        return null;
    }

    public String getAppURI() {
        return "/HomeJobMarketplace";
    }
}
