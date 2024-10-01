import java.util.HashMap;

public class Library {
    private HashMap<String, Book> books;

    public Library()
    {
        this.books = new HashMap<String, Book>();
    }

    public void addBook(String isbn,Book book)
    {
        books.put(isbn, book);
    }

    public void removeBook(String isbn)
    {
        books.remove(isbn);
        
    }

    public Book getBook(String isbn)
    {
        if(this.books.get(isbn) != null){
            return this.books.get(isbn);
        }
        
        return null;
    }

    public HashMap<String, Book> getBooks() {
        return books;
    }

    public String allBooks() {
        StringBuilder sb = new StringBuilder();
        sb.append("Library Collection:\n");
        for (Book book : books.values()) {
            sb.append(book.toString()).append("\n");
        }
        return sb.toString();
    }
    
}
