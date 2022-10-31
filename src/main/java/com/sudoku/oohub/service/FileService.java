package com.sudoku.oohub.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sudoku.oohub.domain.SharedFile;
import com.sudoku.oohub.dto.request.GetFilePathDto;
import com.sudoku.oohub.dto.request.SaveFileDto;
import com.sudoku.oohub.dto.response.DirectoryStructureDto;
import com.sudoku.oohub.dto.response.FileDto;
import com.sudoku.oohub.repository.SharedFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@RequiredArgsConstructor
public class FileService {

    private final SharedFileRepository sharedFileRepository;

    @Value("${local.home}")
    private String homeDir;
    private final WorkspaceService workspaceService;

    /**
     * 파일 저장, 수정
     */
    public String saveFile(@ModelAttribute SaveFileDto saveFileDto) throws IOException {
        String originalPath = homeDir + saveFileDto.getOriginalPath();
        String newFilePath = homeDir + saveFileDto.getUpdatePath();
        createFile(saveFileDto, newFilePath);
        deleteOriginalFile(originalPath, newFilePath);

        return newFilePath;
    }

    /**
     * 단일 파일 조회
     */
    public FileDto getFile(GetFilePathDto getFilePathDto) throws IOException {

        String filePath = homeDir + getFilePathDto.getFilePath();
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

        return FileDto.from(file.getName(), contents,isSharedFile(getFilePathDto));
    }

    private boolean isSharedFile(GetFilePathDto getFilePathDto) {
        return sharedFileRepository.findByFilepath(getFilePathDto.getFilePath()).isPresent();
    }

    /**
     * 워크 스페이스 파일 전체 조회
     */
    public DirectoryStructureDto getAllFilePath() {
        String workspaceName = workspaceService.getMyWorkspace();
        String workspaceDir = homeDir + "/" + workspaceName;

        List<String> allPathList = getAllPathList(workspaceDir);
        Map<String, ArrayList<String>> hashMap = new HashMap();

        allPathList.forEach(
                path -> {
                    path = path.replace(homeDir, "");
                    String[] folderFiles = path.split("/");
                    List<String> strings = Arrays.asList(folderFiles);
                    // 하나의 path 에 대해
                    for (int i = 1; i < strings.size() - 1; i++) {
                        String key = String.join("/",strings.subList(1,i+1));
                        String newValue = strings.get(i + 1);
                        if (hashMap.containsKey(key)) {
                            ArrayList<String> valueList = hashMap.get(key);
                            if (!valueList.contains(newValue)) {
                                valueList.add(newValue);
                                hashMap.put(key, valueList);
                            }
                        } else {
                            hashMap.put(key, new ArrayList<>(List.of(strings.get(i + 1))));
                        }
                    }
                });
        return DirectoryStructureDto.from(hashMap);
    }


    private void createFile(SaveFileDto saveFileDto, String newFilePath) throws IOException {
        try {
            File newFile = new File(newFilePath);

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(saveFileDto.getMultipartFile().getInputStream(), StandardCharsets.UTF_8));

            if (createParentAndFile(newFile)) {
                writeFile(newFile, reader);
            }
        } catch (IOException e) {
            throw new IOException();
        }

    }

    private boolean createParentAndFile(File newFile) throws IOException {
        if (!newFile.exists()) { // 파일이 존재하지 않으면
            newFile.getParentFile().mkdirs();
            newFile.createNewFile();
        }
        return true;
    }

    private void writeFile(File newFile, BufferedReader reader) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(newFile));
        String str;
        System.out.println("파일 새로 쓰는 중 ..");
        while ((str = reader.readLine()) != null) {
            writer.write(str);
            writer.newLine();
        }
        writer.close();
    }

    private void deleteOriginalFile(String originalPath, String newFilePath) throws IOException {
        if (!Objects.equals(originalPath, newFilePath)) {
            if (new File(originalPath).exists()) {
                Files.delete(Path.of(originalPath));
            }
        }
    }

    ArrayList<String> pathList = new ArrayList<>();

    private List<String> getAllPathList(String path) {
        File[] files = Objects.requireNonNull(new File(path).listFiles());
        Arrays.stream(Objects.requireNonNull(new File(path).listFiles())).forEach(
                file -> {
                    if (file.isDirectory()) {
                        getAllPathList(file.getPath());
                    } else {
                        pathList.add(file.getPath());
                    }
                }
        );
        return pathList;
    }

}
