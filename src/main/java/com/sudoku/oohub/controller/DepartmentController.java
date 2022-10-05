package com.sudoku.oohub.controller;

import com.sudoku.oohub.domain.Department;
import com.sudoku.oohub.dto.request.CreateDepartmentDto;
import com.sudoku.oohub.dto.response.DepartmentDto;
import com.sudoku.oohub.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping("/v1/department/{departmentName}")
    public ResponseEntity<DepartmentDto> findByUsername(@PathVariable String departmentName) {
        DepartmentDto department = departmentService.findByName(departmentName);
        return ResponseEntity.ok(department);
    }

    @GetMapping("/v1/department")
    public ResponseEntity<List<DepartmentDto>> viewAll() {
        List<DepartmentDto> all = departmentService.findAll();
        return ResponseEntity.ok(all);
    }

    @PostMapping("/v1/department")
    public ResponseEntity<Long> save(@RequestBody CreateDepartmentDto departmentDto){
        Long departmentId = departmentService.save(departmentDto);
        return ResponseEntity.ok(departmentId);
    }


}
