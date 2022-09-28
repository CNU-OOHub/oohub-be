package com.sudoku.oohub.dto.request;

import com.sudoku.oohub.domain.Member;
import com.sudoku.oohub.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateMemberDto {
    private String username;
    private String password;
    private String departmentName;

}
