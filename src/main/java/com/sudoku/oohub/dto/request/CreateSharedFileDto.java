package com.sudoku.oohub.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateSharedFileDto {
    private String filePath;
    private MultipartFile multipartFile;
}
