package com.sudoku.oohub.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FolderDto {
    String username;
    String folderPath;
}
