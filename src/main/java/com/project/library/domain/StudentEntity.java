package com.project.library.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "students")
public class StudentEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_id_seq")
        private Long id;

        private String name;

        private Integer age;

        @ManyToOne
        @JoinColumn(name = "book_isbn")
        private BookEntity book;

}
