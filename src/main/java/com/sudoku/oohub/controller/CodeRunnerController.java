package com.sudoku.oohub.controller;

import com.sudoku.oohub.dto.request.CommandDto;
import com.sudoku.oohub.service.CodeRunnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class CodeRunnerController {

    private final CodeRunnerService codeRunnerService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/run/line")
    public ResponseEntity<String> runCode(@RequestBody CommandDto commandDto) throws IOException, InterruptedException {
        codeRunnerService.runOneLine(commandDto);
        return ResponseEntity.ok("성공");
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/run/file")
    public ResponseEntity<String> runCode(@ModelAttribute MultipartFile file) throws IOException, InterruptedException {
        codeRunnerService.runFile(file);
        return ResponseEntity.ok("성공");
    }
}
