package com.replayx.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HealthResponseDto {
    private String status;
    private String service;

    public HealthResponseDto() {
    }

    public HealthResponseDto(String status, String service) {
        this.status = status;
        this.service = service;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public static HealthResponseDtoBuilder builder() {
        return new HealthResponseDtoBuilder();
    }

    public static class HealthResponseDtoBuilder {
        private String status;
        private String service;

        public HealthResponseDtoBuilder status(String status) {
            this.status = status;
            return this;
        }

        public HealthResponseDtoBuilder service(String service) {
            this.service = service;
            return this;
        }

        public HealthResponseDto build() {
            return new HealthResponseDto(status, service);
        }
    }
}
