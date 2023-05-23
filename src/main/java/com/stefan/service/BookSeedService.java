package com.stefan.service;

import com.stefan.entities.Book;
import com.stefan.repository.LibraryRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class BookSeedService {

    private final LibraryRepository libraryRepository;

    public BookSeedService(LibraryRepository libraryRepository) {
        this.libraryRepository = libraryRepository;
    }

    public void seed() {
        Book book = new Book( 1,"Lovac u žitu", "J.D. Salinger", "ISBN-10", "available");
        Book book1 = new Book(2, "Putovanje na kraj noći", "Louis Ferdinand Celin", "ISBN-20", "borrowed");
        Book book2 = new Book(3, "Braća Karamazovi", "F.M. Dostojevski", "ISBN-30", "available");
        Book book3 = new Book(4, "Kafka na obali mora", "Haruki Murakami", "ISBN-40", "borrowed");
        Book book4 = new Book(5, "Viktorija", "Knut Hamsun", "ISBN-50", "available");
        Book book5 = new Book(6, "Bludni sin", "Čarls Bukovski", "ISBN-60", "borrowed");
        Book book6 = new Book(7, "100 godina samoće", "Gabriel Garsija Markez", "ISBN-70", "available");
        Book book7 = new Book(8, "Uliks", "Dzejms Dzojs", "ISBN-80", "borrowed");
        Book book8 = new Book(9, "Don Kihot", "Migel de Servantes", "ISBN-90", "available");
        Book book9 = new Book(10, "Mali princ", "Antoine de Saint Exupery", "ISBN-100", "borrowed");
        List<Book> books = List.of(book, book1, book2, book3, book4, book5, book6, book7, book8, book9);
        libraryRepository.saveAll(books);
    }
}
