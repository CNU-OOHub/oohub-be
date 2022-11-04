package com.sudoku.oohub.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sudoku.oohub.domain.SharedFile;
import com.sudoku.oohub.exception.FileNotFoundException;
import com.sudoku.oohub.dto.request.GetFilePathDto;
import com.sudoku.oohub.dto.request.SaveFileDto;
import com.sudoku.oohub.dto.response.DirectoryStructureDto;
import com.sudoku.oohub.dto.response.FileDto;
import com.sudoku.oohub.repository.OrganizationRepository;
import com.sudoku.oohub.repository.SharedFileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;


@Service
@RequiredArgsConstructor
@Slf4j
public class FileService {

    private final SharedFileRepository sharedFileRepository;
    private final OrganizationRepository organizationRepository;

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

        log.debug("FileService: file path: " + filePath);

        if (!file.exists()) {
            throw new FileNotFoundException("파일이 존재하지 않습니다.");
        }
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String str;
        List<String> contents = new ArrayList<>();
        while ((str = reader.readLine()) != null) {
            contents.add(str);
        }

        return FileDto.from(file.getName(), contents, isSharedFile(getFilePathDto), getOrganizationName(getFilePathDto));
    }

    private boolean isSharedFile(GetFilePathDto getFilePathDto) {
        return sharedFileRepository.findByFilepath(getFilePathDto.getFilePath()).isPresent();
    }

    private String getOrganizationName(GetFilePathDto getFilePathDto) {
        var sharedFile = sharedFileRepository.findByFilepath(getFilePathDto.getFilePath());
        if (sharedFile.isEmpty()) {
            return "";
        }
        return organizationRepository.findById(sharedFile.get().getOrganization().getId()).get().getName();
    }

    /**
     * 워크 스페이스 파일 전체 조회
     */
    public DirectoryStructureDto getAllFilePath() {
        String workspaceName = workspaceService.getMyWorkspace();
        String workspaceDir = homeDir + "/" + workspaceName;

        if (!new File(workspaceDir).exists()) {
            return DirectoryStructureDto.from(Map.of());
        }

        List<String> allPathList = getAllPathList(workspaceDir);
        Map<String, ArrayList<String>> hashMap = new HashMap();

        allPathList.forEach(
                path -> {
                    path = path.replace(homeDir, "");
                    String[] folderFiles = path.split("/");
                    List<String> strings = Arrays.asList(folderFiles);
                    // 하나의 path 에 대해
                    for (int i = 1; i < strings.size() - 1; i++) {
                        String key = String.join("/", strings.subList(1, i + 1));
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
        File[] files = new File(path).listFiles();
        Arrays.stream(Objects.requireNonNull(files)).forEach(
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

    public JsonObject getFileStructure() {
        String workspaceName = workspaceService.getMyWorkspace();
        String workspaceDir = homeDir + "/" + workspaceName;

        JsonObject structure = makeJsonStructure(workspaceDir);

        return structure;
    }

    public JsonObject makeJsonStructure(String path) {
        File root = new File(path);

        JsonObject parent = new JsonObject();
        parent.addProperty("name", root.getName());
        System.out.println(root.getName());
        System.out.println(root.getPath());
        File[] files = Objects.requireNonNull(root.listFiles());
        JsonArray children = new JsonArray();
        Arrays.stream(files).forEach(
                file -> {
                    if (file.isDirectory()) {
                        var childrenFolder = makeJsonStructure(file.getPath());
                        children.add(childrenFolder);
                    } else {
                        JsonObject childrenFile = new JsonObject();
                        childrenFile.addProperty("name", file.getName());
                        children.add(childrenFile);
                    }
                }
        );
        parent.add("children", children);
        return parent;
    }
}
