package com.vikash.lms.dao;

import com.vikash.lms.model.Fine;
import com.vikash.lms.util.DBConnection;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FineDAO {

    // Calculate fines for overdue books (run this periodically or on demand)
    public void calculateFines() {
        String overdueQuery = "SELECT t.id, t.user_id, t.book_id, DATEDIFF(CURDATE(), t.due_date) as days_overdue " +
                             "FROM transactions t WHERE t.status = 'ISSUED' AND t.due_date < CURDATE()";

        String checkExistingFine = "SELECT id FROM fines WHERE transaction_id = ? AND status = 'PENDING'";

        String insertFine = "INSERT INTO fines (transaction_id, user_id, book_id, amount, days_overdue, fine_date, status) " +
                           "VALUES (?, ?, ?, ?, ?, CURDATE(), 'PENDING')";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement psOverdue = conn.prepareStatement(overdueQuery);
             ResultSet rs = psOverdue.executeQuery()) {

            while (rs.next()) {
                int transactionId = rs.getInt("id");
                int userId = rs.getInt("user_id");
                int bookId = rs.getInt("book_id");
                int daysOverdue = rs.getInt("days_overdue");

                // Calculate fine amount (₹10 per day overdue)
                BigDecimal fineAmount = BigDecimal.valueOf(daysOverdue * 10.0);

                // Check if fine already exists for this transaction
                try (PreparedStatement psCheck = conn.prepareStatement(checkExistingFine)) {
                    psCheck.setInt(1, transactionId);
                    try (ResultSet rsCheck = psCheck.executeQuery()) {
                        if (!rsCheck.next()) {
                            // No existing fine, create new one
                            try (PreparedStatement psInsert = conn.prepareStatement(insertFine)) {
                                psInsert.setInt(1, transactionId);
                                psInsert.setInt(2, userId);
                                psInsert.setInt(3, bookId);
                                psInsert.setBigDecimal(4, fineAmount);
                                psInsert.setInt(5, daysOverdue);
                                psInsert.executeUpdate();
                            }
                        }
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get all fines
    public List<Fine> getAllFines() {
        List<Fine> fines = new ArrayList<>();
        String query = "SELECT f.*, u.name as user_name, b.title as book_title " +
                      "FROM fines f " +
                      "JOIN users u ON f.user_id = u.id " +
                      "JOIN books b ON f.book_id = b.id " +
                      "ORDER BY f.fine_date DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Fine fine = new Fine();
                fine.setId(rs.getInt("id"));
                fine.setTransactionId(rs.getInt("transaction_id"));
                fine.setUserId(rs.getInt("user_id"));
                fine.setBookId(rs.getInt("book_id"));
                fine.setAmount(rs.getBigDecimal("amount"));
                fine.setDaysOverdue(rs.getInt("days_overdue"));
                fine.setFineDate(rs.getDate("fine_date"));
                fine.setStatus(rs.getString("status"));
                fines.add(fine);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return fines;
    }

    // Get fines for a specific user
    public List<Fine> getFinesByUser(int userId) {
        List<Fine> fines = new ArrayList<>(); 
        String query = "SELECT f.*, b.title as book_title FROM fines f " +
                      "JOIN books b ON f.book_id = b.id " +
                      "WHERE f.user_id = ? ORDER BY f.fine_date DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Fine fine = new Fine();
                    fine.setId(rs.getInt("id"));
                    fine.setTransactionId(rs.getInt("transaction_id"));
                    fine.setUserId(rs.getInt("user_id"));
                    fine.setBookId(rs.getInt("book_id"));
                    fine.setAmount(rs.getBigDecimal("amount"));
                    fine.setDaysOverdue(rs.getInt("days_overdue"));
                    fine.setFineDate(rs.getDate("fine_date"));
                    fine.setStatus(rs.getString("status"));
                    fines.add(fine);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return fines;
    }

    // Mark fine as paid
    public boolean markFineAsPaid(int fineId) {
        String query = "UPDATE fines SET status = 'PAID' WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, fineId);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get total pending fines for a user
    public BigDecimal getTotalPendingFines(int userId) {
        String query = "SELECT SUM(amount) as total FROM fines WHERE user_id = ? AND status = 'PENDING'";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    BigDecimal total = rs.getBigDecimal("total");
                    return total != null ? total : BigDecimal.ZERO;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return BigDecimal.ZERO;
    }
}