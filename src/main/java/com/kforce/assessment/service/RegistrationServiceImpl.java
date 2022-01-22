package com.kforce.assessment.service;

import com.kforce.assessment.domain.User;
import com.kforce.assessment.dto.LocationDto;
import com.kforce.assessment.dto.RegistrationResponseDto;
import com.kforce.assessment.exception.InvalidRequestException;
import com.kforce.assessment.shared.AppErrorMessages;
import com.kforce.assessment.shared.AppProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    @Value(AppProperties.LOCATION_URL)
    private String locationUrl;

    @Value(AppProperties.VALID_COUNTRY)
    private String validCountry;
    private final RestTemplate restTemplate;

    public RegistrationServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public RegistrationResponseDto registerUser(User user) throws Exception {
        LocationDto response;
        try {
            response = restTemplate.getForObject(locationUrl + "/" + user.getIpAddress(), LocationDto.class);
        } catch (Exception e) {
            throw new Exception("Could not fetch location");
        }
        String country = response.getCountry();
        if(!country.equalsIgnoreCase(validCountry)) {
            throw new InvalidRequestException(AppErrorMessages.INVALID_COUNTRY.replace("{{yourCountry}}", country)
            .replace("{{validCountry}}", validCountry));
        }
        UUID uuid = UUID.randomUUID();
        String uuidAsString = uuid.toString();
        String message = String.format("Welcome %s. You registered from %s city.", user.getUsername(), response.getCity());
        RegistrationResponseDto responseDto = RegistrationResponseDto.builder()
                .uuid(uuidAsString)
                .message(message).build();
        return responseDto;
    }

}
