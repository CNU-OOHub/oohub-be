package com.sudoku.oohub.controller;

import com.sudoku.oohub.dto.request.CreateMemberNameDto;
import com.sudoku.oohub.service.WorkspaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class WorkspaceController {

    private final WorkspaceService workspaceService;

    @PostMapping("/v1/workspace")
    ResponseEntity<String> createWorkspace(@RequestBody CreateMemberNameDto createMemberNameDto) throws IOException {
        String message = workspaceService.createWorkspace(createMemberNameDto);
        return ResponseEntity.ok(message);
    }

}
