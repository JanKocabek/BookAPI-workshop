package cz.kocabek.Service;

import cz.kocabek.model.Book;
import cz.kocabek.repository.MemoryBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final MemoryBookRepository memoryBookRepository;

    @Autowired
    public BookService(MemoryBookRepository memoryBookRepository) {
        this.memoryBookRepository = memoryBookRepository;
    }

    public List<Book> getBooks() {
        return memoryBookRepository.findBooks();
    }

    public Optional<Book> getBook(Long id) {
        return memoryBookRepository.findBookById(id);
    }

    public Book addBook(Book book) {
        return memoryBookRepository.addBook(book);
    }

    public Optional<Book> updateBook(Book book) {
        return memoryBookRepository.updateBook(book);
    }
    public Boolean deleteBook(Long id) {
        return memoryBookRepository.deleteBook(id);
    }
}

