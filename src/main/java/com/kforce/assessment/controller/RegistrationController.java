package com.kforce.assessment.controller;

import com.kforce.assessment.domain.User;
import com.kforce.assessment.dto.RegistrationResponseDto;
import com.kforce.assessment.service.RegistrationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class RegistrationController {
    private final RegistrationService service;

    public RegistrationController(RegistrationService service) {
        this.service = service;
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<RegistrationResponseDto> register(@Valid @RequestBody User user) throws Exception {
        RegistrationResponseDto regResponse = service.registerUser(user);
        return new ResponseEntity<RegistrationResponseDto>(regResponse, HttpStatus.CREATED);
    }
}
