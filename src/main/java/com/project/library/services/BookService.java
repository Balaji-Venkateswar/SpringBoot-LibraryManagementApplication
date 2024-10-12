package com.project.library.services;

import com.project.library.domain.BookEntity;

import java.util.List;
import java.util.Optional;

public interface BookService {

    BookEntity createUpdateBook(String isbn, BookEntity book);

    boolean isExists(String isbn);

    Optional<BookEntity> findOne(String isbn);

    List<BookEntity> findAll();

    void remove(String isbn);
}
