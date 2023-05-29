package com.kurs.library;

import com.kurs.library.services.SeederService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class LibraryApplication implements ApplicationRunner {

    private final SeederService service;

    public static void main(String[] args) {
        SpringApplication.run(LibraryApplication.class, args);
    }


    @Override
    public void run(ApplicationArguments args) {
        service.seed();
    }
}
