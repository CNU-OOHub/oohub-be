package com.sudoku.oohub.service;

import com.sudoku.oohub.dto.request.CommandDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static com.sudoku.oohub.util.CommandUtil.executeProcess;

@Service
public class CodeRunnerService {

    private final String pythonPath = "/usr/bin/python3";
    private final String runTarget = "run.py";

    // 한줄 실행
    public List<String> runOneLine(CommandDto commandDto) throws IOException, InterruptedException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(runTarget));
        String command = commandDto.getCommand();
        try {
            System.out.println(command);
            writer.write(command);
            writer.close();
            System.out.println("success write");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        ProcessBuilder builder = new ProcessBuilder(pythonPath, runTarget);
        return executeProcess(builder);
    }

    // 파일 실행
    public List<String> runFile(MultipartFile file) throws IOException, InterruptedException {

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));
            BufferedWriter writer = new BufferedWriter(new FileWriter(runTarget));
            String str;
            while ((str = reader.readLine()) != null) {
                writer.write(str);
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        ProcessBuilder builder = new ProcessBuilder(pythonPath, runTarget);
        return executeProcess(builder);
    }
}
