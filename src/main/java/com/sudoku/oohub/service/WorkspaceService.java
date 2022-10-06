package com.sudoku.oohub.service;

import com.sudoku.oohub.dto.request.CreateMemberNameDto;
import com.sudoku.oohub.exception.NameNotFoundException;
import com.sudoku.oohub.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.naming.NameAlreadyBoundException;
import java.io.File;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class WorkspaceService {

    private final MemberRepository memberRepository;

    public String createWorkspace(CreateMemberNameDto createMemberNameDto) throws IOException{
        String userHomeDir = System.getProperty("user.home");
        if(memberRepository.findByUsername(createMemberNameDto.getUsername()).isEmpty()){
            throw new NameNotFoundException("username: "+createMemberNameDto.getUsername()+"을 가진 유저가 존재하지 않습니다.");
        }
        var workspaceName = memberRepository.findByUsername(createMemberNameDto.getUsername()).get().getWorkspaceName();
        String path = userHomeDir +"/"+workspaceName;
        File folder = new File(path);

        if(!folder.exists()){
            folder.mkdir();
            return "Workspace has been created successfully!";
        } else{
            return "workspace already exists!";
       }
    }

}
