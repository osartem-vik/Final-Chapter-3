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

@WebServlet("/start")
public class StartServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String groupNumber = req.getParameter("groupNumber");
        String fullName = req.getParameter("fullName");

        TestState state = new TestState();
        state.setGroupNumber(groupNumber);
        state.setFullName(fullName);
        state.setAttempts((Integer) session.getAttribute("attempts") == null ? 1 : (Integer) session.getAttribute("attempts") + 1);
        TestLogic.selectRandomQuestions(state); // Вибираємо рандомні питання

        session.setAttribute("testState", state);
        session.setAttribute("attempts", state.getAttempts());

        resp.sendRedirect("test.jsp");
    }
}