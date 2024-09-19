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
                    Integer ISBN = sc.nextInt();                    
                    Book b = new Book(title, genre, pages, author, pub, ISBN);
                    lib.addBook(ISBN, b);
                    writeFile(b.toString(), libBooks);
                    break;
                case 2:
                    
                    System.out.println("Write the ISBN (International Standard Book Number) of the book:");
                    lib.removeBook(sc.nextInt());
                    updateLibraryFile();
                    break;
                case 3:
                    readFile(libBooks);
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
                    System.out.println("Choose a Book by its ISBN:"+lib.allBooks());
                    Book b = lib.getBook(sc.nextInt());
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
                    System.out.println("Which book are you returning?"+user.borrowedBooksList()+" Enter its title: ");
                    Book returned = user.returnBook(sc.next());
                    sc.nextLine();
                    if(returned != null){
                        lib.addBook(returned.getISBN(), returned);
                        updateUserBooksFile();
                    }        
                    else
                        System.out.println("Cannot return! Invalid Input.");
                    break;
                case 3:
                    readFile(userBooks);
                    break;
                default:
                    System.out.println("Invalid Option! Try again");
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
    
    public static void main(String[] args) throws Exception {
        menu();
        sc.close();
        
    }
}
