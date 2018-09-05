package org.care.presentation.visitor;

import org.care.model.Member;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class WelcomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String type = req.getParameter("type");
        Member.MemberType mType = null;
        switch (type) {
            case "sitter":
                mType = Member.MemberType.SITTER;
                break;
            case "seeker":
                mType = Member.MemberType.SEEKER;
                break;
            default:
                resp.getWriter().print("Please select an option");
                break;
        }
        HttpSession session = req.getSession();
        session.setAttribute("MemberType", mType);

        resp.sendRedirect("/HomeJobMarketplace/visitor/login");
//        RequestDispatcher rd = req.getRequestDispatcher("/visitor/login");
//        rd.forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        System.out.println("Post method in Welcome called!");
    }
}
