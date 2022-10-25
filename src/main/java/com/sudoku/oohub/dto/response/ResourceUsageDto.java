package com.sudoku.oohub.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResourceUsageDto {
    String cpuUsagePercent;
    String totalRamUsage;
    String usedRamUsage;

    public static ResourceUsageDto from(String cpuUsagePercent, String totalRamUsage, String usedRamUsage){
        return new ResourceUsageDto(cpuUsagePercent, totalRamUsage, usedRamUsage);
    }
}
