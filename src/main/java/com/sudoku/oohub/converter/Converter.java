package com.sudoku.oohub.converter;


import com.sudoku.oohub.domain.*;
import com.sudoku.oohub.dto.response.*;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Component
public class Converter {

    public DepartmentDto convertDepartmentDto(Department department){
        DepartmentDto departmentDto = DepartmentDto.builder()
                .id(department.getId())
                .name(department.getName())
                .build();
        return departmentDto;
    }

    private Department convertDepartment(DepartmentDto departmentDto) {
        Department department = Department.builder()
                .id(departmentDto.getId())
                .name(departmentDto.getName())
                .build();
        return department;
    }
    public OrganizationDto convertOrganizationDto(Organization organization){
        OrganizationDto organizationDto = OrganizationDto.builder()
                .id(organization.getId())
                .name(organization.getName())
                .build();
        return organizationDto;
    }

    public OrganizationDto convertOrganizationDto(Object[] object){
        OrganizationDto organizationDto = OrganizationDto.builder()
                .id(((BigInteger)object[0]).longValue())
                .name(object[1].toString())
                .build();
        return organizationDto;
    }

    public Organization convertOrganization(OrganizationDto organizationDto){
        Organization organization = Organization.builder()
                .id(organizationDto.getId())
                .name(organizationDto.getName())
                .build();
        return organization;
    }

    public MemberDto convertMemberDto(Member member) {
        MemberDto memberDto = MemberDto.builder()
                .id(member.getId())
                .username(member.getUsername())
                .password(member.getPassword())
                .role(member.getRole())
                .departmentDto(convertDepartmentDto(member.getDepartment()))
                .build();
        return memberDto;
    }

    public Member convertMember(MemberDto memberDto) {
        Member member = Member.builder()
                .id(memberDto.getId())
                .username(memberDto.getUsername())
                .password(memberDto.getPassword())
                .role(memberDto.getRole())
                .department(convertDepartment(memberDto.getDepartmentDto()))
                .build();
        return member;
    }

    public SharedFileDto convertSharedFileDto(SharedFile sharedFile) {
        List<String> contents = List.of(sharedFile.getContents().split("\\n+"));
        SharedFileDto sharedFileDto = SharedFileDto.builder()
                .id(sharedFile.getId())
                .filename(sharedFile.getFilename())
                .filepath(sharedFile.getFilepath())
                .contents(contents)
                .writer(sharedFile.getMember().getUsername())
                .build();
        return sharedFileDto;
    }

    public OrganizationMemberDto convertOrganizationMemberDto(Object[] object) {
        OrganizationMemberDto organizationMemberDto = OrganizationMemberDto.builder()
                .id(((BigInteger)object[0]).longValue())
                .username(object[1].toString())
                .department(object[2].toString())
                .build();
        return organizationMemberDto;
    }
}
