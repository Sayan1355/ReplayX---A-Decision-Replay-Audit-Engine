package com.replayx.service;

import com.replayx.dto.HealthResponseDto;
import org.springframework.stereotype.Service;

@Service
public class HealthService {

    public HealthResponseDto getHealthStatus() {
        return HealthResponseDto.builder()
                .status("UP")
                .service("ReplayX")
                .build();
    }
}
