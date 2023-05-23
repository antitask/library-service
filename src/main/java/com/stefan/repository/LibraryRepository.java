package com.stefan.repository;

import com.stefan.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibraryRepository extends JpaRepository<Book, Integer> {

    Book findByTitle(String title);

}
