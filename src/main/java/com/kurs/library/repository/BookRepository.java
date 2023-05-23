package com.kurs.library.repository;

import com.kurs.library.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT b FROM Book b WHERE LOWER(b.author) = LOWER(:author)")
    List<Book> findByAuthorIgnoreCase(@Param(value = "author") String author);

    @Query("SELECT b FROM Book b WHERE LOWER(b.title) = LOWER(:title)")
    List<Book> findByTitleIgnoreCase(@Param(value = "title")String title);

    @Query("SELECT b FROM Book b WHERE b.isbn = :isbn")
    Optional<Book> findByIsbn(@Param(value = "isbn")String isbn);

    @Query("SELECT b FROM Book b WHERE b.available = :available ORDER BY title")
    List<Book> findByAvailable(@Param(value = "available") Boolean available);

}
