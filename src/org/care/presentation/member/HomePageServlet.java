package org.care.presentation.member;

import org.care.model.Member;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class HomePageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Member.MemberType mType = (Member.MemberType) session.getAttribute("MemberType");
        int userId = (int) session.getAttribute("UserId");
        RequestDispatcher rd;

        if (mType == Member.MemberType.SITTER) {
            rd = req.getRequestDispatcher("/WEB-INF/jsp/sitter/home.jsp");
            rd.forward(req, resp);
        } else if (mType == Member.MemberType.SEEKER) {
            rd = req.getRequestDispatcher("/WEB-INF/jsp/seeker/home.jsp");
            rd.forward(req, resp);
        } else {
            // todo: go to homepage if MemberType not set after giving a error message
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Member.MemberType mType = (Member.MemberType) session.getAttribute("MemberType");
        int userId = (int) session.getAttribute("UserId");
        RequestDispatcher rd;

        if (mType == Member.MemberType.SITTER) {
            rd = req.getRequestDispatcher("/WEB-INF/jsp/sitter/home.jsp");
            rd.forward(req, resp);
        } else if (mType == Member.MemberType.SEEKER) {
            rd = req.getRequestDispatcher("/WEB-INF/jsp/seeker/home.jsp");
            rd.forward(req, resp);
        } else {
            // todo: go to homepage if MemberType not set after giving a error message
        }
    }
}
