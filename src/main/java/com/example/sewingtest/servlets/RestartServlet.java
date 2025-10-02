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

@WebServlet(name = "RestartServlet", urlPatterns = {"/restart"})
public class RestartServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        TestState testState = (TestState) session.getAttribute("testState");

        if (testState == null) {
            response.sendRedirect("start");
            return;
        }

        testState.setCurrentQuestionIndex(0);
        testState.setScore(0);
        testState.setWrongAnswers(0);
        testState.getUserAnswers().clear();
        testState.getCorrectAnswers().clear();
        testState.setAttempts(testState.getAttempts() + 1);
        TestLogic.selectRandomQuestions(testState);

        response.sendRedirect("test");
    }
}