package com.kurs.library.services;

import com.kurs.library.entity.Book;
import com.kurs.library.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SeederService {

    private final BookRepository bookRepository;

    public void seed() {

        Book book1 = new Book(null, "Bruklinska revija ludosti", "Pol Oster", "0-200-0000-0", true, null);
        Book book2 = new Book(null, "Njujorska trilogija", "Pol Oster", "0-201-1111-1", false, LocalDate.of(2023, 5, 9));
        Book book3 = new Book(null, "Autostoperski vodic kroz galaksiju", "Daglas Adams", "0-202-2222-2", true, null);
        Book book4 = new Book(null, "Moja borba", "Karl Uve Knausgor", "0-203-3333-3", false, LocalDate.of(2023, 5, 22));
        Book book5 = new Book(null, "Dopler", "Erland Lu", "0-204-5555-4", true, null);
        Book book6 = new Book(null, "Elementarne cestice", "Misel Uelbek", "0-205-5555-5", false, LocalDate.of(2023, 5, 10));
        Book book7 = new Book(null, "Kada je Nice plakao", "Irvin D. Jalom", "0-206-6666-6", true, null);
        Book book8 = new Book(null, "Hokus pokus", "Kurt Vonegat", "0-207-7777-7", true, null);
        Book book9 = new Book(null, "Crni obelisk", "E.M. Remark", "0-208-8888-8", true, null);
        Book book10 = new Book(null, "Smrt i njeni hirovi", "Zoye Saramago", "0-209-9999-9", false, LocalDate.of(2023, 5, 10));

        List<Book> books = List.of(book1, book2, book3, book4, book5, book6, book7, book8, book9, book10);
        bookRepository.saveAll(books);
    }
}
