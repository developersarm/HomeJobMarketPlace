package org.care.presentation.seeker;

import org.care.dto.SeekerJobApplicationDTO;
import org.care.service.SeekerService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ListSeekerJobApplicationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int jobId = Integer.parseInt(req.getParameter("JobId"));
        String jobTitle = SeekerService.getJobTitle(jobId);
        List<SeekerJobApplicationDTO> seekerJobAppDTOS = SeekerService.getJobApplicationsByJobId(jobId);
        req.setAttribute("jobApplications", seekerJobAppDTOS);
        req.setAttribute("jobTitle", jobTitle);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/jsp/seeker/jobapplicationlist.jsp");
        dispatcher.forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
