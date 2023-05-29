package com.kurs.library.controller;

import com.kurs.library.entity.Book;
import com.kurs.library.entity.Role;
import com.kurs.library.entity.User;
import com.kurs.library.entity.dto.BookDTO;
import com.kurs.library.exception.BookException;
import com.kurs.library.services.AuthService;
import com.kurs.library.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final AuthService authService;

    @GetMapping(path = "public/books")
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

    @GetMapping(path = "public/isbn/{isbn}")
    public ResponseEntity<Book> getBookByISBN(@PathVariable(value = ISBN) String isbn) throws BookException {
        Book bookByISBN = bookService.getBookByISBN(isbn);
        if (bookByISBN == null) {
            throw new BookException("BookException", HttpStatus.NOT_FOUND, 404, "Not found!");
        }
        return ResponseEntity.ok(bookByISBN);
    }

    @GetMapping(path = "/user/borrow/book/{id}")
    public ResponseEntity<String> borrowBook(
            @RequestHeader(value = AUTHORISATION) String authorisation,
            @PathVariable(value = ID) Long id) throws BookException {
        if (authorisation == null || !authorisation.startsWith("Bearer ")) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        User user = authService.authorisedUser(authorisation);

        if (user != null && user.getRole().equals(Role.USER)) {
            return ResponseEntity.ok().body(bookService.borrowBook(id, user));
        }
        throw new BookException("BookException", HttpStatus.UNAUTHORIZED, 401, "Unauthorized");
    }

    @PostMapping(path = "/admin/book")
    public ResponseEntity<String> postBook(
            @RequestHeader(value = AUTHORISATION) String authorisation,
            @Validated @RequestBody BookDTO book) throws BookException {
        if (authorisation == null || !authorisation.startsWith("Bearer ")) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        User user = authService.authorisedUser(authorisation);
        if (user != null && user.getRole().equals(Role.ADMIN)) {
            return ResponseEntity.ok(bookService.saveBook(book));
        }
        throw new BookException("BookException", HttpStatus.UNAUTHORIZED, 401, "Unauthorized");
    }

    @DeleteMapping(path = "/admin/book/{id}")
    public ResponseEntity<String> deleteBook(
            @RequestHeader(value = AUTHORISATION) String authorisation,
            @PathVariable(value = ID) Long id) throws BookException {
        if (authorisation == null || !authorisation.startsWith("Bearer ")) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        User user = authService.authorisedUser(authorisation);
        if (user != null && user.getRole().equals(Role.ADMIN)) {
            return ResponseEntity.ok().body(bookService.deleteBook(id));
        }
        throw new BookException("BookException", HttpStatus.UNAUTHORIZED, 401, "Unauthorized");

    }

}
