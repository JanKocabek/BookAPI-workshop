package cz.kocabek.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.Instant;

@RestControllerAdvice
public class MyGlobalExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(BookNotFoundException.class)
    public ErrorResponse handleBookNotFoundException(BookNotFoundException ex) {
        final var problemDetail = buildProblemDetail(HttpStatus.NOT_FOUND, ex, "Book not found", "book-not-found");
        return ErrorResponse.builder(ex, problemDetail).build();
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DuplicatedRecordException.class)
    public ErrorResponse handleDuplicatedRecordException(DuplicatedRecordException ex) {
        final var problemDetail = buildProblemDetail(HttpStatus.CONFLICT, ex, "Book Duplication", "book-duplication");
        return ErrorResponse.builder(ex, problemDetail).build();
    }

    private static ProblemDetail buildProblemDetail(HttpStatus status, Exception ex, String title, String type) {
        final var problemDetail = ProblemDetail.forStatusAndDetail(status, ex.getMessage());
        final var errPath = "/books/error";
        final var uri = ServletUriComponentsBuilder.fromCurrentContextPath().path(errPath).toUriString();
        problemDetail.setTitle(title);
        problemDetail.setType(URI.create(uri + "/" + type));
        problemDetail.setProperty("timestamp", Instant.now().toString());
        return problemDetail;
    }

}
