package com.sudoku.oohub.controller;

import com.sudoku.oohub.dto.request.CreateMemberNameDto;
import com.sudoku.oohub.dto.response.MemberOrganizationDto;
import com.sudoku.oohub.dto.response.OrganizationDto;
import com.sudoku.oohub.dto.response.OrganizationMemberDto;
import com.sudoku.oohub.service.MemberOrganizationService;
import com.sudoku.oohub.service.MemberService;
import com.sudoku.oohub.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
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
    public ResponseEntity<Long> joinOrganization(@PathVariable String organizationName, @RequestBody CreateMemberNameDto createMemberOrganizationDto) {
        MemberOrganizationDto memberOrganizationDto = MemberOrganizationDto.builder()
                .memberDto(memberService.findByUsername(createMemberOrganizationDto.getUsername()))
                .organizationDto(organizationService.findByOrganizationName(organizationName))
                .build();
        Long id = memberOrganizationService.save(memberOrganizationDto);
        return ResponseEntity.ok(id);
    }

    @GetMapping("/v1/organization/{organizationName}")
    public ResponseEntity<List<OrganizationMemberDto>> organizationMembers(@PathVariable String organizationName){
        List<OrganizationMemberDto> organizationMemberDtos  = memberOrganizationService.findByOrganizationName(organizationName);
        return ResponseEntity.ok(organizationMemberDtos);
    }

    @DeleteMapping("/v1/organization/{organizationName}/{username}")
    public ResponseEntity<String> deleteOrganizationMember(@PathVariable String organizationName, @PathVariable String username){
        memberOrganizationService.deleteMember(organizationName, username);
        return ResponseEntity.ok(username);
    }

}
