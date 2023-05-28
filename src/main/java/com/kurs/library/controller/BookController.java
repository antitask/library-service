package com.kurs.library.controller;

import com.kurs.library.entity.Book;
import com.kurs.library.exception.BookException;
import com.kurs.library.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class BookController {

    private static final String AUTHOR = "author";
    private static final String TITLE = "title";
    private static final String AVAILABLE = "available";
    private static final String ISBN = "isbn";
    private static final String ID = "id";
    private static final String AUTHORISATION = "Authorization";

    private final BookService bookService;

    @GetMapping(path = "/books")
    public ResponseEntity<List<Book>> getAllBooks(
            @RequestParam(value = AUTHOR, required = false) String author,
            @RequestParam(value = TITLE, required = false) String title,
            @RequestParam(value = AVAILABLE, required = false) Boolean available) throws BookException {
        List<Book> allBooks = bookService.getAllBooksOrUseFilter(author, title, available);
        if (allBooks.isEmpty()) {
            throw new BookException("BookException", HttpStatus.NOT_FOUND, 404, "Not found!");
        }
        return ResponseEntity.ok(allBooks);
    }

    @GetMapping(path = "/isbn/{isbn}")
    public ResponseEntity<Book> getBookByISBN(@PathVariable(value = ISBN) String isbn) throws BookException {
        Book bookByISBN = bookService.getBookByISBN(isbn);
        if (bookByISBN == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(bookByISBN);
    }

    @GetMapping(path = "/user/borrow/book/{id}")
    public ResponseEntity<String> borrowBook(
            @RequestHeader(value = AUTHORISATION) String authorisation,
            @PathVariable(value = ID) String id) throws BookException {
        if (authorisation == null || !authorisation.startsWith("Bearer ")) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (!bookService.isValidUser(authorisation)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return ResponseEntity.ok().body(bookService.borrowBook(id));

    }

    @PostMapping(path = "/admin/book")
    public ResponseEntity<String> postBook(@Validated @RequestBody Book book) throws BookException {
        return ResponseEntity.ok(bookService.saveBook(book));
    }

    @DeleteMapping(path = "/admin/book/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable(value = ID) Long id) throws BookException {
        return ResponseEntity.ok().body(bookService.deleteBook(id));
    }


}
