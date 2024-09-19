import java.util.HashMap;

public class Library {
    private HashMap<Integer, Book> books;

    public Library()
    {
        this.books = new HashMap<Integer, Book>();
    }

    public void addBook(Integer isbn,Book book)
    {
        books.put(isbn, book);
    }

    public void removeBook(Integer isbn)
    {
            books.remove(isbn);
        
    }

    public Book getBook(Integer isbn)
    {
        if(this.books.get(isbn) != null){
            return this.books.get(isbn);
        }
        
        return null;
    }

    public HashMap<Integer, Book> getBooks() {
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
