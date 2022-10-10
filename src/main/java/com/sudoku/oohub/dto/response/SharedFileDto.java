package com.sudoku.oohub.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SharedFileDto {
    Long id;
    String contents;
    String filename;
    String filepath;
    String writer;
}
