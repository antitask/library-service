package com.antitask.library.Entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Table (name = "books")
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String author;
    private String isbn;
    private boolean availability;
    private LocalDate dateBorrowed;
    private String username;

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getUsername() {
        return username;
    }

    public LocalDate getDateBorrowed() {
        return dateBorrowed;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setDateBorrowed(LocalDate dateBorrowed) {
        this.dateBorrowed = dateBorrowed;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}