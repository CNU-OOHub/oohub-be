package com.sudoku.oohub.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaveFileDto {
    private String originalPath;
    private String updatePath;
    private String contents;
}
