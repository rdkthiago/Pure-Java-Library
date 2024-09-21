import java.util.ArrayList;

public class User {
    private String username;
    private String id;
    // private int password;
    private ArrayList<Book> borrowedBooks;

    public User(String username, String id)
    {
        this.username = username;
        this.id = id;
        // this.password = password.hashCode(); // just for test.
        this.borrowedBooks = new ArrayList<Book>();
    }

    public boolean borrowBook(Book b)
    {
        if(b != null)
            return this.borrowedBooks.add(b);
        return false;
        
    }

    public Book returnBook(String isbn)
    {
        for (int i = 0; i < borrowedBooks.size(); i++) {
            if(borrowedBooks.get(i).getISBN().equals(isbn)){
                Book temporaryBook = borrowedBooks.get(i);
                borrowedBooks.remove(i);
                return temporaryBook;
            }
        }
        return null;
    }

    public String getUserName()
    {
        return this.username;
    }

    public String getUserID()
    {
        return this.id;
    }

    public String borrowedBooksList()
    {
        return borrowedBooks.toString();
    }
    
}
