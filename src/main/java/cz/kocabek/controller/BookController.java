package cz.kocabek.controller;

import cz.kocabek.model.Book;
import cz.kocabek.repository.MemoryBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final MemoryBookRepository memoryBookService;

    @Autowired
    public BookController(MemoryBookRepository memoryBookService) {
        this.memoryBookService = memoryBookService;
    }

//    @RequestMapping("/helloBook")
//    public Book helloBook() {
//        return new Book(1L, "9788324631766", "Thinking in Java",
//                "Bruce Eckel", "Helion", "programming");
//    }

    @GetMapping("")
    public List<Book> getBooks() {
        return memoryBookService.getBooks();
    }

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable("id") int id) {
        return memoryBookService.getBookById((long) id);
    }

}
