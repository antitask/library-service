package com.antitask.library.service;

import com.antitask.library.Entity.BookEntity;
import com.antitask.library.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }


    public BookEntity addBook(BookEntity book) {
        return bookRepository.save(book);
    }

    public void removeBook(Long bookId) {
        bookRepository.deleteById(bookId);
    }

    public List<BookEntity> getAllBooks() throws Exception {
        List<BookEntity> books = bookRepository.findAll();
        if (books.size() == 0) {
            throw new Exception("Trenutno nemamo knjiga.");
        } else {
            return books;
        }

    }

}
