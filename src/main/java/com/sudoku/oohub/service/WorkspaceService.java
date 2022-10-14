package com.sudoku.oohub.service;

import com.sudoku.oohub.domain.Member;
import com.sudoku.oohub.dto.request.CreateMemberNameDto;
import com.sudoku.oohub.dto.response.StorageDto;
import com.sudoku.oohub.exception.NameNotFoundException;
import com.sudoku.oohub.exception.UsernameNotFoundException;
import com.sudoku.oohub.repository.MemberRepository;
import com.sudoku.oohub.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class WorkspaceService {

    private final MemberRepository memberRepository;

    public String createWorkspace(CreateMemberNameDto createMemberNameDto) throws IOException {
        String userHomeDir = System.getProperty("user.home");
        if (memberRepository.findByUsername(createMemberNameDto.getUsername()).isEmpty()) {
            throw new NameNotFoundException("username: " + createMemberNameDto.getUsername() + "을 가진 유저가 존재하지 않습니다.");
        }
        var workspaceName = memberRepository.findByUsername(createMemberNameDto.getUsername()).get().getWorkspaceName();
        String path = userHomeDir + "/" + workspaceName;
        File folder = new File(path);

        if (!folder.exists()) {
            folder.mkdir();
            return "Workspace has been created successfully!";
        } else {
            return "workspace already exists!";
        }
    }

    public String getMyWorkspace() {
        String username = SecurityUtil.getCurrentUsername().orElseThrow(
                () -> new UsernameNotFoundException("로그인이 필요한 서비스입니다."));
        Member member = memberRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        return member.getWorkspaceName();

    }

    public StorageDto getStorageCapacity() throws IOException {
        String username = SecurityUtil.getCurrentUsername()
                .orElseThrow(()-> new NameNotFoundException("현재 username을 가져올 수 없습니다."));
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new NameNotFoundException("username: "+username+" 을 가진 유저를 찾을 수 없습니다."));

        String userHomeDir = System.getProperty("user.home");
        Path path = Paths.get(userHomeDir+"/"+member.getWorkspaceName());

        long bytes = Files.walk(path)
                .filter(p -> p.toFile().isFile())
                .mapToLong(p -> p.toFile().length())
                .sum();

        return StorageDto.builder().usage(bytes).build();
    }
}
