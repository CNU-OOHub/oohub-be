package com.sudoku.oohub.controller;

import com.sudoku.oohub.dto.request.CreateMemberNameDto;
import com.sudoku.oohub.dto.response.StorageDto;
import com.sudoku.oohub.service.WorkspaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/v1/workspace/storage")
    public ResponseEntity<StorageDto> getStorageCapacity() throws IOException {
        return ResponseEntity.ok(workspaceService.getStorageCapacity());
    }

}
