package com.sudoku.oohub.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateSharedFileDto {
    private String filePath;
    private String name;
    private List<String> contents;
}
