package org.care.presentation.seeker;

import org.care.service.SeekerService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class DeleteJobServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //todo: delete applications also before deleting job
        HttpSession session = req.getSession();
        int userId = (int) session.getAttribute("UserId");
        int jobId = Integer.parseInt(req.getParameter("JobId"));
        boolean isDeleted = SeekerService.deleteJob(jobId);

        if (isDeleted) {
            req.setAttribute("msg", "Deleted Successfully!");
        } else {
            req.setAttribute("error", "Can't delete job");
        }
        resp.sendRedirect("/HomeJobMarketplace/seeker/list-job");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
