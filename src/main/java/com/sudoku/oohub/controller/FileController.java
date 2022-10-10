package com.sudoku.oohub.controller;

import com.sudoku.oohub.dto.request.SaveFileDto;
import com.sudoku.oohub.dto.request.UpdateFileNameDto;
import com.sudoku.oohub.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class FileController {
    private final FileService fileService;

    @PostMapping("/v1/file/name")
    ResponseEntity<String> updateFileName(@RequestBody UpdateFileNameDto updateFileNameDto) {
        //TODO()
        return ResponseEntity.ok("ok");
    }

    /**
     * 파일 저장 또는 수정
     */
    @PostMapping("/v1/file")
    ResponseEntity<String> saveFile(@ModelAttribute SaveFileDto saveFileDto) throws IOException {
        String message = fileService.saveFile(saveFileDto);
        return ResponseEntity.ok(message);
    }
}
