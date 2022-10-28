package com.sudoku.oohub.service;

import com.sudoku.oohub.dto.response.ResourceUsageDto;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.sudoku.oohub.util.CommandUtil.executeProcess;

@Service
public class MonitoringService {

    public ResourceUsageDto getComputerResource() throws IOException, InterruptedException {
        // cpu
        List<String> cpuUsage = executeProcess(
                new ProcessBuilder("/bin/bash", "-c", "top -b -n1 | grep -Po '[0-9.]+ id' | awk '{print 100-$1}'"));
        // ram
        List<String> ramUsage = executeProcess(
                new ProcessBuilder("/bin/bash", "-c", "free -h"));

        String[] split = ramUsage.get(1).substring(15).split(" {7}");
        String ramTotal = split[0].substring(0, split[0].length() - 2);
        String ramUsed = split[0].substring(0, split[0].length() - 2);
        return ResourceUsageDto.from(cpuUsage.get(0), ramTotal, ramUsed);
    }
}
