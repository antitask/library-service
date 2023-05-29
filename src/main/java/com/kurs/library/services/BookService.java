package com.kurs.library.services;

import com.kurs.library.entity.Book;
import com.kurs.library.entity.User;
import com.kurs.library.entity.dto.BookDTO;
import com.kurs.library.exception.BookException;
import com.kurs.library.repository.BookRepository;
import com.kurs.library.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;


    /**
     This method returns all books from the database
     or uses optional filters for return books by a specific author, title or availability
     */
    public List<Book> getAllBooksOrUseFilter(String author, String title, Boolean available) {
        if (author != null) {
            log.info("Filtering books by author");
            return bookRepository.findByAuthorIgnoreCase(author);
        }
        if (title != null) {
            log.info("Filtering books by title");
            return bookRepository.findByTitleIgnoreCase(title);
        }
        if (available != null) {
            log.info("Filtering books availability");
            return bookRepository.findByAvailable(available);
        }
        log.info("Returning all books");
        return bookRepository.findAll();
    }

    /**
     This method returns the book with a specific ISBN,
     or if ISBN is non-existing then throws BookException
     */
    public Book getBookByISBN(String isbn) throws BookException {
        Optional<Book> byIsbn = bookRepository.findByIsbn(isbn);
        if (byIsbn.isEmpty()) {
            log.info(String.format("Book with isbn = %s is not found", isbn));
            throw new BookException("BookException", HttpStatus.BAD_REQUEST, 400, "Not found!");
        }
        return byIsbn.get();
    }

    /**
     This method saves the book to the database,
     or if the book is already in the database throws BookException
     */
    public String saveBook(BookDTO bookDto) throws BookException {
        Book book = dtoToBook(bookDto);
        if (isTheBookAlreadyInDatabase(book)) {
            log.info(String.format("%s is already in database", book.getTitle()));
            throw new BookException(BookException.class.getName(), HttpStatus.BAD_REQUEST, 400,
                    String.format("%s is already in database", book.getTitle()));
        }
        return String.format("'%s' book is saved", bookRepository.save(book).getTitle());
    }

    /**
     This method deletes the book found by a specific id,
     or if the book with that id doesn't exist throws BookException
     */
    public String deleteBook(Long id) throws BookException {
        Optional<Book> bookById = bookRepository.findById(id);
        if (bookById.isPresent()) {
            bookRepository.deleteById(id);
            return String.format("'%s' has been deleted from the database", bookById.get().getTitle());
        }
        log.info(String.format("The Book with id = %d is not found", id));
        throw new BookException(BookException.class.getName(),
                HttpStatus.BAD_REQUEST, 400, String.format("The Book with id = %d is not found", id));
    }

    /**
     This method checks if the book is already in the database.
     */
    private boolean isTheBookAlreadyInDatabase(Book book) {
        List<Book> booksByTitleIgnoreCase = bookRepository.findByTitleIgnoreCase(book.getTitle());
        if (booksByTitleIgnoreCase.isEmpty()) {
            return false;
        }
        for (Book value : booksByTitleIgnoreCase) {
            if (value.getTitle().equalsIgnoreCase(book.getTitle())) {
                return true;
            }
        }
        return false;
    }

    /**
     - This method attempts to locate a book with specific ID and verifies its availability.
     - If both conditions are met, it sets the availability to false and
     record the current time as the borrowing time
     - it then updates the database with these changes.
     - Otherwise, it throws a BookException
     */
    public String borrowBook(Long id, User user) throws BookException {
        Optional<Book> book = bookRepository.findById(id);

        if (book.isPresent()) {
            if (book.get().isAvailable() && book.get().getBorrower() == null && user.getBook() == null) {
                book.get().setAvailable(false);
                book.get().setDateBorrow(LocalDate.now());
                book.get().setBorrower(user.getName());
                bookRepository.save(book.get());
                user.setBook(book.get().getTitle());
                userRepository.save(user);

                return String.format(String.format("'%s' is borrowed '%s'", user.getName(), book.get().getTitle()));
            }
            if (user.getBook() != null) {
                throw new BookException(BookException.class.getSimpleName(), HttpStatus.BAD_REQUEST,
                        400, "You already have borrowed book");
            }

            log.info(String.format("'%s' is not available", book.get().getTitle()));
            throw new BookException(BookException.class.getSimpleName(),
                    HttpStatus.BAD_REQUEST, 400, "The Book is not available");
        }
        log.info(String.format(String.format("The Book with id = %s is not found", id)));
        throw new BookException(BookException.class.getSimpleName(),
                HttpStatus.NOT_FOUND, 404, String.format("The Book with id = %s is not found", id));
    }

    private Book dtoToBook(BookDTO book) {
        return new Book(null,
                book.getTitle(),
                book.getAuthor(),
                book.getISBN(),
                book.isAvailable(),
                book.getDateBorrowed(),
                null);
    }

}