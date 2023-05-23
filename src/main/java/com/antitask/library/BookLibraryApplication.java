package com.antitask.library;

import com.antitask.library.repository.BookRepository;
import com.antitask.library.service.LibrarySeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BookLibraryApplication implements ApplicationRunner {

    @Autowired
    BookRepository bookRepository;
    @Autowired
    LibrarySeedService librarySeedService;

    public static void main(String[] args) {
        SpringApplication.run(BookLibraryApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        librarySeedService.seed();
    }
}