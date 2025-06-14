package cz.kocabek.controller;

import com.fasterxml.jackson.annotation.JsonView;
import cz.kocabek.Service.BookService;
import cz.kocabek.dto.BookDTO;
import cz.kocabek.dto.BooksDTO;
import cz.kocabek.dto.View;
import cz.kocabek.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @JsonView(View.Book.class)
    @GetMapping("")
    public BooksDTO getBooks() {
        return bookService.getBooks().withStatus(HttpStatus.OK);
    }

    @JsonView(View.BookWithStatus.class)
    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(bookService.getBook(id).withStatus(HttpStatus.OK));
    }

    @PostMapping(value = "", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        Book adedBook = bookService.addBook(book);
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(adedBook.getId()).toUri()).body(adedBook);
    }

    @PutMapping(value = "", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Book> updateBook(@RequestBody Book book) {
        return ResponseEntity.ok(bookService.updateBook(book));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable("id") Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();

    }

}
