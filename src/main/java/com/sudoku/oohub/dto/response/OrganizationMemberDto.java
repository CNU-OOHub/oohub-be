package com.sudoku.oohub.dto.response;

import com.sudoku.oohub.domain.Role;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrganizationMemberDto {
    private Long id;
    private String username;
    private String department;
}
