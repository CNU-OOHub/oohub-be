package com.sudoku.oohub.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateFileNameDto {
    private String originalFilePath;
    private String updateFilePath;
}
