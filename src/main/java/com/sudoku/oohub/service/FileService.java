package com.sudoku.oohub.service;

import com.sudoku.oohub.dto.request.GetFilePathDto;
import com.sudoku.oohub.dto.request.SaveFileDto;
import com.sudoku.oohub.dto.response.FileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FileService {

    private final String userHomeDir = System.getProperty("user.home");
    private final WorkspaceService workspaceService;

    // 파일 저장, 수정
    public String saveFile(@ModelAttribute SaveFileDto saveFileDto) throws IOException {
        String originalPath = userHomeDir + saveFileDto.getOriginalPath();
        String newFilePath = userHomeDir + saveFileDto.getUpdatePath();
        createFile(saveFileDto, newFilePath);
        deleteOriginalFile(originalPath, newFilePath);

        return newFilePath;
    }

    // 파일 조회
    public FileDto getFile(GetFilePathDto getFilePathDto) throws IOException {
        String filePath = userHomeDir + getFilePathDto.getFilePath();
        File file = new File(filePath);

        if (!file.exists()) {
            throw new FileNotFoundException("파일이 존재하지 않습니다.");
        }
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String str;
        List<String> contents = new ArrayList<>();
        while ((str = reader.readLine()) != null) {
            contents.add(str);
        }

        return FileDto.from(file.getName(), contents);
    }

    public void getAllFilePath() {
        String workspaceName = workspaceService.getMyWorkspace();
        String workspaceDir = userHomeDir + "/" + workspaceName;

        List<String> allPathList = getAllPathList(workspaceDir);

        allPathList.forEach(
                System.out::println
        );
    }


    private void createFile(SaveFileDto saveFileDto, String newFilePath) throws IOException {
        try {
            File newFile = new File(newFilePath);

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(saveFileDto.getMultipartFile().getInputStream(), StandardCharsets.UTF_8));

            if (!newFile.exists()) { // 파일이 존재하지 않으면
                newFile.getParentFile().mkdirs();
                newFile.createNewFile();
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(newFile));
            String str;
            while ((str = reader.readLine()) != null) {
                writer.write(str);
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            throw new IOException();
        }

    }

    private void deleteOriginalFile(String originalPath, String newFilePath) throws IOException {
        if (!Objects.equals(originalPath, newFilePath)) {
            if (new File(originalPath).exists()) {
                Files.delete(Path.of(originalPath));
            }
        }
    }

    ArrayList<String> pathList = new ArrayList<String>();
    private List<String> getAllPathList(String path) {
        File[] files = Objects.requireNonNull(new File(path).listFiles());
        Arrays.stream(Objects.requireNonNull(new File(path).listFiles())).forEach(
                file -> {
                    if (file.isDirectory()) {
                        getAllPathList(file.getPath());
                    } else{
                        pathList.add(file.getPath());
//                        System.out.println(file.getPath());
                    }
                }
        );
        return pathList;
    }

}
