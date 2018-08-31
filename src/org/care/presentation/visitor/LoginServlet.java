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
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/jsp/visitor/login.jsp");
        requestDispatcher.forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        HttpSession session = req.getSession();
        Member.MemberType mType = (Member.MemberType) session.getAttribute("MemberType");
        RequestDispatcher requestDispatcher;
        int userId = 0;

        if (mType == Member.MemberType.SEEKER) {
            userId = SeekerService.validateUser(email, password);
        } else if (mType == Member.MemberType.SITTER) {
            userId = SitterService.validateUser(email, password);
        } else {
            // todo: redirect to index with a error message
        }

        // todo: Check if the member is seeker and session if is sitter and vice-versa
        // todo: destroy the userid saved in session while logging out

        if (userId > 0) {
            session.setAttribute("UserId", userId);
            if (mType == Member.MemberType.SITTER) {
                requestDispatcher = req.getRequestDispatcher("/sitter/home");
                requestDispatcher.forward(req, resp);
            } else {
                requestDispatcher = req.getRequestDispatcher("/seeker/home");
                requestDispatcher.forward(req, resp);
            }
        } else {
            req.setAttribute("error", "Invalid Username/password");
            requestDispatcher = req.getRequestDispatcher("/WEB-INF/jsp/visitor/login.jsp");
            requestDispatcher.include(req,resp);
        }

    }
}
