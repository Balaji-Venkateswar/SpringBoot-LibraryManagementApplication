package com.project.library.repositories;

import com.project.library.domain.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository
        extends CrudRepository<BookEntity, String>, PagingAndSortingRepository<BookEntity, String>{

}