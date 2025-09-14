import java.util.*;
public class LibraryManagementSystem {
    private static List<Book> books=new ArrayList<>();
    private static List<User> users=new ArrayList<>();
    private static List<Transaction> transactions = new ArrayList<>();
    private static Scanner sc= new Scanner(System.in);

    public static void main(String[] args) {
        users.add(new User(1,"vikash"));
        users.add(new User(2,"Thakur"));

        while(true){
            System.out.println("\n==========Library Menu=======");
            System.out.println("1.Add Book");
            System.out.println("2. View Book");
            System.out.println("3.Issue Book");
            System.out.println("4.Return Book");
            System.out.println("5.Exit");
            System.out.println("Enter your choice :");
            int choice =sc.nextInt();

            switch(choice){
                case 1->addBook();
                case 2->viewBooks();
                case 3->issueBook();
                case 4->returnBook();
                case 5->{
                    System.out.println("Exiting....");
                    return;
                }
                default ->System.out.println("Invalid Choice");
            }
        }
    }
    private static void addBook(){
        System.out.println("Enter Book Id : ");
        int id =sc.nextInt();
        sc.nextLine();
        System.out.println("Enter Title :");
        String title=sc.nextLine();
        System.out.println("Enter author :");
        String author=sc.nextLine();

        books.add(new Book(id,title,author));
        System.out.println("Book Added...");
    }
    private static void viewBooks(){
        for(Book b : books){
        System.out.println(b);
        }
    }

    private static void  issueBook(){
        System.out.println("Enter book id :");
        int uid=sc.nextInt();
        System.out.println("Enter book name: ");
        int bid=sc.nextInt();

        for(Book b : books){
            if(b.getId()==bid && !b.isIssued()){
                b.setIssued(true);
                transactions.add(new Transaction(uid, bid));
                System.out.println("Book issued");
                return;
            }
        }
        System.out.println("Book not available");
    }

    private static void returnBook(){
        System.out.println("Enter book  id :");
        int bid=sc.nextInt();
        
        for(Book b : books){
            if(b.getId()== bid && b.isIssued()){
                b.setIssued(false);
                for(Transaction t : transactions){
                    if(t.toString().contains("Book "+bid)&& t.toString().contains("Not returned")){
                        t.returnBook();
                    }     
               }
               System.out.println("Book returned..");
               return;
            }
        }
        System.out.println("Book was not issued");
    }
}
