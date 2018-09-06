package org.care.presentation.seeker;

import org.care.dto.JobPostFormDTO;
import org.care.service.SeekerService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class NewJobServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/jsp/seeker/newjob.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        int userId = (int) session.getAttribute("UserId");

        String title = req.getParameter("title");
        String startDate = req.getParameter("startdate");
        String endDate = req.getParameter("enddate");
        String payPerHour = req.getParameter("payperhour");

        JobPostFormDTO jobPostFormDTO = new JobPostFormDTO(title, userId, startDate, endDate, payPerHour);

        if (jobPostFormDTO.validate()) {
            boolean isPosted = SeekerService.postJob(jobPostFormDTO);
            if (isPosted) {
                req.setAttribute("msg", "Job posted successfully!");
                req.getRequestDispatcher("/seeker/home").forward(req,resp);
            } else {
                req.setAttribute("error", "Failed to post the job!");
                req.getRequestDispatcher("/seeker/home").forward(req,resp);
            }
        } else {
            req.setAttribute("jobData", jobPostFormDTO);
            req.getRequestDispatcher("/WEB-INF/jsp/seeker/newjob.jsp").forward(req,resp);
        }
    }
}
