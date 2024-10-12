package com.project.library.services;

import com.project.library.domain.StudentEntity;

import java.util.List;
import java.util.Optional;

public interface StudentService {

    StudentEntity createStudent(StudentEntity student);

    Optional<StudentEntity> findOne(Long id);

    List<StudentEntity> findAll();

    void remove(Long id);
}
