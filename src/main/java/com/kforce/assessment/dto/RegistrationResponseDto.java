package com.kforce.assessment.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegistrationResponseDto {
    private String uuid;
    private String message;
}
