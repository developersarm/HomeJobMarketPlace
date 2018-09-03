package org.care.presentation.seeker;

import org.care.model.Job;
import org.care.model.JobApplication;
import org.care.service.SeekerService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NewJobServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/jsp/seeker/newjob.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            HttpSession session = req.getSession();
            int userId = (int) session.getAttribute("UserId");
            String title = req.getParameter("title");
            Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(req.getParameter("startdate"));
            Timestamp startDateTS = new Timestamp(startDate.getTime());
            Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(req.getParameter("enddate"));
            Timestamp endDateTS = new Timestamp(endDate.getTime());
            float payPerHour = Float.parseFloat(req.getParameter("payperhour"));

            Job job = new Job(title, userId, startDateTS, endDateTS, payPerHour);
            SeekerService.postJob(job);
        } catch (ParseException e) {
            Logger logger = Logger.getLogger(NewJobServlet.class.getName());
            logger.log(Level.SEVERE, "Cannot convert html date into java date format");
        }

        //todo: Generate error message on unsuccessful registration

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/seeker/home");
        requestDispatcher.forward(req,resp);
    }
}
