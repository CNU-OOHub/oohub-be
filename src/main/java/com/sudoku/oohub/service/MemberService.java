package com.sudoku.oohub.service;

import com.sudoku.oohub.domain.Department;
import com.sudoku.oohub.domain.Member;
import com.sudoku.oohub.domain.Role;
import com.sudoku.oohub.dto.request.CreateMemberDto;
import com.sudoku.oohub.exception.DuplicateMemberException;
import com.sudoku.oohub.repository.DepartmentRepository;
import com.sudoku.oohub.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MemberRepository memberRepository;
    private final DepartmentRepository departmentRepository;

    @Transactional
    public Long join(CreateMemberDto createMemberDto) {
        if (memberRepository.findByUsername(createMemberDto.getUsername()).orElse(null) != null) {
            throw new DuplicateMemberException("이미 가입되어 있는 유저입니다.");
        }

        // TODO -> Department 부서명 연결 필요
        Department department = Department.builder()
                .name("부서명")
                .build();

        departmentRepository.save(department);

        Member member = Member.builder()
                .username(createMemberDto.getUsername())
                .password(bCryptPasswordEncoder.encode(createMemberDto.getPassword()))
                .department(department)
                .role(Role.ROLE_USER)
                .build();

        return memberRepository.save(member).getId();
    }

}
