package com.sudoku.oohub.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CommandUtil {

    public static List<String> executeProcess(ProcessBuilder builder) throws IOException, InterruptedException {
        Process process = builder.start();
        int exitVal = process.waitFor();  // 자식 프로세스가 종료될 때까지 기다림
        BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8)); // 서브 프로세스가 출력하는 내용을 받기 위해
        String line;
        List<String> resultList = new ArrayList<>();
        while ((line = br.readLine()) != null) {
            System.out.println(">>>  " + line); // 표준출력에 쓴다
            resultList.add(line);
        }
        if (exitVal != 0) {
            // 비정상 종료
            System.out.println("Process ended...(종료코드) ::: " + exitVal);
            System.out.println("서브 프로세스가 비정상 종료되었습니다.");
        }

        return resultList;
    }
}
