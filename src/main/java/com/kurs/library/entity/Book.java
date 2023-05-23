package com.kurs.library.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(name = "books")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull(message = "title can't be null")
    @Size(min = 1, max = 250)
    private String title;
    @NotNull(message = "author can't be null")
    @Size(min = 1, max = 50)
    private String author;
    @NotNull(message = "isbn can't be null")
    @Size(min = 1, max = 20)
    private String isbn;
    @NotNull(message = "available can't be null")
    private boolean available;
    private LocalDate dateBorrow;

}
