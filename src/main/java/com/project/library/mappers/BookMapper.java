package com.project.library.mappers;

import com.project.library.domain.BookEntity;
import com.project.library.dto.BookDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class BookMapper implements  Mapper<BookEntity, BookDto>{

    private final ModelMapper modelMapper;

    public BookMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public BookEntity mapTo(BookDto bookDto) {
        return modelMapper.map(bookDto, BookEntity.class);
    }

    @Override
    public BookDto mapFrom(BookEntity bookEntity) {
        return modelMapper.map(bookEntity, BookDto.class);
    }
}
