package com.sudoku.oohub.service;

import com.sudoku.oohub.converter.Converter;
import com.sudoku.oohub.domain.Organization;
import com.sudoku.oohub.dto.request.CreateOrganizationDto;
import com.sudoku.oohub.dto.response.OrganizationDto;
import com.sudoku.oohub.exception.NameNotFoundException;
import com.sudoku.oohub.repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrganizationService {

    private final OrganizationRepository organizationRepository;
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

    public Organization save(CreateOrganizationDto organizationDto){
        Organization organization = Organization.builder()
                .name(organizationDto.getOrganizationName())
                .build();
        return organizationRepository.save(organization);
    }

    public String delete(String organizationName) {
        Organization organization = organizationRepository.findByName(organizationName)
                .orElseThrow(() -> new NameNotFoundException(organizationName+"이란 그룹이 존재하지 않습니다."));
        organizationRepository.delete(organization);
        return organizationName;
    }
}
