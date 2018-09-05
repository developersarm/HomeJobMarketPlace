package org.care.presentation.visitor;

import org.care.dto.LoginDTO;
import org.care.model.Member;
import org.care.service.MemberService;

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
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        LoginDTO loginDTO = MemberService.authenticateUser(email, password);
        Member.MemberType mType = loginDTO.getmType();
        int userId = loginDTO.getUserId();
        Member.Status status = loginDTO.getStatus();

        HttpSession session = req.getSession();
        RequestDispatcher requestDispatcher;
        if (userId > 0 && status == Member.Status.ACTIVE) {
            session.setAttribute("UserId", userId);
            session.setAttribute("MemberType", mType);
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
            requestDispatcher.include(req, resp);
        }

    }
}
