package com.sudoku.oohub.service;

import com.sudoku.oohub.dto.request.CreateMemberNameDto;
import com.sudoku.oohub.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@RequiredArgsConstructor
public class WorkspaceService {

    private final MemberRepository memberRepository;

    public String createWorkspace(CreateMemberNameDto createMemberNameDto){
        String userHomeDir = System.getProperty("user.home");
        var workspaceName = memberRepository.findByUsername(createMemberNameDto.getUsername()).get().getWorkspaceName();
        String path = userHomeDir +"\\"+workspaceName;
        File folder = new File(path);

        if(!folder.exists()){
            try{
                folder.mkdir();
                return "Workspace has been created successfully!";
            }
            catch (Exception e) {
                throw new RuntimeException("path:"+path+" 에 workspace 생성을 실패했습니다.");
            }
        } else{
            System.out.println("이미 ["+path+"] 에 workspace가 생성되어있습니다.");
            return "workspace already exists!";
       }
    }

}
