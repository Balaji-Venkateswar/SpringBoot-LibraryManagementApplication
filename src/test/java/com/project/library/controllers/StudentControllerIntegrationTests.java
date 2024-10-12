package com.project.library.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.library.TestDataUtility;
import com.project.library.domain.BookEntity;
import com.project.library.domain.StudentEntity;
import com.project.library.dto.BookDto;
import com.project.library.dto.StudentDto;
import com.project.library.services.StudentService;
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
public class StudentControllerIntegrationTests {

    private MockMvc mockMvc;
    private StudentService studentService;
    private ObjectMapper objectMapper;

    @Autowired
    public StudentControllerIntegrationTests(MockMvc mockMvc, StudentService studentService, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.studentService = studentService;
        this.objectMapper = objectMapper;
    }

    @Test
    public void testThatStudentIsCreatedWithHttpStatus201Created() throws Exception {

        BookDto bookDto = TestDataUtility.testBookDto1();
        StudentDto studentDto = TestDataUtility.testStudentDto1(bookDto);

        String jsonStudent = objectMapper.writeValueAsString(studentDto);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonStudent)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );

    }

    @Test
    public void testThatCreatedStudentIsReturned() throws Exception {

        BookDto bookDto = TestDataUtility.testBookDto1();
        String jsonBook = objectMapper.writeValueAsString(bookDto);

        StudentDto studentDto = TestDataUtility.testStudentDto1(bookDto);
        String jsonStudent = objectMapper.writeValueAsString(studentDto);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonStudent)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(studentDto.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(studentDto.getAge())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.book.isbn").value(bookDto.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.book.title").value(bookDto.getTitle())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.book.published_year").value(bookDto.getPublished_year())
        );
    }


    @Test
    public void testThatStudentIsFoundWithHttpStatusOk() throws Exception {

        BookEntity bookEntity = TestDataUtility.testBookEntity1();
        StudentEntity studentEntity = TestDataUtility.testStudentEntity1(bookEntity);
        StudentEntity savedStudent = studentService.createStudent(studentEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/students/" + savedStudent.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );

    }

    @Test
    public void testThatStudentIsFoundAndReturned() throws Exception {

        BookEntity bookEntity = TestDataUtility.testBookEntity1();
        StudentEntity studentEntity = TestDataUtility.testStudentEntity1(bookEntity);
        StudentEntity savedStudent = studentService.createStudent(studentEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/students/" + savedStudent.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(savedStudent.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(savedStudent.getAge())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.book.isbn").value(bookEntity.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.book.title").value(bookEntity.getTitle())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.book.published_year").value(bookEntity.getPublished_year())
        );
    }


    @Test
    public void testThatAllStudentsAreReturnedWithHttpStatusOk() throws Exception {

        BookEntity bookEntity1 = TestDataUtility.testBookEntity1();
        BookEntity bookEntity2 = TestDataUtility.testBookEntity2();

        StudentEntity studentEntity1 = TestDataUtility.testStudentEntity1(bookEntity1);
        StudentEntity studentEntity2 = TestDataUtility.testStudentEntity1(bookEntity2);

        StudentEntity savedStudent1 = studentService.createStudent(studentEntity1);
        StudentEntity savedStudent2 = studentService.createStudent(studentEntity2);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/students")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );

    }

    @Test
    public void testThatAllStudentsAreReturned() throws Exception {

        BookEntity bookEntity1 = TestDataUtility.testBookEntity1();
        BookEntity bookEntity2 = TestDataUtility.testBookEntity2();

        StudentEntity studentEntity1 = TestDataUtility.testStudentEntity1(bookEntity1);
        StudentEntity studentEntity2 = TestDataUtility.testStudentEntity1(bookEntity2);

        StudentEntity savedStudent1 = studentService.createStudent(studentEntity1);
        StudentEntity savedStudent2 = studentService.createStudent(studentEntity2);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/students")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].name").value(savedStudent1.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].age").value(savedStudent1.getAge())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].book.isbn").value(bookEntity1.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].book.title").value(bookEntity1.getTitle())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].book.published_year").value(bookEntity1.getPublished_year())
        );

    }

    @Test
    public void testThatStudentIsDeletedWithHttpStatusNoContent() throws Exception {

        BookEntity bookEntity = TestDataUtility.testBookEntity1();
        StudentEntity studentEntity = TestDataUtility.testStudentEntity1(bookEntity);
        StudentEntity savedStudent = studentService.createStudent(studentEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/students/" + savedStudent.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        );

    }



}

