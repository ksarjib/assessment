package com.kforce.assessment.service;

import com.kforce.assessment.domain.User;
import com.kforce.assessment.dto.RegistrationResponseDto;

public interface RegistrationService {
    RegistrationResponseDto registerUser(User user) throws Exception;
}
