package com.antitask.library.service;

import com.antitask.library.Entity.BookEntity;
import com.antitask.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.time.LocalDate;
import java.util.List;

import static java.time.LocalDate.of;

@Service
@Profile("dev")
public class LibrarySeedService {

    private final BookRepository bookRepository;

    public LibrarySeedService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void seed() {
        BookEntity book1 = new BookEntity(null, "The Hunger Games", "Marcos Quilasim", "8432-3532-7543-1234",
                true, LocalDate.of(2021, 2, 24), "Johnny99");
        BookEntity book2 = new BookEntity(null, "To Kill a Mockingbird", "Harper Lee", "9780061120084", true, LocalDate.of(2021, 3, 15), null);

        BookEntity book3 = new BookEntity(null, "1984", "George Orwell", "9780451524935", true, LocalDate.of(2021, 4, 5), null);

        BookEntity book4 = new BookEntity(null, "The Great Gatsby", "F. Scott Fitzgerald", "9780743273565", true, LocalDate.of(2021, 5, 1), null);

        BookEntity book5 = new BookEntity(null, "Moby Dick", "Herman Melville", "9780142437247", false, null, null);

        BookEntity book6 = new BookEntity(null, "War and Peace", "Leo Tolstoy", "9780140447934", true, LocalDate.of(2021, 6, 20), null);

        BookEntity book7 = new BookEntity(null, "The Catcher in the Rye", "J.D. Salinger", "9780316769488", false, null, null);

        BookEntity book8 = new BookEntity(null, "Pride and Prejudice", "Jane Austen", "9780141439518", true, LocalDate.of(2021, 7, 10), null);

        BookEntity book9 = new BookEntity(null, "The Lord of the Rings", "J.R.R. Tolkien", "9780618640157", true, LocalDate.of(2021, 8, 5), null);

        BookEntity book10 = new BookEntity(null, "The Chronicles of Narnia", "C.S. Lewis", "9780064471190", false, null, null);

        BookEntity book11 = new BookEntity(null, "Harry Potter and the Philosopher's Stone", "J.K. Rowling", "9780747532743", true, LocalDate.of(2021, 9, 15), null);

        BookEntity book12 = new BookEntity(null, "The Hobbit", "J.R.R. Tolkien", "9780547928227", true, LocalDate.of(2021, 10, 1), null);



        List <BookEntity> books = List.of(book1, book2, book3, book4, book5, book6, book7, book8, book9, book10, book11, book12);
        bookRepository.saveAll(books);

    }

}
