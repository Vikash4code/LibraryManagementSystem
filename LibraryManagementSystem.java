import java.util.*;
import java.sql.*;

public class LibraryManagementSystem {
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        while (true) {
            System.out.println("\n==========Library Menu=======");
            System.out.println("1.Add Book");
            System.out.println("2.View Book");
            System.out.println("3.Issue Book");
            System.out.println("4.Return Book");
            System.out.println("5.Exit");
            System.out.println("Enter your choice :");
            int choice = sc.nextInt();

            switch (choice) {
                case 1 -> addBook();
                case 2 -> viewBooks();
                case 3 -> issueBook();
                case 4 -> returnBook();
                case 5 -> {
                    System.out.println("Exiting....");
                    return;
                }
                default -> System.out.println("Invalid Choice");
            }
        }
    }

    private static void addBook() {
        try {
            System.out.println("Enter Book Id : ");
            int id = sc.nextInt();
            sc.nextLine();
            System.out.println("Enter Title :");
            String title = sc.nextLine();
            System.out.println("Enter author :");
            String author = sc.nextLine();

            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO books VALUES (?, ?, ?, false)");
            ps.setInt(1, id);
            ps.setString(2, title);
            ps.setString(3, author);

            ps.executeUpdate();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void viewBooks() {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement st = con.prepareStatement("select * from books");
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                System.out.println("Book id : " + rs.getInt(1) + "|" + "Title : " + rs.getString(2) + "|" + "Author : "
                        + rs.getString(3) + "|" + rs.getBoolean(4));
            }
            con.close();
            ;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void issueBook() {
        try {
            System.out.println("Enter user id :");
            int uid = sc.nextInt();
            System.out.println("Enter book id: ");
            int bid = sc.nextInt();

            Connection con = DBConnection.getConnection();
            PreparedStatement check = con.prepareStatement("Select issued form books where book_id=?");
            check.setInt(1, bid); // giving bood id to search
            ResultSet rs = check.executeQuery();

            if (rs.next() && !rs.getBoolean("issued")) {
                PreparedStatement updateBook = con.prepareStatement("update books set issued=true where book_id=?");
                updateBook.setInt(1, bid);
                updateBook.executeUpdate();

                PreparedStatement updateTransaction = con.prepareStatement(
                        "Insert into transaction(user_id,book_id,issue_date,returned)values(?,?,curdate(),false)");
                updateTransaction.setInt(1, bid);
                updateTransaction.setInt(2, uid);
                updateTransaction.executeUpdate();

                System.out.println("Book issued");
            } else {
                System.out.println("Book not available");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void returnBook() {
        try {
            System.out.println("Enter book id:");
            int bid = sc.nextInt();

            Connection con = DBConnection.getConnection();

            // 2 CHECK: was the book issued?
            PreparedStatement checkTxn = con.prepareStatement(
                    "SELECT * FROM transactions WHERE book_id=? AND returned=false");
            checkTxn.setInt(1, bid);
            ResultSet rs = checkTxn.executeQuery();

            if (!rs.next()) {
                System.out.println("Book was not issued");
                con.close();
                return;
            }

            // UPDATE books table
            PreparedStatement updateBook = con.prepareStatement(
                    "UPDATE books SET issued=false WHERE book_id=?");
            updateBook.setInt(1, bid);
            updateBook.executeUpdate();

            // UPDATE transactions table
            PreparedStatement updateTxn = con.prepareStatement(
                    "UPDATE transactions SET returned=true WHERE book_id=? AND returned=false");
            updateTxn.setInt(1, bid);
            updateTxn.executeUpdate();

            System.out.println("Book returned");
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
