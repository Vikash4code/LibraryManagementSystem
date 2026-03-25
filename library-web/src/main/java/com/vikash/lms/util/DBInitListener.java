package com.vikash.lms.util;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.sql.Connection;
import java.sql.Statement;

@WebListener
public class DBInitListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try (Connection conn = DBConnection.getConnection(); Statement stmt = conn.createStatement()) {
            // Create users table
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS users ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "name VARCHAR(100) NOT NULL, "
                    + "email VARCHAR(150) NOT NULL UNIQUE, "
                    + "password VARCHAR(100) NOT NULL, "
                    + "role VARCHAR(20) NOT NULL"
                    + ")");

            // Create books table
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS books ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "title VARCHAR(255) NOT NULL, "
                    + "author VARCHAR(255) NOT NULL, "
                    + "isbn VARCHAR(100), "
                    + "category VARCHAR(100), "
                    + "total_copies INT NOT NULL DEFAULT 1, "
                    + "available_copies INT NOT NULL DEFAULT 1"
                    + ")");

            // Create transactions table
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS transactions ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "book_id INT NOT NULL, "
                    + "user_id INT NOT NULL, "
                    + "issue_date DATE, "
                    + "return_date DATE, "
                    + "status VARCHAR(50), "
                    + "FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE CASCADE, "
                    + "FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE"
                    + ")");

            // Insert default admin if not exists
            stmt.executeUpdate("INSERT INTO users (name, email, password, role) "
                    + "SELECT 'Administrator', 'admin@library.com', 'admin123', 'ADMIN' "
                    + "FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM users WHERE email='admin@library.com')");

            System.out.println("DBInitListener: default tables checked and default admin ensured.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
