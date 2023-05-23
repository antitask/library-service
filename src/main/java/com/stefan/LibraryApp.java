package com.stefan;

import com.stefan.service.BookSeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LibraryApp implements ApplicationRunner {

    @Autowired
    BookSeedService bookSeedService;

    public static void main(String[] args) {
        SpringApplication.run(LibraryApp.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        bookSeedService.seed();

    }
}
