import java.util.Objects;

public class Book {
    private String title;
    private String genre;
    private int pages;
    private String author;
    private String publisher;
    private Integer isbn;

    public Book(String title, String genre, int pages, String author, String publisher, Integer isbn)
    {
        this.title = title;
        this.genre = genre;
        this.pages = pages;
        this.author = author;
        this.publisher = publisher;
        this.isbn = isbn;
    }

    public String getTitle()
    {
        return this.title;
    }

    public String getGenre()
    {
        return this.genre;
    }

    public int getPages()
    {
        return this.pages;
    }

    public String getAuthor()
    {
        return this.author;
    }

    public String getPublisher()
    {
        return this.publisher;
    }

    public Integer getISBN()
    {
        return this.isbn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return isbn.equals(book.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn);
    }

    public String toString()
    {
        return ("\nTitle: "+this.title+"\nGenre: "+this.genre+"\nPages: "+this.pages+"\nAuthor: "+this.author+"\nPublisher: "+this.publisher+"\nInternational Standard Book Number(ISBN): "+this.isbn+"\n");
    }

}
