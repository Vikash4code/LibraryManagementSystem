package com.vikash.lms.controller;

import java.io.IOException;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import com.vikash.lms.dao.UserDAO;
import com.vikash.lms.model.User;

@WebServlet("/addStudent")
public class AddStudentServlet extends HttpServlet{

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
                          throws ServletException,IOException{

        String name=request.getParameter("name");
        String email=request.getParameter("email");
        String password=request.getParameter("password");

        User user=new User(name,email,password,"STUDENT");

        UserDAO dao=new UserDAO();

        dao.addStudent(user);

        response.sendRedirect(request.getContextPath()+"/students");
    }
} 