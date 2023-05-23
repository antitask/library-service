package com.stefan.service;

import com.stefan.entities.Book;
import com.stefan.repository.LibraryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibraryService {

    @Autowired
    LibraryRepository libraryRepository;

    public Book bookByTitle(String title) throws Exception {
        Book book = libraryRepository.findByTitle(title);
        if(book == null){
            throw new Exception("Nemamo tu knjigu.");
        } else {
            return book;
        }
    }

    public List<Book> allBooks () throws Exception {
        List<Book> books = libraryRepository.findAll();
        if (books.size() == 0) {
            throw new Exception("Nema knjiga u biblioteci!!!");
        } else {
            return books;
        }
    }
}

