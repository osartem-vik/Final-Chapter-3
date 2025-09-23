package com.example.servlets;

import com.example.util.TestLogic;
import com.example.model.TestState;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/test")
public class TestServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        TestState state = (TestState) session.getAttribute("testState");
        String answer = req.getParameter("answer");

        TestLogic.processAnswer(state, answer);
        session.setAttribute("testState", state);

        if (state.getWrongAnswers() >= 2 || state.getCurrentQuestionIndex() >= 5) {
            resp.sendRedirect("result.jsp");
        } else {
            resp.sendRedirect("test.jsp");
        }
    }
}
