package org.care.presentation.member;

import org.care.dto.SeekerProfileDTO;
import org.care.dto.SitterProfileDTO;
import org.care.model.Member;
import org.care.service.SeekerService;
import org.care.service.SitterService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SaveProfileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        int userId = (int) session.getAttribute("UserId");
        Member.MemberType memberType = (Member.MemberType) session.getAttribute("MemberType");

        String firstName = req.getParameter("firstname");
        String lastName = req.getParameter("lastname");
        String phoneNo = req.getParameter("phoneno");
        String emailId = req.getParameter("emailid");
        String address = req.getParameter("address");
        String pincode = req.getParameter("pincode");

        if (memberType == Member.MemberType.SITTER) {
            String experience = req.getParameter("experience");
            SitterProfileDTO sitterProfileData = new SitterProfileDTO(firstName, lastName, phoneNo,
                    emailId, address, pincode, experience);

            if (sitterProfileData.validate()) {
                SitterService.updateProfile(userId, sitterProfileData);
                req.getRequestDispatcher("/sitter/home").forward(req, resp);
            } else {
                req.setAttribute("ProfileData", sitterProfileData);
                req.getRequestDispatcher("/WEB-INF/jsp/member/editprofile.jsp").forward(req,resp);
            }

        } else if (memberType == Member.MemberType.SEEKER) {
            String totalChildren = req.getParameter("totalchildren");
            String spouseName = req.getParameter("spousename");
            SeekerProfileDTO seekerProfileData = new SeekerProfileDTO(firstName, lastName, phoneNo,
                    emailId, address, pincode, totalChildren, spouseName);

            if (seekerProfileData.validate()) {
                SeekerService.updateProfile(userId, seekerProfileData);
                req.getRequestDispatcher("/seeker/home").forward(req, resp);
            } else {
                req.setAttribute("ProfileData", seekerProfileData);
                req.getRequestDispatcher("/WEB-INF/jsp/member/editprofile.jsp").forward(req,resp);
            }

        } else {
            req.setAttribute("error", "Please login first!");
            req.getRequestDispatcher("/HomeJobMarketplace").forward(req,resp);
        }
    }
}
