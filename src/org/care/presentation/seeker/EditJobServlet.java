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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        try {
            //todo: correct date error in jsp
            HttpSession session = req.getSession();
            int userId = (int) session.getAttribute("UserId");
            int jobId = Integer.parseInt(req.getParameter("jobid"));
            String title = req.getParameter("title");
            Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(req.getParameter("startdate"));
            Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(req.getParameter("enddate"));
            Double payPerHour = Double.valueOf(req.getParameter("payperhour"));
            SeekerJobDTO jobDTO = new SeekerJobDTO(jobId, title, startDate, endDate, payPerHour);
            boolean isUpdated = SeekerService.updateJob(userId, jobDTO);

            if (isUpdated) {
                req.setAttribute("msg", "Updated successfully!");
            } else {
                req.setAttribute("error", "Updation failed");
            }
            resp.sendRedirect("/HomeJobMarketplace/seeker/list-job");
        } catch (ParseException e) {
            Logger logger = Logger.getLogger(EditJobServlet.class.getName());
            logger.log(Level.SEVERE, "Cannot convert html date into java date format");
        }
    }
}
