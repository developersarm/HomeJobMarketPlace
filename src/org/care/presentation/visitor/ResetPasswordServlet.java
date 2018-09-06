package org.care.presentation.visitor;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResetPasswordServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().print("this is resetpassword");
        RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/jsp/visitor/resetpassword.jsp");
        rd.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //todo: give implementation for forget password
        resp.getWriter().print("this is resetpassword");
//        RequestDispatcher rd = req.getRequestDispatcher("/jsp/visitor/resetpassword.jsp");
//        rd.include(req, resp);
    }
}
