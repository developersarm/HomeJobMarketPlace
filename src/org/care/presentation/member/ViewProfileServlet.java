package org.care.presentation.member;

import org.care.dto.ProfileDTO;
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

public class ViewProfileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        int userId = (int) session.getAttribute("UserId");
        Member.MemberType memberType = (Member.MemberType) session.getAttribute("MemberType");
        ProfileDTO profileData = null;
        if (memberType == Member.MemberType.SEEKER) {
            profileData = SeekerService.getProfile(userId);
        } else if (memberType == Member.MemberType.SITTER) {
            profileData = SitterService.getProfile(userId);
        } else {
            resp.sendRedirect("/HomeJobMarketplace/");
        }
        req.setAttribute("ProfileData", profileData);

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/jsp/member/profile.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("entered post");
    }
}
