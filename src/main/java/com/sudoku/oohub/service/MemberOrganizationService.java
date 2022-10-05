package com.sudoku.oohub.service;

import com.sudoku.oohub.converter.Converter;
import com.sudoku.oohub.domain.MemberOrganization;
import com.sudoku.oohub.dto.response.MemberDto;
import com.sudoku.oohub.dto.response.MemberOrganizationDto;
import com.sudoku.oohub.dto.response.OrganizationDto;
import com.sudoku.oohub.repository.MemberOrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberOrganizationService {

    private final MemberOrganizationRepository repository;
    private final MemberService memberService;
    private final Converter converter;

    public Long save(MemberOrganizationDto memberOrganizationDto) {
        var member = converter.convertMember(memberOrganizationDto.getMemberDto());
        var organization = converter.convertOrganization(memberOrganizationDto.getOrganizationDto());
        MemberOrganization memberOrganization = MemberOrganization.builder()
                .member(member)
                .organization(organization)
                .build();
        return repository.save(memberOrganization).getId();
    }

    public List<OrganizationDto> findByUsername(String username){
        MemberDto memberDto = memberService.findByUsername(username);
        return repository.findAllByMemberId(memberDto.getId())
                .stream().map(converter::convertOrganizationDto).collect(Collectors.toList());
    }

}
