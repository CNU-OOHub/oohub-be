package com.sudoku.oohub.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateSharedFilePathDto {
    private String filePath;
}
