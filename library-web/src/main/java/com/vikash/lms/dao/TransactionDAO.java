package com.vikash.lms.dao;

import com.vikash.lms.model.Transaction;
import com.vikash.lms.util.DBConnection;

import java.sql.*;
import java.util.*;

public class TransactionDAO {

    public boolean issueBook(int userId, int bookId) {

        boolean success = false;

        String checkDuplicate = "SELECT * FROM transactions WHERE user_id=? AND book_id=? AND status='ISSUED'";

        String checkAvailability = "SELECT available_copies FROM books WHERE id=?";

        String insertTransaction = "INSERT INTO transactions(user_id,book_id,issue_date,due_date,status) VALUES(?,?,CURDATE(),DATE_ADD(CURDATE(),INTERVAL 7 DAY),'ISSUED')";

        String updateBook = "UPDATE books SET available_copies = available_copies - 1 WHERE id=?";

        try (Connection conn = DBConnection.getConnection()) {

            conn.setAutoCommit(false);

            PreparedStatement ps1 = conn.prepareStatement(checkDuplicate);
            ps1.setInt(1, userId);
            ps1.setInt(2, bookId);

            ResultSet rs1 = ps1.executeQuery();

            if (rs1.next()) {
                conn.rollback();
                return false;
            }

            PreparedStatement ps2 = conn.prepareStatement(checkAvailability);
            ps2.setInt(1, bookId);

            ResultSet rs2 = ps2.executeQuery();

            if (rs2.next()) {

                int copies = rs2.getInt("available_copies");

                if (copies <= 0) {
                    conn.rollback();
                    return false;
                }
            }

            PreparedStatement ps3 = conn.prepareStatement(insertTransaction);

            ps3.setInt(1, userId);
            ps3.setInt(2, bookId);

            ps3.executeUpdate();

            PreparedStatement ps4 = conn.prepareStatement(updateBook);

            ps4.setInt(1, bookId);

            ps4.executeUpdate();

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