package com.project.library;
import com.project.library.domain.BookEntity;
import com.project.library.domain.StudentEntity;
import com.project.library.dto.BookDto;
import com.project.library.dto.StudentDto;

public class TestDataUtility {

    private TestDataUtility() {}

    public static BookEntity testBookEntity1() {
        return BookEntity.builder()
                .isbn("933-211-056")
                .title("A Brief History of Humankind")
                .published_year(2014)
                .build();
    }

    public static BookEntity testBookEntity2() {
        return BookEntity.builder()
                .isbn("235-452-012")
                .title("Circle")
                .published_year(1997)
                .build();
    }

    public static StudentEntity testStudentEntity1(final BookEntity bookEntity) {
        return StudentEntity.builder()
                .name("John")
                .age(19)
                .book(bookEntity)
                .build();

    }

    public static StudentEntity testStudentEntity2(final BookEntity bookEntity) {
        return StudentEntity.builder()
                .name("Parviz")
                .age(21)
                .book(bookEntity)
                .build();

    }




    public static BookDto testBookDto1() {
        return BookDto.builder()
                .isbn("933-211-056")
                .title("A Brief History of Humankind")
                .published_year(2014)
                .build();
    }

    public static BookDto testBookDto2() {
        return BookDto.builder()
                .isbn("235-452-012")
                .title("Circle")
                .published_year(1997)
                .build();
    }




    public static StudentDto testStudentDto1(final BookDto bookDto) {
        return StudentDto.builder()
                .name("John")
                .age(19)
                .book(bookDto)
                .build();
    }

    public static StudentDto testStudentDto2(final BookDto bookDto) {
        return StudentDto.builder()
                .name("Parviz")
                .age(21)
                .book(bookDto)
                .build();
    }




}

