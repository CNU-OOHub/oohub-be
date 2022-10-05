package com.sudoku.oohub.dto.response;

import com.sudoku.oohub.domain.Department;
import com.sudoku.oohub.domain.Role;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDto {
    private Long id;
    private String username;
    private String password;
    private Role role;
    private DepartmentDto departmentDto;
}
