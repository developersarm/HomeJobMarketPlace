package org.care.presentation.sitter;

import org.care.context.MyApplicationContext;
import org.care.dto.SitterAppliedJobDTO;
import org.care.service.SitterService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class SitterHomePageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        List<SitterAppliedJobDTO> sitterJobList;
        sitterJobList = SitterService.getAppliedJobsList(MyApplicationContext.get().getMember().getId());
        req.setAttribute("JobsList", sitterJobList);

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/jsp/sitter/home.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        int userId = (int) session.getAttribute("UserId");

        List<SitterAppliedJobDTO> sitterJobList;
        sitterJobList = SitterService.getAppliedJobsList(userId);
        req.setAttribute("JobsList", sitterJobList);

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/jsp/sitter/home.jsp");
        requestDispatcher.forward(req, resp);
    }
}
