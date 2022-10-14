package com.sudoku.oohub.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DirectoryStructureDto {
    private Map<String, ArrayList<String>>  mapResult;

    public static DirectoryStructureDto from(Map<String, ArrayList<String>> mapResult){
        return new DirectoryStructureDto(mapResult);
    }
}
