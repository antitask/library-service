package com.kurs.library.services;

import com.kurs.library.entity.Book;
import com.kurs.library.entity.User;
import com.kurs.library.entity.dto.BookDTO;
import com.kurs.library.exception.BookException;
import com.kurs.library.repository.BookRepository;
import com.kurs.library.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public List<Book> getAllBooksOrUseFilter(String author, String title, Boolean available) {
        if (author != null) {
            return bookRepository.findByAuthorIgnoreCase(author);
        }
        if (title != null) {
            return bookRepository.findByTitleIgnoreCase(title);
        }
        if (available != null) {
            return bookRepository.findByAvailable(available);
        }
        return bookRepository.findAll();
    }

    public Book getBookByISBN(String isbn) throws BookException {
        Optional<Book> byIsbn = bookRepository.findByIsbn(isbn);
        if (byIsbn.isEmpty()) {
            throw new BookException("BookException", HttpStatus.BAD_REQUEST, 400, "Not found!");
        }
        return byIsbn.get();
    }

    public String saveBook(Book book) throws BookException {
        if (isBookAlreadyInDatabase(book)) {
            throw new BookException(BookException.class.getName(), HttpStatus.BAD_REQUEST, 400,
                    String.format("%s is already in database", book.getTitle()));
        }
        return String.format("'%s' book is saved", bookRepository.save(book).getTitle());
    }

    public String deleteBook(Long id) throws BookException {
        Optional<Book> bookById = bookRepository.findById(id);
        if (bookById.isPresent()) {
            bookRepository.deleteById(id);
            return String.format("'%s' has been deleted from the database", bookById.get().getTitle());
        }
        throw new BookException(BookException.class.getName(),
                HttpStatus.BAD_REQUEST, 400, String.format("The Book wit id = %d is not found", id));
    }

    public boolean isValidUser(String authorisationHeader) {
        String sub = authorisationHeader.substring("Bearer ".length());
        String[] parse = sub.split("\\.");
        if (parse.length != 3) {
            return false;
        }
        Optional<User> byName = userRepository.findByName(parse[0]);
        if (byName.isEmpty()) {
            return false;
        }
        return byName
                .filter(user -> user.getName().equals(parse[0])
                        && user.getPassword().equals(parse[1]))
                .isPresent();
    }

    private boolean isBookAlreadyInDatabase(Book book) {
        List<Book> byTitleIgnoreCase = bookRepository.findByTitleIgnoreCase(book.getTitle());
        if (byTitleIgnoreCase.isEmpty()) {
            return false;
        }
        for (Book value : byTitleIgnoreCase) {
            if (value.getTitle().equalsIgnoreCase(book.getTitle())) {
                return true;
            }
        }
        return false;
    }

    public String borrowBook(String id) throws BookException {
        Optional<Book> book = bookRepository.findById(Long.parseLong(id));

        if (book.isPresent() && book.get().isAvailable()) {
            book.get().setAvailable(false);
            book.get().setDateBorrow(LocalDate.now());

            bookRepository.save(book.get());

            return String.format("You borrow " + book.get().getTitle() + ".");
        }
        throw new BookException(BookException.class.getSimpleName(),
                HttpStatus.BAD_REQUEST, 400, "Book is not available");
    }

    private BookDTO bookToDto(Book book) {
        return new BookDTO(book.getTitle(),
                book.getAuthor(),
                book.getIsbn(),
                book.isAvailable(),
                book.getDateBorrow());
    }

}
