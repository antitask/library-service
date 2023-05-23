package com.kurs.library.controller;

import com.kurs.library.entity.Book;
import com.kurs.library.exception.BookException;
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
    public ResponseEntity<Book> getBookByISBN(@PathVariable(value = ISBN) String isbn) {
        Book bookByISBN = bookService.getBookByISBN(isbn);
        if (bookByISBN == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(bookByISBN);
    }

    @PostMapping(path = "/admin/book")
    public ResponseEntity<String> postBook(@Validated @RequestBody Book book) {
        return ResponseEntity.ok(bookService.saveBook(book));
    }

    @DeleteMapping(path = "/admin/book/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable(value = ID) Long id) {
        String bookToDelete = bookService.deleteBook(id);
        if (bookToDelete == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(bookToDelete);
    }


}
