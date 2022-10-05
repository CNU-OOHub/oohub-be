package com.sudoku.oohub.repository;

import com.sudoku.oohub.domain.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface DepartmentRepository extends JpaRepository<Department,Long> {

    Optional<Department> findByName(@Param("name") String name);

}
