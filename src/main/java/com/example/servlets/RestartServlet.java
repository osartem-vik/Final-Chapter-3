package com.sewingtest.servlets;

import com.sewingtest.model.TestState;
import com.sewingtest.util.TestLogic;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/restart")
public class RestartServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        TestState state = (TestState) session.getAttribute("testState");
        state.setCurrentQuestionIndex(0);
        state.setScore(0);
        state.setWrongAnswers(0);
        state.setLastAnswerCorrect(true);
        state.setAttempts(state.getAttempts() + 1);
        TestLogic.selectRandomQuestions(state); // Новий набір питань
        session.setAttribute("testState", state);
        session.setAttribute("attempts", state.getAttempts());
        resp.sendRedirect("test.jsp");
    }
}
