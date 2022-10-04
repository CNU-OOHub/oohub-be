package com.sudoku.oohub.controller;

import com.sudoku.oohub.dto.request.CommandDto;
import com.sudoku.oohub.dto.response.RunResultDto;
import com.sudoku.oohub.service.CodeRunnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CodeRunnerController {

    private final CodeRunnerService codeRunnerService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/run/line")
    public ResponseEntity<RunResultDto> runCode(@RequestBody CommandDto commandDto) throws IOException, InterruptedException {
        RunResultDto result = RunResultDto.from(codeRunnerService.runOneLine(commandDto));
        return ResponseEntity.ok(result);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/run/file")
    public ResponseEntity<RunResultDto> runCode(@ModelAttribute MultipartFile file) throws IOException, InterruptedException {
        RunResultDto result = RunResultDto.from(codeRunnerService.runFile(file));
        return ResponseEntity.ok(result);
    }
}
