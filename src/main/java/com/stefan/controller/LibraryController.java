package com.stefan.controller;

import com.stefan.entities.Book;
import com.stefan.service.LibraryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j

public class LibraryController {

        @Autowired
        LibraryService libraryService;

        @GetMapping("/book/{title}")
        public ResponseEntity<Book> getBookByTitle(@PathVariable String title) throws Exception {
            return ResponseEntity.ok().body(libraryService.bookByTitle(title));
        }
}
