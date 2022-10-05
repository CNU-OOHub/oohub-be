package com.sudoku.oohub.service;

import com.sudoku.oohub.converter.Converter;
import com.sudoku.oohub.domain.Organization;
import com.sudoku.oohub.dto.request.CreateOrganizationDto;
import com.sudoku.oohub.dto.response.OrganizationDto;
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
                .orElseThrow(() -> new RuntimeException("Could not find organization by "+organizationName));
    }

    public Organization save(CreateOrganizationDto organizationDto){
        Organization organization = Organization.builder()
                .name(organizationDto.getOrganizationName())
                .build();
        return organizationRepository.save(organization);
    }

    public String delete(String organizationName) {
        Optional<Organization> organization = organizationRepository.findByName(organizationName);
        organizationRepository.delete(organization.get());
        return organizationName;
    }
}
