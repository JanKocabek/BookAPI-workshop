package cz.kocabek.Service;

import cz.kocabek.dto.BookDTO;
import cz.kocabek.model.Book;
import cz.kocabek.repository.MemoryBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

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

    public BookDTO getBook(Long id) {
        final String BASE_URI = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        final var book = memoryBookRepository.findBookById(id);
        return new BookDTO(book, id, BASE_URI);

    }

    public Book addBook(Book book) {
        return memoryBookRepository.addBook(book);
    }

    public Book updateBook(Book book) {
        return memoryBookRepository.updateBook(book);
    }

    public void deleteBook(Long id) {
        memoryBookRepository.deleteBook(id);
    }
}

