package com.sudoku.oohub.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SharedFileDto {
    Long id;
    List<String> contents;
    String filename;
    String filepath;
    String writer;
}
