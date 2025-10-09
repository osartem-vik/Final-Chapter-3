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

@WebServlet(name = "TestServlet", urlPatterns = {"/test"})
public class TestServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("TestServlet: doGet called at " + new java.util.Date());
        HttpSession session = request.getSession();
        TestState testState = (TestState) session.getAttribute("testState");

        if (testState == null) {
            System.out.println("TestServlet: testState is null, redirecting to start");
            response.sendRedirect("start");
            return;
        }

        String imagePath = TestLogic.getImage(testState);
        System.out.println("TestServlet: Image path for current question: " + imagePath);
        request.getRequestDispatcher("/test.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        TestState testState = (TestState) session.getAttribute("testState");

        if (testState == null) {
            response.sendRedirect("start");
            return;
        }

        String answer = request.getParameter("answer");
        if (answer != null && !answer.trim().isEmpty()) {
            TestLogic.processAnswer(testState, answer);
        }

        if (testState.getCurrentQuestionIndex() >= 5 || testState.getWrongAnswers() >= 2) {
            response.sendRedirect("result.jsp");
        } else {
            request.getRequestDispatcher("/test.jsp").forward(request, response);
        }
    }
}