import java.time.LocalDate;

public class Transaction {
    private int userId;
    private int bookId;
    private LocalDate issueDate;
    private LocalDate returnDate;

    public Transaction(int userId,int bookId){
        this.userId=userId;
        this.bookId=bookId;
        this.issueDate=LocalDate.now();
        this.returnDate=null;
    }
    public void returnBook(){
        this.returnDate=LocalDate.now();
    }
    public String toString(){
        return "User :"+ userId+ ", Book :"+bookId+", Issued :"+issueDate +",  Returned : "+(returnDate==null ? "Not Returned" : returnDate);
    }

}
