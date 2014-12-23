package com.riskified.samples.notificationServer.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.riskified.RiskifedError;
import com.riskified.notifications.Notification.NotificationOrder;
import com.riskified.notifications.NotificationHandler;

public class NotificationServlet extends HttpServlet {

    private NotificationHandler formatter;

    public NotificationServlet(String authKey) throws  RiskifedError {
        formatter = new NotificationHandler(authKey);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
            IOException {
        PrintWriter out = resp.getWriter();
        try {
            NotificationOrder notification = formatter.parseServletPostRequest(req).getOrder();

            out.println("<HTML><BODY>Merchant Received Notification For Order " + notification.getId()
                    + " with status " + notification.getStatus() + " and description " + notification.getDescription()
                    + "</BODY></HTML>");

        } catch (Exception e) {
            resp.sendError(500, "<HTML><BODY>Merchant couldn't parse notification message</BODY></HTML>");
            e.printStackTrace();
        }

    }

}