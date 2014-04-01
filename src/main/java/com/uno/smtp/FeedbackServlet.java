package com.uno.smtp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FeedbackServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        execute(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        execute(req, resp);
    }

    protected void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String from = req.getParameter("from");
        String subject = req.getParameter("subject");
        String message = req.getParameter("message");
        try {
            Sender.sendEmail(from, subject, message);
            resp.getWriter().write("{status: 'OK'}");
        } catch (Exception e) {
            resp.getWriter().write("{status:'failed'}");
        }



    }
}
