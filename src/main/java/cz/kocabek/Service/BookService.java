package cz.kocabek.Service;

import cz.kocabek.model.Book;
import cz.kocabek.repository.MemoryBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Book getBook(Long id) {
        return memoryBookRepository.findBookById(id);
    }

}

