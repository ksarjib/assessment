package com.kforce.assessment.domain;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class User {
    @NotNull(message = "Username is required.")
    @NotBlank(message = "Username is required.")
    private String username;

    @NotNull(message = "Password is required.")
    @NotBlank(message = "Password is required.")
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[_$#%.]).{8,}$", message = "Password must contain one capital letter, one number and one special character among '$ # % .'")
    private String password;

    @NotNull(message = "IpAddress is required.")
    @NotBlank(message = "IpAddress is required.")
    private String ipAddress;
}
