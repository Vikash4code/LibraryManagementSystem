package com.vikash.lms.controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import com.vikash.lms.dao.UserDAO;
import com.vikash.lms.model.User;

@WebServlet("/students")
public class StudentListServlet extends HttpServlet{

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
                         throws ServletException,IOException{

        UserDAO dao=new UserDAO();

        List<User> students=dao.getAllStudents();

        request.setAttribute("students",students);

        RequestDispatcher rd=request.getRequestDispatcher("/admin/students.jsp");

        rd.forward(request,response);
    }
}