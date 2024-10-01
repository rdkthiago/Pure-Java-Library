import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class App {
    private static Scanner sc = new Scanner(System.in);
    private static Library lib = new Library();
    private static User user = new User("John", "1A");
    private static FileWriter writer;
    private static File userBooks = new File("UserBooks.txt");
    private static File libBooks = new File("LibBooks.txt");
    
    private static void libMenu() throws IOException
    {
        int opc = 0;
        do{
            System.out.println("[1] Add a book [2] Remove a book [3] Show all books [-1] Back");
            opc = sc.nextInt();
            sc.nextLine();
            switch(opc){
                case 1:
                    System.out.println("Write the title: ");
                    String title = sc.nextLine();

                    System.out.println("Write the genre: ");
                    String genre = sc.next();
                    
                    System.out.println("Write the number of pages: ");
                    int pages = sc.nextInt();
                    sc.nextLine();

                    System.out.println("Write the author name: ");
                    String author = sc.nextLine();

                    System.out.println("Write the publisher name: ");
                    String pub = sc.next();

                    sc.nextLine();
                    System.out.println("Write the ISBN (International Standard Book Number):");
                    while (!sc.hasNextInt()) {
                        System.out.println("Invalid ISBN. Please enter a valid number:");
                        sc.next();
                    }
                    String ISBN = sc.next();                    
                    Book b = new Book(title, genre, pages, author, pub, ISBN);
                    lib.addBook(ISBN, b);
                    writeFile(b.toString(), libBooks);
                    break;
                case 2:
                    
                    System.out.println("Write the ISBN (International Standard Book Number) of the book:");
                    lib.removeBook(sc.next());
                    updateLibraryFile();
                    break;
                case 3:
                    System.out.println(lib.allBooks());
                    break;
            }
        }while(opc != -1);
    }

    public static void userMenu() throws IOException
    {
        int opc=0;

        do{
            System.out.println("[1] Borrow a book [2] Return a borrowed book [3] Show borrowed books [-1] Back");
            opc = sc.nextInt();
            sc.nextLine();

            switch (opc) {
                case 1:
                    System.out.println("Choose a Book by its ISBN:\n"+lib.allBooks());
                    Book b = lib.getBook(sc.next());
                    if(b != null)
                    {
                        if(user.borrowBook(b)) {
                            lib.removeBook(b.getISBN());
                            writeFile(b.toString(), userBooks);
                            System.out.println("Borrowed with success!");
                            updateLibraryFile();
                            
                        }
                        else
                            System.out.println("Failed to Borrow!");
                    }
                    else{
                        System.out.println("Invalid ISBN!");
                    }

                    break;
            
                case 2:
                    System.out.println("Which book are you returning?");
                    readFile(userBooks);
                    System.out.println("\nEnter it's ISBN: ");
                    Book returned = user.returnBook(sc.nextLine());
                    if(returned != null){
                        lib.addBook(returned.getISBN(), returned);
                        updateUserBooksFile();
                        updateLibraryFile();
                    }        
                    else
                        System.out.println("Cannot return! Invalid Input.");
                    break;
                case 3:
                    readFile(userBooks);
                    break;
                default:
                    System.out.println("returning...");
                    break;
                
            }
        }while(opc != -1);
    }

    public static void menu() throws IOException
    {
        int opc = 0;
        
        do{
            System.out.println("[1] Access library menu [2] Access user menu [3] quit");
            opc = sc.nextInt();

            if(opc == 1)
                libMenu();
            else if (opc == 2) {
                userMenu();
            }

        }while(opc != 3);
        
        
    }

    public static void readFile(File txt) throws IOException {
        if (!txt.exists()) {
            System.out.println("File not found, creating a new file: " + txt.getName());
            txt.createNewFile();
            return;
        }
    
        Scanner reader = new Scanner(txt);
        while (reader.hasNextLine()) {
            String line = reader.nextLine();
            System.out.println(line);
        }
        reader.close();
    }
    

    public static void writeFile(String content, File txt) throws IOException
    {
        writer = new FileWriter(txt, true);
        writer.write(content);
        writer.write(System.lineSeparator());
        writer.close();

    }

    public static void updateLibraryFile() throws IOException {
        writer = new FileWriter(libBooks, false);
        for (Book book : lib.getBooks().values()) { 
            writer.write(book.toString());
            writer.write(System.lineSeparator());
        }
        writer.close();
    }
    
    public static void updateUserBooksFile() throws IOException {
        writer = new FileWriter(userBooks, false);
        writer.write(user.borrowedBooksList());
        writer.close();
    }
    

    public static void loadLibraryBooks() throws IOException {
        Scanner reader = new Scanner(libBooks);
        
        while (reader.hasNextLine()) {
            // Leia o título
            String titleLine = reader.nextLine();
            String title = titleLine.split(": ")[1].trim(); // Pega o valor após "Title: "
            
            // Leia o gênero
            String genreLine = reader.nextLine();
            String genre = genreLine.split(": ")[1].trim(); // Pega o valor após "Genre: "
            
            // Leia o número de páginas
            String pagesLine = reader.nextLine();
            int pages = Integer.parseInt(pagesLine.split(": ")[1].replace(",", "").trim()); // Remove a vírgula e espaços
            
            // Leia o autor
            String authorLine = reader.nextLine();
            String author = authorLine.split(": ")[1].trim(); // Pega o valor após "Author: "
            
            // Leia a editora
            String publisherLine = reader.nextLine();
            String publisher = publisherLine.split(": ")[1].trim(); // Pega o valor após "Publisher: "
            
            // Leia o ISBN
            String isbnLine = reader.nextLine();
            String isbn = isbnLine.split(": ")[1].trim(); // Pega o valor após "ISBN: "
            
            // Adiciona o livro à biblioteca
            Book book = new Book(title, genre, pages, author, publisher, isbn);
            lib.addBook(isbn, book);
    
            // Avança para a próxima linha vazia (ou seja, entre dois livros)
            if (reader.hasNextLine()) {
                reader.nextLine(); // Pula a linha vazia
            }
        }
        
        reader.close();
    }
     

    public static void loadUserBooks() throws IOException {
        Scanner reader = new Scanner(userBooks);
        
        while (reader.hasNextLine()) {
            String[] bookData = reader.nextLine().split(",");  
            if (bookData.length == 6) {
                String title = bookData[0].split(": ")[1].trim(); // Pega o valor após "Title: "
                String genre = bookData[1].split(": ")[1].trim(); // Pega o valor após "Genre: "
                
                // Remove a vírgula e converte para int
                int pages = Integer.parseInt(bookData[2].split(": ")[1].replace(",", "").trim()); // Remove a vírgula e espaços
                
                String author = bookData[3].split(": ")[1].trim(); // Pega o valor após "Author: "
                String publisher = bookData[4].split(": ")[1].trim(); // Pega o valor após "Publisher: "
                String isbn = bookData[5].split(": ")[1].trim(); // Pega o valor após "ISBN: "
                
                // Cria e adiciona o livro à lista de livros do usuário
                Book book = new Book(title, genre, pages, author, publisher, isbn);
                user.borrowBook(book);  
            }
        }
        
        reader.close();
    }    
    
    
    public static void main(String[] args) throws Exception {
        if (libBooks.exists()) {
            loadLibraryBooks();
        }
        
        if (userBooks.exists()) {
            loadUserBooks();
        }

        menu();
        sc.close();
        
    }
}
