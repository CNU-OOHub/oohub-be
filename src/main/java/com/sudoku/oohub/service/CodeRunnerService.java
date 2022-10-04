package com.sudoku.oohub.service;

import com.sudoku.oohub.dto.request.CommandDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;

@Service
public class CodeRunnerService {

    private final String pythonPath = "/usr/local/bin/python";
    private final String runTarget = "run.py";

    public Boolean writeToPy(String command) {
        BufferedWriter writer;
        try {
            System.out.println(command);
            writer = new BufferedWriter(new FileWriter("run.py"));
            writer.write(command);
            writer.close();
            System.out.println("success write");
            return true;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    // 한줄 실행
    public void runOneLine(CommandDto commandDto) throws IOException, InterruptedException {
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
        executeProcess(pythonPath, runTarget);
    }

    // 파일 실행
    public void runFile(MultipartFile file) throws IOException, InterruptedException {

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));
            BufferedWriter writer = new BufferedWriter(new FileWriter("run.py"));
            String str;
            while ((str = reader.readLine()) != null) {
                writer.write(str);
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        executeProcess(pythonPath, runTarget);
    }

    private void executeProcess(String arg1, String arg2) throws IOException, InterruptedException {
        ProcessBuilder builder = new ProcessBuilder(arg1, arg2);
        Process process = builder.start();
        int exitVal = process.waitFor();  // 자식 프로세스가 종료될 때까지 기다림
        BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8)); // 서브 프로세스가 출력하는 내용을 받기 위해
        String line;
        while ((line = br.readLine()) != null) {
            System.out.println(">>>  " + line); // 표준출력에 쓴다
        }
        if (exitVal != 0) {
            // 비정상 종료
            System.out.println("Process ended...(종료코드) ::: " + exitVal);
            System.out.println("서브 프로세스가 비정상 종료되었습니다.");
        }
    }
}
