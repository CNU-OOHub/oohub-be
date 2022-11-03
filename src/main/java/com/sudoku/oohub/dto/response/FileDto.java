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
    private Boolean isShared;
    private String organizationName;

    public static FileDto from(String fileName, List<String> contents, Boolean isShared, String organizationName){
        return new FileDto(fileName, contents,isShared, organizationName);
    }
}
