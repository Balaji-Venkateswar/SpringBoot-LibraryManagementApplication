package com.project.library.controllers;

import com.project.library.domain.BookEntity;
import com.project.library.dto.BookDto;
import com.project.library.mappers.Mapper;
import com.project.library.services.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class BookController {

    private final BookService bookService;
    private final Mapper<BookEntity, BookDto> bookMapper;

    public BookController(BookService bookService, Mapper<BookEntity, BookDto> bookMapper) {
        this.bookService = bookService;
        this.bookMapper = bookMapper;
    }

    @PutMapping(path = "/books/{isbn}")
    public ResponseEntity<BookDto> createUpdateBook(@PathVariable String isbn, @RequestBody BookDto bookDto) {
        BookEntity bookEntity = bookMapper.mapTo(bookDto);
        boolean bookExists = bookService.isExists(isbn);
        BookEntity savedBookEntity = bookService.createUpdateBook(isbn, bookEntity);
        BookDto savedUpdatedBookDto = bookMapper.mapFrom(savedBookEntity);

        if(bookExists)
            return new ResponseEntity<>(savedUpdatedBookDto, HttpStatus.OK);
        else
            return new ResponseEntity<>(savedUpdatedBookDto, HttpStatus.CREATED);
    }

    @GetMapping(path = "/books/{isbn}")
    public ResponseEntity<BookDto> getBookWithIsbn(@PathVariable String isbn){

        Optional<BookEntity> foundBookEntity = bookService.findOne(isbn);

        return  foundBookEntity.map(bookEntity -> {
                BookDto bookDto = bookMapper.mapFrom(bookEntity);
                return new ResponseEntity<>(bookDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @GetMapping(path = "/books")
    public List<BookDto> getAllBooks(){
        List<BookEntity> books = bookService.findAll();

        return books.stream()
                .map(bookMapper::mapFrom)
                .collect(Collectors.toList());
    }

    @DeleteMapping(path = "/books/{isbn}")
    public ResponseEntity deleteBook(@PathVariable String isbn){
        bookService.remove(isbn);
        return  new ResponseEntity(HttpStatus.NO_CONTENT);
    }


}
