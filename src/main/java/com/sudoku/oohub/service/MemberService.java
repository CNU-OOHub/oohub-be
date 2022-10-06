package com.sudoku.oohub.service;

import com.sudoku.oohub.converter.Converter;
import com.sudoku.oohub.domain.Department;
import com.sudoku.oohub.domain.Member;
import com.sudoku.oohub.domain.Role;
import com.sudoku.oohub.dto.request.CreateMemberDto;
import com.sudoku.oohub.dto.response.MemberDto;
import com.sudoku.oohub.exception.DuplicateMemberException;
import com.sudoku.oohub.exception.NameNotFoundException;
import com.sudoku.oohub.repository.DepartmentRepository;
import com.sudoku.oohub.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MemberRepository memberRepository;
    private final DepartmentRepository departmentRepository;
    private final Converter converter;

    @Transactional
    public Long join(CreateMemberDto createMemberDto) {
        if (memberRepository.findByUsername(createMemberDto.getUsername()).orElse(null) != null) {
            throw new DuplicateMemberException("이미 가입되어 있는 유저입니다.");
        }

        Member member = Member.builder()
                .username(createMemberDto.getUsername())
                .password(bCryptPasswordEncoder.encode(createMemberDto.getPassword()))
                .workspaceName(createMemberDto.getWorkspaceName())
                .department(departmentRepository.findByName(createMemberDto.getDepartmentName())
                        .orElseThrow(() -> new NameNotFoundException("부서명: "+createMemberDto.getDepartmentName()+" 이 존재하지 않습니다." )))
                .role(Role.ROLE_USER)
                .build();

        return memberRepository.save(member).getId();
    }

    public MemberDto findByUsername(String username) {
        return memberRepository.findByUsername(username)
                .map(converter::convertMemberDto)
                .orElseThrow(()->new NameNotFoundException("username: "+ username + "을 가진 유저가 존재하지 않습니다."));
    }
}
