package com.project.library.mappers;

import com.project.library.domain.StudentEntity;
import com.project.library.dto.StudentDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper implements Mapper<StudentEntity, StudentDto> {

    private final ModelMapper modelMapper;

    public StudentMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    @Override
    public StudentEntity mapTo(StudentDto studentDto) {
        return modelMapper.map(studentDto, StudentEntity.class);
    }

    @Override
    public StudentDto mapFrom(StudentEntity studentEntity) {
        return modelMapper.map(studentEntity, StudentDto.class);
    }
}
