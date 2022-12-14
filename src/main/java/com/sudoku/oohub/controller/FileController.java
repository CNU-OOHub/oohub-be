package com.sudoku.oohub.controller;

import com.google.gson.JsonObject;
import com.sudoku.oohub.dto.request.GetFilePathDto;
import com.sudoku.oohub.dto.request.SaveFileDto;
import com.sudoku.oohub.dto.response.DirectoryStructureDto;
import com.sudoku.oohub.dto.response.FileDto;
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

    /**
     * 파일 저장 또는 수정
     */
    @PostMapping("/v1/files")
    ResponseEntity<String> saveFile(@RequestBody SaveFileDto saveFileDto) throws IOException {
        String message = fileService.saveFile(saveFileDto);
        return ResponseEntity.ok(message);
    }

    /**
     * 파일 내용 조회
     *
     */
    @PostMapping("/v1/files/info")
    ResponseEntity<FileDto> getFile(@RequestBody GetFilePathDto getFilePathDto) throws IOException {
        FileDto fileDto = fileService.getFile(getFilePathDto);
        return ResponseEntity.ok(fileDto);
    }

    /**
     * 로컬 파일 전체 조회
     */
//   @GetMapping("/v1/files/all")
//    ResponseEntity<DirectoryStructureDto> getAllFilePath() {
//       return ResponseEntity.ok(fileService.getAllFilePath());
//   }

    /**
     * 로컬 파일 전체 조회 - json 형식
     */
   @GetMapping(value = "/v1/files/all", produces="application/json; charset=utf-8")
    ResponseEntity<String> getFileStructure() {
       return ResponseEntity.ok(fileService.getFileStructure().toString());
   }
}
