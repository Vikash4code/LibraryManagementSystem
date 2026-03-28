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
                    + "due_date DATE, "
                    + "return_date DATE, "
                    + "status VARCHAR(50), "
                    + "FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE CASCADE, "
                    + "FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE"
                    + ")");

            // Create fines table
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS fines ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY, " 
                    + "transaction_id INT NOT NULL, "
                    + "user_id INT NOT NULL, "
                    + "book_id INT NOT NULL, "
                    + "amount DECIMAL(10,2) NOT NULL, "
                    + "days_overdue INT NOT NULL, "
                    + "fine_date DATE NOT NULL, "
                    + "status VARCHAR(20) DEFAULT 'PENDING', "
                    + "FOREIGN KEY (transaction_id) REFERENCES transactions(id) ON DELETE CASCADE, "
                    + "FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE, "
                    + "FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE CASCADE"
                    + ")");

            // Insert default admin if not exists
            stmt.executeUpdate("INSERT IGNORE INTO users (name, email, password, role) VALUES "
                    + "('Administrator', 'admin@library.com', 'admin123', 'ADMIN')");

            // Add more admins here (optional)
            stmt.executeUpdate("INSERT IGNORE INTO users (name, email, password, role) VALUES "
                    + "('Admin Manager', 'manager@library.com', 'manager123', 'ADMIN')");

            stmt.executeUpdate("INSERT IGNORE INTO users (name, email, password, role) VALUES "
                    + "('Head Librarian', 'librarian@library.com', 'librarian123', 'ADMIN')");

            System.out.println("DBInitListener: default tables checked and default admins ensured.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
