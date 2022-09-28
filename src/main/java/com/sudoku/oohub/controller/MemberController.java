package com.sudoku.oohub.controller;

import com.sudoku.oohub.dto.request.CreateMemberDto;
import com.sudoku.oohub.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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



}
