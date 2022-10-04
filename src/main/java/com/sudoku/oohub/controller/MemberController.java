package com.sudoku.oohub.controller;

import com.sudoku.oohub.dto.request.CreateMemberDto;
import com.sudoku.oohub.service.MemberService;
import com.sudoku.oohub.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/v1/join")
    public ResponseEntity<Long> join(@RequestBody @Validated CreateMemberDto createMemberDto){
        Long memberId = memberService.join(createMemberDto);
        return ResponseEntity.ok(memberId);
    }

    @GetMapping("/v1/users")
    public ResponseEntity<String> test(){
        String currentUsername = SecurityUtil.getCurrentUsername().orElseGet(null);
        return ResponseEntity.ok(currentUsername);
    }

}
