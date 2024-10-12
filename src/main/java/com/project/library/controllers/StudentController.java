package com.project.library.controllers;

import com.project.library.domain.BookEntity;
import com.project.library.domain.StudentEntity;
import com.project.library.dto.StudentDto;
import com.project.library.mappers.Mapper;
import com.project.library.services.BookService;
import com.project.library.services.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class StudentController {

    private final StudentService studentService;
    private final BookService bookService;
    private final Mapper<StudentEntity, StudentDto> studentMapper;

    public StudentController(StudentService studentService, BookService bookService, Mapper<StudentEntity, StudentDto> studentMapper) {
        this.studentService = studentService;
        this.bookService = bookService;
        this.studentMapper = studentMapper;
    }


    @PostMapping(path = "/students")
    public ResponseEntity<StudentDto> createStudent(@RequestBody StudentDto studentDto){
        StudentEntity studentEntity = studentMapper.mapTo(studentDto);

        if(studentEntity.getBook() == null) {
            StudentEntity savedStudentEntity = studentService.createStudent(studentEntity);
            return new ResponseEntity<>(studentMapper.mapFrom(savedStudentEntity), HttpStatus.CREATED);
        }
        else{
            BookEntity bookEntity = studentEntity.getBook();
            boolean bookExists = bookService.isExists(bookEntity.getIsbn());
            if (bookExists){
                StudentEntity savedStudentEntity = studentService.createStudent(studentEntity);
                return new ResponseEntity<>(studentMapper.mapFrom(savedStudentEntity), HttpStatus.CREATED);
            }
            else
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/students/{id}")
    public ResponseEntity<StudentDto> getStudentWithId(@PathVariable Long id){

        Optional<StudentEntity> foundStudent = studentService.findOne(id);

        return foundStudent.map(studentEntity -> {
            StudentDto studentDto = studentMapper.mapFrom(studentEntity);
            return new ResponseEntity<>(studentDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(path = "/students")
    public List<StudentDto> getAllStudents(){
        List<StudentEntity> students = studentService.findAll();

        return students.stream()
                .map(studentMapper::mapFrom)
                .collect(Collectors.toList());
    }

    @DeleteMapping(path = "/students/{id}")
    public ResponseEntity deleteBook(@PathVariable Long id){
        studentService.remove(id);

        return  new ResponseEntity(HttpStatus.NO_CONTENT);

    }


}
