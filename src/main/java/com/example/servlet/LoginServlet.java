package com.example.servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final String USERNAME1 = "user";
    private static final String USERNAME2 = "admin";
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Object user = req.getSession().getAttribute("user");
        if (user != null) {
            RequestDispatcher dispatcher = req.getRequestDispatcher("/user/hello.jsp");
            dispatcher.include(req, resp);
        } else {
            RequestDispatcher dispatcher = req.getRequestDispatcher("/login.jsp");
            dispatcher.include(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        if (login == null || login.isBlank() || password == null || password.isBlank()) {
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
            return;
        }

        boolean isValidUser = login.equals(USERNAME1)||login.equals(USERNAME2);

        if (isValidUser) {
            HttpSession session = req.getSession();
            session.setAttribute("user", login);
            req.getRequestDispatcher("index.jsp").forward(req, resp);
            resp.sendRedirect(req.getContextPath() + "/user/hello.jsp");
        } else {
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }
}
