package cz.kocabek.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import cz.kocabek.model.Book;
import org.springframework.http.HttpStatus;


public class BookDTO {
    private int status;
    private final Book book;
    @JsonProperty("self")
    private final String selfUri;

    @JsonCreator
    public BookDTO(Book book, Long id, String uri) {
        this.book = book;
        this.selfUri = uri + "/books/" + id;
    }

    public Book getBook() {
        return book;
    }

    public String getSelfUri() {
        return selfUri;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status.value();
    }

    public BookDTO withStatus(HttpStatus status) {
        this.status = status.value();
        return this;
    }

    @Override
    public String toString() {
        return "BookDTO{" +
               "book=" + book +
               ", selfUri='" + selfUri + '\'' +
               '}';
    }
}
