package com.sudoku.oohub.service;

import com.sudoku.oohub.converter.Converter;
import com.sudoku.oohub.domain.Member;
import com.sudoku.oohub.domain.MemberOrganization;
import com.sudoku.oohub.domain.Organization;
import com.sudoku.oohub.dto.request.CreateOrganizationDto;
import com.sudoku.oohub.dto.response.OrganizationDto;
import com.sudoku.oohub.exception.DuplicateMemberOrganizationException;
import com.sudoku.oohub.exception.NameNotFoundException;
import com.sudoku.oohub.exception.UsernameNotFoundException;
import com.sudoku.oohub.repository.MemberOrganizationRepository;
import com.sudoku.oohub.repository.MemberRepository;
import com.sudoku.oohub.repository.OrganizationRepository;
import com.sudoku.oohub.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final MemberOrganizationRepository memberOrganizationRepository;
    private final MemberRepository memberRepository;
    private final Converter converter;

    public List<OrganizationDto> findAll(){
        return organizationRepository.findAll()
                .stream().map((organization) -> converter.convertOrganizationDto(organization))
                .collect(Collectors.toList());
    }

    public OrganizationDto findByOrganizationName(String organizationName) {
        return organizationRepository.findByName(organizationName)
                .map(converter::convertOrganizationDto)
                .orElseThrow(() -> new NameNotFoundException(organizationName+"이란 그룹이 존재하지 않습니다."));
    }

    @Transactional
    public Organization save(CreateOrganizationDto organizationDto){
//        String username = SecurityUtil.getCurrentUsername().orElseThrow(
//                () -> new UsernameNotFoundException("로그인이 필요한 서비스입니다."));
//        Member member = memberRepository.findByUsername(username).orElseThrow(
//                () -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        Organization organization = Organization.builder()
                .name(organizationDto.getOrganizationName())
                .build();
        Organization savedOrganization = organizationRepository.save(organization);

//        if(memberOrganizationRepository.findByMemberIdAndOrganizationId(member.getId(), organization.getId()).isPresent()){
//            throw new DuplicateMemberOrganizationException("이미 가입된 사용자 입니다.");
//        }
//
//        MemberOrganization memberOrganization = MemberOrganization.builder()
//                .member(member)
//                .organization(savedOrganization)
//                .build();
//        memberOrganizationRepository.save(memberOrganization);
        return savedOrganization;
    }

    public String delete(String organizationName) {
        Organization organization = organizationRepository.findByName(organizationName)
                .orElseThrow(() -> new NameNotFoundException(organizationName+"이란 그룹이 존재하지 않습니다."));
        organizationRepository.delete(organization);
        return organizationName;
    }
}
