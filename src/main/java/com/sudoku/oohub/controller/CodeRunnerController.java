package com.sudoku.oohub.controller;

import com.sudoku.oohub.dto.request.CommandDto;
import com.sudoku.oohub.dto.request.ContentDto;
import com.sudoku.oohub.dto.response.RunResultDto;
import com.sudoku.oohub.service.CodeRunnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CodeRunnerController {

    private final CodeRunnerService codeRunnerService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/run/line")
    public ResponseEntity<RunResultDto> runOneLine(@RequestBody CommandDto commandDto) throws IOException, InterruptedException {
        RunResultDto result = RunResultDto.from(codeRunnerService.runOneLine(commandDto));
        return ResponseEntity.ok(result);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/run/file")
    public ResponseEntity<RunResultDto> runFile(@RequestBody ContentDto contentDto) throws IOException, InterruptedException {
        RunResultDto result = RunResultDto.from(codeRunnerService.runFile(contentDto));
        return ResponseEntity.ok(result);
    }
}
