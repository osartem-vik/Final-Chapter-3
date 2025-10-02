package com.example.sewingtest.servlets;

import com.example.sewingtest.model.TestState;
import com.example.util.TestLogic;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "StartServlet", urlPatterns = {"/start"})
public class StartServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/welcome.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String fullName = request.getParameter("fullName");
        String groupNumber = request.getParameter("groupNumber");

        if (fullName == null || fullName.trim().isEmpty() || groupNumber == null || groupNumber.trim().isEmpty()) {
            request.setAttribute("error", "Будь ласка, введіть ім'я та номер групи.");
            request.getRequestDispatcher("/welcome.jsp").forward(request, response);
            return;
        }

        HttpSession session = request.getSession();
        TestState testState = new TestState(groupNumber, fullName);
        TestLogic.selectRandomQuestions(testState);

        session.setAttribute("testState", testState);
        session.setAttribute("attempts", 1);

        response.sendRedirect("test");
    }
}