package com.sudoku.oohub.converter;


import com.sudoku.oohub.domain.Department;
import com.sudoku.oohub.domain.Member;
import com.sudoku.oohub.domain.MemberOrganization;
import com.sudoku.oohub.domain.Organization;
import com.sudoku.oohub.dto.response.DepartmentDto;
import com.sudoku.oohub.dto.response.MemberDto;
import com.sudoku.oohub.dto.response.OrganizationDto;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Component;

import java.math.BigInteger;

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

}
