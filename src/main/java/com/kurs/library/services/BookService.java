package com.kurs.library.services;

import com.kurs.library.entity.Book;
import com.kurs.library.entity.dto.BookDTO;
import com.kurs.library.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

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

    public Book getBookByISBN(String isbn) {
        return bookRepository.findByIsbn(isbn).orElse(null);

    }

    public String saveBook(Book book) {
        return String.format("'%s' book is saved", bookRepository.save(book).getTitle());
    }

    public String deleteBook(Long id) {
        Optional<Book> bookById = bookRepository.findById(id);
        if (bookById.isPresent()) {
            bookRepository.deleteById(id);
            return String.format("'%s' has been deleted from the database", bookById.get().getTitle());
        }
        return null;
    }


    private BookDTO bookToDto(Book book) {
        return new BookDTO(book.getTitle(),
                book.getAuthor(),
                book.getIsbn(),
                book.isAvailable(),
                book.getDateBorrow());
    }



}
