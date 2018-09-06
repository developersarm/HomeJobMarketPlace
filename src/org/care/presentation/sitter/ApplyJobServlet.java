package org.care.presentation.sitter;

import org.care.dto.JobApplicationForm;
import org.care.service.SitterService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ApplyJobServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int jobId = Integer.parseInt(req.getParameter("JobId"));
        req.setAttribute("JobId", jobId);
        String jobTitle = SitterService.getJobTitle(jobId);
        req.setAttribute("Title", jobTitle);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/jsp/sitter/applyjob.jsp");
        dispatcher.forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        int userId = (int) session.getAttribute("UserId");
        String jobId = req.getParameter("jobid");
        String expectedPay = req.getParameter("expectedpay");

        JobApplicationForm jobApplicationForm = new JobApplicationForm(userId, jobId, expectedPay);

        if (jobApplicationForm.validate()) {
            boolean isApplied = SitterService.applyJob(jobApplicationForm);
            if (isApplied) {
                req.setAttribute("msg", "Successfully applied for Job!");
                RequestDispatcher dispatcher = req.getRequestDispatcher("/sitter/home");
                dispatcher.forward(req, resp);
            } else {

                req.setAttribute("error", "Application for Job failed!");
                RequestDispatcher dispatcher = req.getRequestDispatcher("/sitter/home");
                dispatcher.forward(req,resp);
            }
        } else {
            req.setAttribute("JobId", jobId);
            String jobTitle = SitterService.getJobTitle(Integer.parseInt(jobId));
            req.setAttribute("Title", jobTitle);
            req.setAttribute("jobApplication", jobApplicationForm);
            req.getRequestDispatcher("/WEB-INF/jsp/sitter/applyjob.jsp").forward(req,resp);
        }
    }
}
