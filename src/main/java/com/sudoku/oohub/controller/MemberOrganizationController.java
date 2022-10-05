package com.sudoku.oohub.controller;

import com.sudoku.oohub.domain.MemberOrganization;
import com.sudoku.oohub.domain.Organization;
import com.sudoku.oohub.dto.request.CreateMemberOrganizationDto;
import com.sudoku.oohub.dto.request.CreateOrganizationDto;
import com.sudoku.oohub.dto.response.MemberOrganizationDto;
import com.sudoku.oohub.dto.response.OrganizationDto;
import com.sudoku.oohub.service.MemberOrganizationService;
import com.sudoku.oohub.service.MemberService;
import com.sudoku.oohub.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberOrganizationController {

    private final MemberOrganizationService memberOrganizationService;
    private final MemberService memberService;
    private final OrganizationService organizationService;

    @GetMapping("v1/organization/{username}/all")
    public ResponseEntity<List<OrganizationDto>> organizationsOfMember(@PathVariable String username){
        List<OrganizationDto> organizations = memberOrganizationService.findByUsername(username);
        return ResponseEntity.ok(organizations);
    }

    @PostMapping("v1/organization/{organizationName}")
    public ResponseEntity<Long> joinOrganization(@PathVariable String organizationName, @RequestBody CreateMemberOrganizationDto createMemberOrganizationDto) {
        MemberOrganizationDto memberOrganizationDto = MemberOrganizationDto.builder()
                .memberDto(memberService.findByUsername(createMemberOrganizationDto.getUsername()))
                .organizationDto(organizationService.findByOrganizationName(organizationName))
                .build();
        Long id = memberOrganizationService.save(memberOrganizationDto);
        return ResponseEntity.ok(id);
    }

}
