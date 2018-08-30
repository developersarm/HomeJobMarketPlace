package org.care.presentation.visitor;

import org.care.model.Member;
import org.care.service.SeekerService;
import org.care.service.SitterService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/jsp/visitor/login.jsp");
        rd.forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        HttpSession session = req.getSession();
        Member.MemberType mType = (Member.MemberType) session.getAttribute("MemberType");
        RequestDispatcher rd;
        int userId = 0;

        if (mType == Member.MemberType.SEEKER) {
            userId = SeekerService.validateUser(email, password);
        } else if (mType == Member.MemberType.SITTER) {
            userId = SitterService.validateUser(email, password);
        } else {
            resp.getWriter().print("Please go to homepage! (Member type not set");
        }

        if (userId > 0) {
            session.setAttribute("UserId", userId);
            rd = req.getRequestDispatcher("/member/home");
            rd.forward(req, resp);
        } else {
            resp.getWriter().print("Invalid email id or password!");
            this.doGet(req, resp);
        }

    }
}
