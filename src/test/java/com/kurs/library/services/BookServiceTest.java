package com.kurs.library.services;

import com.kurs.library.entity.Book;
import com.kurs.library.entity.dto.BookDTO;
import com.kurs.library.exception.BookException;
import com.kurs.library.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService underTest;
    private Book TEST_BOOK;

    @BeforeEach
    public void start() {
        TEST_BOOK = new Book(null, "Title", "Author", "ISBN", true, null, null);
    }

    @Test
    void testGetAllBooksOrUseFilter_WithAuthorFilter() {
        when(underTest.getAllBooksOrUseFilter("Author", null, null)).thenReturn(List.of(TEST_BOOK));

        List<Book> allBooksOrUseFilter =
                underTest.getAllBooksOrUseFilter("Author", null, null);

        assertEquals(TEST_BOOK, allBooksOrUseFilter.get(0));
        verify(bookRepository, times(1)).findByAuthorIgnoreCase(TEST_BOOK.getAuthor());
    }

    @Test
    void testGetAllBooksOrUseFilter_WithoutFilter() {
        Book book = new Book();
        when(underTest.getAllBooksOrUseFilter(null, null, null)).thenReturn(List.of(TEST_BOOK, book));

        List<Book> allBooksOrUseFilter =
                underTest.getAllBooksOrUseFilter(null, null, null);

        assertEquals(2, allBooksOrUseFilter.size());
        verify(bookRepository, times(1)).findAll();

    }

    @Test
    void testGetByISBM_WitValidISBN() throws BookException {
        when(bookRepository.findByIsbn(TEST_BOOK.getIsbn())).thenReturn(Optional.of(TEST_BOOK));

        Book isbn = underTest.getBookByISBN("ISBN");
        assertEquals(isbn, TEST_BOOK);
        verify(bookRepository, times(1)).findByIsbn(TEST_BOOK.getIsbn());

    }

    @Test
    void testGetByISBM_WithNonExistingISBN() {
        assertThrows(BookException.class, () -> underTest.getBookByISBN("1234"));
        verify(bookRepository, times(1)).findByIsbn("1234");
    }

    @Test
    void testSaveBookMethod_WithValidBook() throws BookException {
        when(bookRepository.save(TEST_BOOK)).thenReturn(TEST_BOOK);

        String result = underTest.saveBook(new BookDTO(TEST_BOOK.getTitle(), TEST_BOOK.getAuthor(), TEST_BOOK.getIsbn(), TEST_BOOK.isAvailable(), null));

        assertEquals(String.format("'%s' book is saved", TEST_BOOK.getTitle()), result);
        verify(bookRepository, times(1)).save(TEST_BOOK);

    }

    @Test
    void testDeleteBook_WithValidId() throws BookException {
        Long id = 1L;

        when(bookRepository.findById(id)).thenReturn(Optional.of(TEST_BOOK));

        String result = underTest.deleteBook(id);
        assertEquals(String.format("'%s' has been deleted from the database", TEST_BOOK.getTitle()), result);
        verify(bookRepository, times(1)).findById(id);
        verify(bookRepository, times(1)).deleteById(id);

    }

    @Test
    void testDeleteBook_WithNonExistingId() {
        Long id = 1L;

        assertThrows(BookException.class, () -> underTest.deleteBook(id));

        verify(bookRepository, times(1)).findById(id);
        verify(bookRepository, times(0)).deleteById(id);

    }
}