package com.kurs.library.entity.dto;

import com.kurs.library.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
@Data
@AllArgsConstructor
public class BookDTO {
    private String title;
    private String author;
    private String ISBN;
    private boolean available;
    private LocalDate dateBorrowed;
}
