package cz.kocabek.repository;

import cz.kocabek.exception.BookNotFoundException;
import cz.kocabek.exception.DuplicatedRecordException;
import cz.kocabek.model.Book;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MemoryBookRepository {

    private final TreeMap<Long, Book> books = new TreeMap<>();
    private final Map<String, Long> isbnLookup = new HashMap<>();
    private Long lastId;

    MemoryBookRepository() {
        books.put(1L, new Book(1L, "978-3-16-148410-0", "The Great Gatsby", "F. Scott Fitzgerald", "Scribner", "Fiction"));
        books.put(2L, new Book(2L, "978-0-14-028333-4", "1984", "George Orwell", "Penguin", "Dystopian"));
        books.put(3L, new Book(3L, "978-0-451-53091-3", "Fahrenheit 451", "Ray Bradbury", "Ballantine Books", "Science Fiction"));
        books.put(4L, new Book(4L, "978-0-06-112008-4", "To Kill a Mockingbird", "Harper Lee", "Harper Perennial", "Classic"));
        books.put(5L, new Book(5L, "978-0-7432-7356-5", "The Da Vinci Code", "Dan Brown", "Doubleday", "Thriller"));
        books.put(6L, new Book(6L, "978-1-5011-8887-0", "Where the Crawdads Sing", "Delia Owens", "G.P. Putnam's Sons", "Mystery"));
        books.put(7L, new Book(7L, "978-0-452-28423-4", "Pride and Prejudice", "Jane Austen", "Penguin Classics", "Romance"));
        books.put(8L, new Book(8L, "978-0-670-82162-4", "The Road", "Cormac McCarthy", "Knopf", "Post-Apocalyptic"));
        books.put(9L, new Book(9L, "978-0-06-085052-4", "Brave New World", "Aldous Huxley", "Harper Perennial", "Dystopian"));
        books.put(10L, new Book(10L, "978-0-15-601219-5", "Life of Pi", "Yann Martel", "Mariner Books", "Adventure"));
        lastId = books.lastKey();
        for (Book book : books.values()) {
            isbnLookup.put(book.getIsbn(), book.getId());
        }
    }


    public List<Book> findBooks() {
        return List.copyOf(books.values());
    }

    public Book findBookById(Long id) {
        if (!books.containsKey(id))
            throw new BookNotFoundException("Book with id: " + id + " not found");
        return books.get(id);
    }

    public Book addBook(Book book) {
        if (checkISBN(book.getIsbn())) throw new DuplicatedRecordException("Duplicated ISBN");
        book.setId(lastId + 1);
        lastId++;
        books.put(book.getId(), book);
        isbnLookup.put(book.getIsbn(), book.getId());
        //System.out.println("booksIsbn: " + isbnLookup);
        return book;
    }

    public Book updateBook(Book book) {
        if (!books.containsKey(book.getId()))
            throw new BookNotFoundException("Book with id: " + book.getId() + " not found and can't be updated");
        if (checkISBN(book.getIsbn()) && !book.getIsbn().equals(books.get(book.getId()).getIsbn()))
            throw new DuplicatedRecordException("This ISBN is already taken, book can't be updated");
        books.put(book.getId(), book);
        return book;
    }

    public void deleteBook(Long id) {
        if (!books.containsKey(id))
            throw new BookNotFoundException("Book with ID " + id + " not found and can't be deleted");
        books.remove(id);
    }

    private boolean checkISBN(String isbn) {
        return isbnLookup.containsKey(isbn);
    }
}
