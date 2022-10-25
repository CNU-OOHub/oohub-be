package com.sudoku.oohub.controller;

import com.sudoku.oohub.dto.response.ResourceUsageDto;
import com.sudoku.oohub.service.MonitoringService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MonitoringController {

    private final MonitoringService monitoringService;

    @GetMapping("v1/monitoring/resources")
    public ResponseEntity<ResourceUsageDto> getResources() throws IOException, InterruptedException {
        ResourceUsageDto computerResource = monitoringService.getComputerResource();
        return ResponseEntity.ok().body(computerResource);
    }
}
