package com.sudoku.oohub.service;

import com.sudoku.oohub.dto.request.FolderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class FolderService {

    private final String userHomeDir = System.getProperty("user.home");

    public String createFolder(FolderDto folderDto) throws IOException {
        String path = userHomeDir +"/"+folderDto.getFolderPath();
        File folder = new File(path);

        if(!folder.exists()){
            folder.mkdir();
            return "Folder has been created successfully!";
        } else{
            return "Folder already exists!";
        }
    }

    public String deleteFolder(String folderName, FolderDto folderDto) throws IOException{
        String path = userHomeDir +"/"+folderDto.getFolderPath();
        File folder = new File(path);

        while (folder.exists()){
            File[] folder_list = folder.listFiles();

            for(int j=0; j< folder_list.length; j++){
                folder_list[j].delete();
                System.out.println(folder_list[j].getName()+"파일이 삭제되었습니다.");
            }
            if(folder_list.length==0 && folder.isDirectory()){
                folder.delete();
                System.out.println("폴더가 삭제되었습니다.");
            }
        }
        return folderName;
    }
}
