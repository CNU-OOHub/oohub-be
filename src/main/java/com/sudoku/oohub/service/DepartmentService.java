package com.sudoku.oohub.service;

import com.sudoku.oohub.converter.Converter;
import com.sudoku.oohub.domain.Department;
import com.sudoku.oohub.dto.request.CreateDepartmentDto;
import com.sudoku.oohub.dto.response.DepartmentDto;
import com.sudoku.oohub.exception.DuplicateDepartmentException;
import com.sudoku.oohub.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    private final Converter converter;

    public Long save(CreateDepartmentDto departmentDto){

        if(departmentRepository.findByName(departmentDto.getDepartmentName()).orElse(null) != null){
            throw new DuplicateDepartmentException("이미 존재하는 부서명입니다.");
        }

        Department department = Department.builder()
                .name(departmentDto.getDepartmentName())
                .build();

        Department savedDepartment = departmentRepository.save(department);
        return savedDepartment.getId();
    }

    public DepartmentDto findByName(String departmentName){
        return departmentRepository.findByName(departmentName)
                .map(converter::convertDepartmentDto)
                .orElseThrow(() -> new RuntimeException("Could not find department by " + departmentName));
    }

    public List<DepartmentDto> findAll() {
        return departmentRepository.findAll()
                .stream().map((department)-> converter.convertDepartmentDto(department))
                .collect(Collectors.toList());
    }

}
