package org.care.presentation.seeker;

import org.care.model.JobApplication;
import org.care.service.SeekerService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class SeekerHomePageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Logger.getLogger(SeekerHomePageServlet.class.getName()).info("Get called in Home page");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        int userId = (int) session.getAttribute("UserId");

        List<JobApplication> jobApplications;
        jobApplications = SeekerService.getJobApplications(userId, JobApplication.Status.ACTIVE);
        req.setAttribute("JobApplications", jobApplications);

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/jsp/seeker/home.jsp");
        requestDispatcher.forward(req,resp);
    }
}
