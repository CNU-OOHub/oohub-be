package com.sudoku.oohub.controller;

import com.sudoku.oohub.dto.request.ContentDto;
import com.sudoku.oohub.dto.request.CreateSharedFileDto;
import com.sudoku.oohub.dto.request.CreateSharedFilePathDto;
import com.sudoku.oohub.dto.response.SharedFileDto;
import com.sudoku.oohub.service.SharedFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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

    @PostMapping("/v1/{organizationName}/sharedFile/{fileName}/info")
    public ResponseEntity<SharedFileDto> viewSharedFile(@PathVariable String organizationName, @PathVariable String fileName, @RequestBody CreateSharedFilePathDto filePathDto){
        SharedFileDto sharedFileDto = sharedFileService.viewSharedFile(organizationName, filePathDto.getFilePath());
        return ResponseEntity.ok(sharedFileDto);
    }

    @PostMapping("/v1/{organizationName}/sharedFile")
    public ResponseEntity<SharedFileDto> sharingFile(@PathVariable String organizationName, @RequestBody CreateSharedFilePathDto filePathDto) throws IOException {
        SharedFileDto sharedFileDto = sharedFileService.saveSharedFile(organizationName, filePathDto);
        return ResponseEntity.ok(sharedFileDto);
    }

    @PostMapping("/v1/{organizationName}/sharedFile/{fileName}")
    public ResponseEntity<String> stopSharingFile(@PathVariable String organizationName, @PathVariable String fileName, @RequestBody CreateSharedFilePathDto filePathDto) {
        sharedFileService.deleteFile(organizationName, filePathDto.getFilePath());
        return ResponseEntity.ok(fileName);
    }


}
