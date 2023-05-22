package com.antitask.library.service;

import com.antitask.library.Entity.BookEntity;
import com.antitask.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("dev")
public class LibrarySeedService {

    private final BookRepository bookRepository;

    public LibrarySeedService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
public void seed() {
        String[] bookTitles = {
                "Ana Karenjina",
                "To Kill a Mockingbird",
                "1984",
                "The Great Gatsby",
                "Moby Dick",
                "War and Peace",
                "The Catcher in the Rye",
                "Pride and Prejudice",
                "The Lord of the Rings",
                "The Chronicles of Narnia",
                "Harry Potter and the Philosopher's Stone",
                "The Hunger Games"
        };

        for (String title : bookTitles) {
            BookEntity book = new BookEntity();
            book.setTitle(title);

            bookRepository.save(book);
        }
    }
}
