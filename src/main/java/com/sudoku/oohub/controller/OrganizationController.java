package com.sudoku.oohub.controller;

import com.sudoku.oohub.domain.Organization;
import com.sudoku.oohub.dto.request.CreateOrganizationDto;
import com.sudoku.oohub.dto.response.OrganizationDto;
import com.sudoku.oohub.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class OrganizationController {

    private final OrganizationService organizationService;

    @GetMapping("/v1/organization")
    ResponseEntity<List<OrganizationDto>> viewAll(){
        return ResponseEntity.ok(organizationService.findAll());
    }

    @PostMapping("/v1/organization")
    ResponseEntity<Long> save(@RequestBody CreateOrganizationDto organizationDto) {
        return ResponseEntity.ok(organizationService.save(organizationDto).getId());
    }

    @DeleteMapping("/v1/organization/{organizationName}")
    ResponseEntity<String> delete(@PathVariable String organizationName) {
        String deletedOrganizationName = organizationService.delete(organizationName);
        return ResponseEntity.ok(deletedOrganizationName);
    }


}
