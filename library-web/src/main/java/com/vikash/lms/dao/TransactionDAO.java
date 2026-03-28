package com.vikash.lms.dao;

import com.vikash.lms.model.Transaction;
import com.vikash.lms.util.DBConnection;

import java.sql.*;
import java.util.*;

public class TransactionDAO {

    public boolean issueBook(int userId, int bookId) {

        boolean success = false;

        String checkDuplicate = "SELECT * FROM transactions WHERE user_id=? AND book_id=? AND status='ISSUED'";

        String checkAvailability = "SELECT total_copies, available_copies FROM books WHERE id=?";

        String insertTransaction = "INSERT INTO transactions(user_id,book_id,issue_date,due_date,status) VALUES(?,?,CURDATE(),DATE_ADD(CURDATE(),INTERVAL 7 DAY),'ISSUED')";

        String updateBook = "UPDATE books SET available_copies = available_copies - 1 WHERE id=? AND available_copies > 0";

        try (Connection conn = DBConnection.getConnection()) {
  
            conn.setAutoCommit(false);

            // Check for duplicate issue
            try (PreparedStatement ps1 = conn.prepareStatement(checkDuplicate)) {
                ps1.setInt(1, userId);
                ps1.setInt(2, bookId);
                
                try (ResultSet rs1 = ps1.executeQuery()) {
                    if (rs1.next()) {
                        conn.rollback();
                        System.out.println("User already has this book issued");
                        return false;
                    }
                }
            }

            // Check book availability - validate available_copies > 0 and available_copies <= total_copies
            try (PreparedStatement ps2 = conn.prepareStatement(checkAvailability)) {
                ps2.setInt(1, bookId);
                
                try (ResultSet rs2 = ps2.executeQuery()) {
                    if (rs2.next()) {
                        int totalCopies = rs2.getInt("total_copies");
                        int availableCopies = rs2.getInt("available_copies");
                        
                        // Validate: available_copies must be > 0 and <= total_copies
                        if (availableCopies <= 0) {
                            conn.rollback();
                            System.out.println("Book not available - no copies left");
                            return false;
                        }
                        
                        if (availableCopies > totalCopies) {
                            conn.rollback();
                            System.out.println("ERROR: Available copies exceed total copies!");
                            return false;
                        }
                    } else {
                        conn.rollback();
                        System.out.println("Book not found");
                        return false;
                    }
                }
            }

            // Insert transaction
            try (PreparedStatement ps3 = conn.prepareStatement(insertTransaction)) {
                ps3.setInt(1, userId);
                ps3.setInt(2, bookId);
                ps3.executeUpdate();
            }

            // Update book availability (with validation)
            try (PreparedStatement ps4 = conn.prepareStatement(updateBook)) {
                ps4.setInt(1, bookId);
                int rowsUpdated = ps4.executeUpdate();
                
                if (rowsUpdated == 0) {
                    conn.rollback();
                    System.out.println("Failed to update book availability");
                    return false;
                }
            }

            conn.commit();
            success = true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return success;
    }

    // Return book method
    public boolean returnBook(int transactionId) {
        boolean success = false;

        String getTransaction = "SELECT book_id FROM transactions WHERE id = ? AND status = 'ISSUED'";
        String getBookCopies = "SELECT total_copies, available_copies FROM books WHERE id = ?";
        String updateTransaction = "UPDATE transactions SET status = 'RETURNED', return_date = CURDATE() WHERE id = ?";
        String updateBook = "UPDATE books SET available_copies = available_copies + 1 WHERE id = ? AND available_copies < total_copies";

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            int bookId = -1;

            // Get book ID from transaction
            try (PreparedStatement ps1 = conn.prepareStatement(getTransaction)) {
                ps1.setInt(1, transactionId);
                try (ResultSet rs = ps1.executeQuery()) {
                    if (rs.next()) {
                        bookId = rs.getInt("book_id");
                    } else {
                        conn.rollback();
                        System.out.println("Transaction not found or already returned");
                        return false;
                    }
                }
            }

            // Check book copy counts before incrementing
            try (PreparedStatement ps2 = conn.prepareStatement(getBookCopies)) {
                ps2.setInt(1, bookId);
                try (ResultSet rs = ps2.executeQuery()) {
                    if (rs.next()) {
                        int totalCopies = rs.getInt("total_copies");
                        int availableCopies = rs.getInt("available_copies");
                        
                        if (availableCopies >= totalCopies) {
                            conn.rollback();
                            System.out.println("ERROR: Available copies already equals or exceeds total copies!");
                            return false;
                        }
                    } else {
                        conn.rollback();
                        System.out.println("Book not found");
                        return false;
                    }
                }
            }

            // Update transaction status
            try (PreparedStatement ps3 = conn.prepareStatement(updateTransaction)) {
                ps3.setInt(1, transactionId);
                ps3.executeUpdate();
            }

            // Update book availability (with validation to ensure available_copies <= total_copies)
            try (PreparedStatement ps4 = conn.prepareStatement(updateBook)) {
                ps4.setInt(1, bookId);
                int rowsUpdated = ps4.executeUpdate();
                
                if (rowsUpdated == 0) {
                    conn.rollback();
                    System.out.println("Failed to update book availability or validation failed");
                    return false;
                }
            }

            conn.commit();
            success = true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return success;
    }

    public List<Transaction> getAllTransactions() {

        List<Transaction> list = new ArrayList<>();

        String sql = "SELECT * FROM transactions";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Transaction t = new Transaction();

                t.setId(rs.getInt("id"));
                t.setUserId(rs.getInt("user_id"));
                t.setBookId(rs.getInt("book_id"));
                t.setIssueDate(rs.getDate("issue_date"));
                t.setDueDate(rs.getDate("due_date"));
                t.setStatus(rs.getString("status"));

                list.add(t);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}