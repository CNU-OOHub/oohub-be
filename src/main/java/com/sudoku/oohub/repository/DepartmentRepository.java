package com.sudoku.oohub.repository;

import com.sudoku.oohub.domain.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department,Long> {
}
