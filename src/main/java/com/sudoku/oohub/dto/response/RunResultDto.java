package com.sudoku.oohub.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RunResultDto {
    private List<String> result;

    public static RunResultDto from(List<String> result){
        return new RunResultDto(result);
    }
}
