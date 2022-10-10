package com.sudoku.oohub.service;

import com.sudoku.oohub.dto.request.SaveFileDto;
import com.sudoku.oohub.dto.request.UpdateFileNameDto;
import com.sudoku.oohub.exception.FileException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FileService {

    public String saveFile(@ModelAttribute SaveFileDto saveFileDto) throws IOException {
        String userHomeDir = System.getProperty("user.home");
        String originalPath = userHomeDir + saveFileDto.getOriginalPath();
        String newFilePath = userHomeDir + saveFileDto.getUpdatePath();
        File newFile = new File(newFilePath);

        createFile(saveFileDto, newFile);
        deleteOriginalFile(originalPath,newFilePath);

        return newFilePath;
    }

    private void createFile(SaveFileDto saveFileDto, File newFile) throws IOException {
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(saveFileDto.getMultipartFile().getInputStream(), StandardCharsets.UTF_8));

            if (!newFile.exists()) { // 파일이 존재하지 않으면
//                Files.createDirectories(Path.of(newFile.getAbsolutePath()));
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
        if (!Objects.equals(originalPath, newFilePath)){
            if (new File(originalPath).exists()) {
                Files.delete(Path.of(originalPath));
            }
        }
    }

}
