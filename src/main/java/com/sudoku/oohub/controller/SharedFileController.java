package com.sudoku.oohub.controller;

import com.sudoku.oohub.dto.request.CreateSharedFileDto;
import com.sudoku.oohub.dto.response.SharedFileDto;
import com.sudoku.oohub.service.SharedFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SharedFileController {

    private final SharedFileService sharedFileService;

    @GetMapping("/v1/{organizationName}/sharedFile")
    public ResponseEntity<List<SharedFileDto>> sharedFileList(@PathVariable String organizationName){
        List<SharedFileDto> sharedFiles = sharedFileService.findAllByOrganizationName(organizationName);
        return ResponseEntity.ok(sharedFiles);
    }

    @GetMapping("/v1/{organizationName}/sharedFile/{fileName}")
    public ResponseEntity<SharedFileDto> viewSharedFile(@PathVariable String organizationName, @PathVariable String fileName){
        SharedFileDto sharedFileDto = sharedFileService.viewSharedFile(organizationName, fileName);
        return ResponseEntity.ok(sharedFileDto);
    }

    @PostMapping("/v1/{organizationName}/sharedFile")
    public ResponseEntity<SharedFileDto> sharingFile(@PathVariable String organizationName, @ModelAttribute CreateSharedFileDto createSharedFileDto) {
        SharedFileDto sharedFileDto = sharedFileService.saveSharedFile(organizationName, createSharedFileDto);
        return ResponseEntity.ok(sharedFileDto);
    }

    @DeleteMapping("/v1/{organizationName}/sharedFile/{fileName}")
    public ResponseEntity<String> stopSharingFile(@PathVariable String organizationName, @PathVariable String fileName) {
        sharedFileService.deleteFile(organizationName, fileName);
        return ResponseEntity.ok(fileName);
    }


}
