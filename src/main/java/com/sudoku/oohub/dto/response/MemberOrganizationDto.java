package com.sudoku.oohub.dto.response;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberOrganizationDto {
    private MemberDto memberDto;
    private OrganizationDto organizationDto;
}
