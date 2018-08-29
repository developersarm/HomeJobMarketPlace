package org.care.presentation.visitor;

import org.care.model.Member;
import org.care.model.Sitter;
import org.care.service.SeekerService;
import org.care.service.SitterService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class MemberRegistrationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/visitor/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String firstName = req.getParameter("firstname");
        String lastName = req.getParameter("lastname");
        int phoneNo = Integer.parseInt(req.getParameter("phoneno"));
        String emailId = req.getParameter("emailid");
        String password = req.getParameter("password");
        String typeParam = req.getParameter("type");
        Member.MemberType memberType = typeParam.equals("SEEKER")? Member.MemberType.SEEKER : Member.MemberType.SITTER;
        HttpSession session = req.getSession();
        session.setAttribute("MemberType", typeParam);
        String address = req.getParameter("address");
        int pincode = Integer.parseInt(req.getParameter("pincode"));

        if (memberType == Member.MemberType.SITTER) {
            int experience = Integer.parseInt(req.getParameter("experience"));
            Sitter sitter = new Sitter(firstName, lastName, phoneNo, emailId, password, address,pincode, experience);
            SitterService.register(sitter);
        }

    }
}
