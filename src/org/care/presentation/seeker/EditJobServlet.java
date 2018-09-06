package org.care.presentation.seeker;

import org.care.dto.SeekerJobDTO;
import org.care.service.SeekerService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class EditJobServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int jobId = Integer.parseInt(req.getParameter("JobId"));
        SeekerJobDTO seekerJobDTO = SeekerService.getJob(jobId);
        req.setAttribute("Job", seekerJobDTO);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/jsp/seeker/editjob.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        int userId = (int) session.getAttribute("UserId");
        String jobId = req.getParameter("jobid");
        String title = req.getParameter("title");
        String startDate = req.getParameter("startdate");
        String endDate = req.getParameter("enddate");
        String payPerHour = req.getParameter("payperhour");
        SeekerJobDTO jobDTO = new SeekerJobDTO(jobId, title, startDate, endDate, payPerHour);

        if (jobDTO.validate()) {
            boolean isUpdated = SeekerService.updateJob(userId, jobDTO);

            if (isUpdated) {
                req.setAttribute("msg", "Updated successfully!");
            } else {
                req.setAttribute("error", "Updation failed");
            }
            req.getRequestDispatcher("/HomeJobMarketplace/seeker/list-job").forward(req, resp);
        } else {
            req.setAttribute("Job", jobDTO);
            RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/jsp/seeker/editjob.jsp");
            dispatcher.forward(req, resp);
        }
    }
}
