package com.sudoku.oohub.dto.response;

import lombok.*;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileDto {
    private String fileName;
    private List<String> contents;

    public static FileDto from(String fileName, List<String> contents){
        return new FileDto(fileName, contents);
    }
}
