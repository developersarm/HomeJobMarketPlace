package org.care.context;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyApplicationContext {

    private static Connection jdbcConnection;
    private static ThreadLocal<MyApplicationContext> appContextThreadLocal = new ThreadLocal<>();

    private MyApplicationContext() {
    }

    public static void initContext() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        jdbcConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/homejobmarketplace",
                "root", "admin");
    }

    public static void destroyContext() throws SQLException{
        jdbcConnection.close();
    }

    public static void create() {
        if (appContextThreadLocal.get() != null) {
            throw new IllegalStateException("Thread local application context already exists!");
        }
        appContextThreadLocal.set(new MyApplicationContext());
    }

    public static void destroy() {
        if (appContextThreadLocal.get() == null) {
            throw new IllegalStateException("Thread local application context is already destroyed!");
        }
        appContextThreadLocal.remove();
    }
}
