package cz.kocabek.controller;

import cz.kocabek.Service.BookService;
import cz.kocabek.exception.BookNotFoundException;
import cz.kocabek.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("")
    public List<Book> getBooks() {
        return bookService.getBooks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable("id") Long id) {
        Book book = bookService.getBook(id).orElseThrow(() -> new BookNotFoundException("Book with id " + id + " not found"));
        return ResponseEntity.ok(book);
    }

    @PostMapping(value = "", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        Book adedBook= bookService.addBook(book);
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(adedBook.getId()).toUri()).body(adedBook);
    }

    @PutMapping(value = "", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Book> updateBook(@RequestBody Book book) {
        Book updateBook = bookService.updateBook(book).orElseThrow(() -> new BookNotFoundException("book with id: " + book.getId() + " not found and can't be updated"));
        return ResponseEntity.ok(updateBook);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteBook(@PathVariable("id") Long id) {
        if (bookService.deleteBook(id)) {
            return ResponseEntity.ok(true);
        }
        throw new BookNotFoundException("Book with id: " + id + " not found and can't be deleted");
    }

}
