package com.vikash.lms.dao;

import java.sql.*;
import java.util.*;

import com.vikash.lms.model.User;
import com.vikash.lms.util.DBConnection;

public class UserDAO {

    public void addStudent(User user) {

        String sql = "INSERT INTO users(name,email,password,role) VALUES(?,?,?,'STUDENT')";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllStudents() {

        List<User> students = new ArrayList<>();

        String sql = "SELECT id,name,email FROM users WHERE role='STUDENT'";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                User u = new User();

                u.setId(rs.getInt("id"));
                u.setName(rs.getString("name"));
                u.setEmail(rs.getString("email"));

                students.add(u);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return students;
    }

    public User validateUser(String email, String password) {

        User user = null;

        String sql = "SELECT * FROM users WHERE email=? AND password=?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                user = new User();

                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setRole(rs.getString("role"));

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }
}