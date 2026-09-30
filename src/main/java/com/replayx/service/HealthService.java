package com.replayx.service;

import com.replayx.dto.HealthResponseDto;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;

@Service
public class HealthService {

    private final DataSource dataSource;

    public HealthService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public HealthResponseDto getHealthStatus() {
        boolean isDbConnected = checkDatabaseConnection();
        return HealthResponseDto.builder()
                .status(isDbConnected ? "UP" : "DOWN")
                .service("ReplayX")
                .build();
    }

    private boolean checkDatabaseConnection() {
        if (dataSource == null) {
            return false;
        }
        try (Connection connection = dataSource.getConnection()) {
            return connection != null && !connection.isClosed() && connection.isValid(2);
        } catch (Exception e) {
            return false;
        }
    }
}
