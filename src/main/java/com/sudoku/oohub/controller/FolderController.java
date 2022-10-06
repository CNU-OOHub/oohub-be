package com.sudoku.oohub.controller;

import com.sudoku.oohub.dto.request.CreateMemberNameDto;
import com.sudoku.oohub.dto.request.FolderDto;
import com.sudoku.oohub.service.FolderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class FolderController {

    private final FolderService folderService;

    @PostMapping("/v1/folder")
    ResponseEntity<String> createFolder(@RequestBody FolderDto folderDto) throws IOException {
        String message = folderService.createFolder(folderDto);
        return ResponseEntity.ok(message);
    }

    @PostMapping("/v1/folder/{folderName}")
    ResponseEntity<String> deleteFolder(@PathVariable String folderName, @RequestBody FolderDto folderDto) throws IOException{
        String deletedName = folderService.deleteFolder(folderName, folderDto);
        return ResponseEntity.ok(deletedName);
    }

}
