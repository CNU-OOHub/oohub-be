package com.sudoku.oohub.dto.response;

import com.sudoku.oohub.domain.Role;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenDto {
    private String token;
    private String refreshToken;
    private String username;
    private String departmentName;
    private Boolean isAdmin;

    public static TokenDto from(
            String token, String refreshToken, String username, String departmentName, Boolean isAdmin) {
        return TokenDto.builder()
                .token(token)
                .refreshToken(refreshToken)
                .username(username)
                .departmentName(departmentName)
                .isAdmin(isAdmin)
                .build();
    }
}
