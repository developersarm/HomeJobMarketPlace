package org.care.presentation.sitter;

import org.care.dto.SitterNAJobDTO;
import org.care.service.SitterService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class ListSitterJobsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        int userId = (int) session.getAttribute("UserId");
        List<SitterNAJobDTO> sitterNAJobDTOS = SitterService.getNAJobsList(userId);

        req.setAttribute("JobsList", sitterNAJobDTOS);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/jsp/sitter/listjobs.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("post method of " + ListSitterJobsServlet.class.getName() + " called!");
    }
}
