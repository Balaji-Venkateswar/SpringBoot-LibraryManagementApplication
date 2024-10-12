package com.project.library.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.library.TestDataUtility;
import com.project.library.domain.BookEntity;
import com.project.library.dto.BookDto;
import com.project.library.services.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class BookControllerIntegrationTests {

    private MockMvc mockMvc;
    private BookService bookService;
    private ObjectMapper objectMapper;

    @Autowired
    public BookControllerIntegrationTests(MockMvc mockMvc, BookService bookService, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.bookService = bookService;
        this.objectMapper = objectMapper;
    }

    @Test
    public void testThatBookIsCreatedWithHttpStatus201Created() throws Exception {

        BookDto bookDto = TestDataUtility.testBookDto1();
        String jsonBook = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/933-211-056")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBook)
        ).andExpect(
               MockMvcResultMatchers.status().isCreated()
       );
    }

    @Test
    public void testThatCreateBookReturnsCreatedBook() throws Exception {
        BookDto bookDto = TestDataUtility.testBookDto1();
        String jsonBook = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/933-211-056")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBook)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value("933-211-056")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value("A Brief History of Humankind")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.published_year").value(2014)
        );
    }

    @Test
    public void testThatBookAlreadyCreatedReturnsHttpStatusOk() throws Exception {

        BookEntity bookEntity = TestDataUtility.testBookEntity1();
        BookEntity savedBookEntity = bookService.createUpdateBook(bookEntity.getIsbn(), bookEntity);

        BookDto bookDto = TestDataUtility.testBookDto1();
        String jsonBook = objectMapper.writeValueAsString(bookDto);


        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/933-211-056")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBook)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatBookAlreadyCreatedReturnsCreatedBook() throws Exception {

        BookEntity bookEntity = TestDataUtility.testBookEntity1();
        BookEntity savedBookEntity = bookService.createUpdateBook(bookEntity.getIsbn(), bookEntity);

        BookDto bookDto = TestDataUtility.testBookDto1();
        String jsonBook = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/933-211-056")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBook)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value(savedBookEntity.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value(savedBookEntity.getTitle())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.published_year").value(savedBookEntity.getPublished_year())
        );
    }



    @Test
    public void testThatOneBookIsReturnedWithHttpStatusOk() throws Exception {

        BookEntity bookEntity = TestDataUtility.testBookEntity1();
        BookEntity savedBook = bookService.createUpdateBook(bookEntity.getIsbn(), bookEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/" + savedBook.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatOneBookIsReturned() throws Exception {

        BookEntity bookEntity = TestDataUtility.testBookEntity1();
        BookEntity savedBook = bookService.createUpdateBook(bookEntity.getIsbn(), bookEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/" + savedBook.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value(bookEntity.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value(bookEntity.getTitle())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.published_year").value(bookEntity.getPublished_year())
        );
    }




    @Test
    public void testThatAllBookAreReturnedWithHttpStatusOk() throws Exception {

        BookEntity bookEntity = TestDataUtility.testBookEntity1();
        BookEntity savedBook = bookService.createUpdateBook(bookEntity.getIsbn(), bookEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/books")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );

    }

    @Test
    public void testThatAllBookAreReturned() throws Exception {

        BookEntity bookEntity = TestDataUtility.testBookEntity1();
        BookEntity savedBook = bookService.createUpdateBook(bookEntity.getIsbn(), bookEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/books")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].isbn").value("933-211-056")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].title").value("A Brief History of Humankind")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].published_year").value(2014)
        );

    }

    @Test
    public void testThatBookIsDeletedWithHttpStatusNoContent() throws Exception {

        BookEntity bookEntity = TestDataUtility.testBookEntity1();
        BookEntity savedBook = bookService.createUpdateBook(bookEntity.getIsbn(), bookEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/books/"+ savedBook.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        );

    }


}
