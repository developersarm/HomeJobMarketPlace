package org.care.presentation.member;

import org.care.service.MemberService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class DeleteProfileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        int userId = (int) session.getAttribute("UserId");
        boolean isDeleted = MemberService.deleteUser(userId);

        if (isDeleted) {
            session.invalidate();
            resp.sendRedirect("/HomeJobMarketplace/");
        } else {
            req.setAttribute("error", "Can't delete profile");
            //todo: add error msg in jsp
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/member/profile");
            requestDispatcher.include(req,resp);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("put of delete profile servlet");
    }
}