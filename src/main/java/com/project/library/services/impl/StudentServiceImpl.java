package com.project.library.services.impl;

import com.project.library.domain.StudentEntity;
import com.project.library.repositories.StudentRepository;
import com.project.library.services.StudentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    public StudentServiceImpl(final StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    @Override
    public StudentEntity createStudent(StudentEntity student) {
        return studentRepository.save(student);
    }

    @Override
    public Optional<StudentEntity> findOne(Long id) {
        return studentRepository.findById(id);
    }

    @Override
    public List<StudentEntity> findAll() {
        return StreamSupport.stream(
                studentRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public void remove(Long id) {
        studentRepository.deleteById(id);
    }
}
