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
        ProcessBuilder cpuBuilder = new ProcessBuilder("/bin/bash", "-c", "top -b -n1 | grep -Po '[0-9.]+ id' | awk '{print 100-$1}'");
        List<String> cpuUsage = executeProcess(cpuBuilder);
        // ram
        ProcessBuilder ramBuilder = new ProcessBuilder("/bin/bash", "-c", "free -h");

        List<String> ramUsage = executeProcess(ramBuilder);
//        String test = "Mem:           7.8Gi       864Mi       769Mi       342Mi       6.2Gi       6.3Gi";
        String[] split = ramUsage.get(1).substring(15).split(" {7}");
        return ResourceUsageDto.from(cpuUsage.get(0), split[0], split[1]);
    }
}
