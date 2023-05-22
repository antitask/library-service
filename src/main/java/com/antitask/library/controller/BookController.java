package com.antitask.library.controller;

import com.antitask.library.Entity.BookEntity;
import com.antitask.library.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Book;
import java.util.List;

@RestController
@Slf4j
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/books")
    public BookEntity addBook(@RequestBody BookEntity book) {
        return bookService.addBook(book);
    }

    @DeleteMapping("/{bookId}")
    public void removeBook(@PathVariable Long bookId) {
        bookService.removeBook(bookId);
    }

    @GetMapping("/allbooks")
    public List<BookEntity> getAllBooks() throws Exception {
        return bookService.getAllBooks();
    }


}