package com.sudoku.oohub.service;

import com.sudoku.oohub.converter.Converter;
import com.sudoku.oohub.domain.Member;
import com.sudoku.oohub.domain.MemberOrganization;
import com.sudoku.oohub.domain.Organization;
import com.sudoku.oohub.dto.response.MemberDto;
import com.sudoku.oohub.dto.response.MemberOrganizationDto;
import com.sudoku.oohub.dto.response.OrganizationDto;
import com.sudoku.oohub.dto.response.OrganizationMemberDto;
import com.sudoku.oohub.exception.DuplicateMemberOrganizationException;
import com.sudoku.oohub.exception.NameNotFoundException;
import com.sudoku.oohub.repository.MemberOrganizationRepository;
import com.sudoku.oohub.repository.MemberRepository;
import com.sudoku.oohub.repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberOrganizationService {

    private final MemberOrganizationRepository repository;
    private final MemberRepository memberRepository;
    private final OrganizationRepository organizationRepository;
    private final Converter converter;

    public Long save(MemberOrganizationDto memberOrganizationDto) {
        var member = converter.convertMember(memberOrganizationDto.getMemberDto());
        var organization = converter.convertOrganization(memberOrganizationDto.getOrganizationDto());
        MemberOrganization memberOrganization = MemberOrganization.builder()
                .member(member)
                .organization(organization)
                .build();
        if(repository.findByMemberIdAndOrganizationId(member.getId(), organization.getId()).isPresent()){
            throw new DuplicateMemberOrganizationException("이미 가입된 사용자 입니다.");
        }
        return repository.save(memberOrganization).getId();
    }

    public List<OrganizationDto> findByUsername(String username){
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(()-> new NameNotFoundException("username: "+username+"을 가진 유저가 존재하지 않습니다."));
        return repository.findAllByMemberId(member.getId())
                .stream().map(converter::convertOrganizationDto).collect(Collectors.toList());
    }

    public List<OrganizationMemberDto> findByOrganizationName(String organizationName) {
        Organization organization =organizationRepository.findByName(organizationName)
                .orElseThrow(() -> new NameNotFoundException("organization name"+ organizationName + "의 organization이 존재하지 않습니다."));

        return repository.findAllByOrganizationId(organization.getId())
                .stream().map(converter::convertOrganizationMemberDto).collect(Collectors.toList());
    }

    public void deleteMember(String organizationName, String username) {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(()-> new NameNotFoundException("username: "+username+"을 가진 유저가 존재하지 않습니다."));
        Organization organization =organizationRepository.findByName(organizationName)
                .orElseThrow(() -> new NameNotFoundException("organization name"+ organizationName + "의 organization이 존재하지 않습니다."));
        repository.deleteByMemberIdAndOrganizationId(member.getId(), organization.getId());
    }
}
