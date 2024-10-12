package com.project.library.repositories;

import com.project.library.domain.BookEntity;
import com.project.library.domain.StudentEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface StudentRepository extends CrudRepository<StudentEntity, Long>,
        PagingAndSortingRepository<StudentEntity, Long> {}

