package org.care.presentation.seeker;

import org.care.dto.SeekerJobsListDTO;
import org.care.service.SeekerService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class ListSeekerJobsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        int userId = (int) session.getAttribute("UserId");
        List<SeekerJobsListDTO> seekerJobsListDTOS = SeekerService.getJobsList(userId);
        req.setAttribute("JobsList", seekerJobsListDTOS);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/jsp/seeker/yourjobs.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
