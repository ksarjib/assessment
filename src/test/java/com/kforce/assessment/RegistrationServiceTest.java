package com.kforce.assessment;

import com.kforce.assessment.domain.User;
import com.kforce.assessment.dto.LocationDto;
import com.kforce.assessment.dto.RegistrationResponseDto;
import com.kforce.assessment.exception.InvalidRequestException;
import com.kforce.assessment.service.RegistrationServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource(properties = { "geolocation.url=http://ip-api.com/json" })
@TestPropertySource(properties = { "validcountry=Canada" })
public class RegistrationServiceTest extends AbstractTest{

    @InjectMocks
    RegistrationServiceImpl service;

    @Mock
    private RestTemplate restTemplate;

    private MockRestServiceServer mockServer;


    private static final String BASE_URL= "http://ip-api.com/json";
    private static final String VALID_COUNTRY = "Canada";

    @Before
    public void init() {
//        mockServer = MockRestServiceServer.createServer(restTemplate);
        ReflectionTestUtils.setField(service, "locationUrl", BASE_URL);
        ReflectionTestUtils.setField(service, "validCountry", VALID_COUNTRY);
    }

    @Test
    public void registerUser_success() throws Exception {
        User user = new User();
        user.setUsername("sarjib");
        user.setPassword("Sarjib#12");
        user.setIpAddress("24.48.0.1");

        LocationDto locationDto = new LocationDto("success", "Canada", "Montreal");

        Mockito.when(restTemplate.getForObject(Mockito.anyString(), Mockito.eq(LocationDto.class)))
                .thenReturn(locationDto);
        RegistrationResponseDto response = service.registerUser(user);
        Assert.assertEquals("Welcome sarjib. You registered from Montreal city.", response.getMessage());
    }

    @Test(expected = InvalidRequestException.class)
    public void registerCountryNotCanada_thenFailure() throws Exception {
        User user = new User();
        user.setUsername("sarjib");
        user.setPassword("Sarjib#12");
        user.setIpAddress("124.48.0.1");

        LocationDto locationDto = new LocationDto("success", "South Korea", "Seoul");

        Mockito.when(restTemplate.getForObject(Mockito.anyString(), Mockito.eq(LocationDto.class)))
                .thenReturn(locationDto);
        ReflectionTestUtils.setField(service, "locationUrl", BASE_URL);
        ReflectionTestUtils.setField(service, "validCountry", VALID_COUNTRY);
        service.registerUser(user);
    }

    @Test(expected = Exception.class)
    public void registerApiCallFailed_thenFailure() throws Exception {
        User user = new User();
        user.setUsername("sarjib");
        user.setPassword("Sarjib#12");
        user.setIpAddress("1asdfasdf");

        ReflectionTestUtils.setField(service, "locationUrl", "somewrongurl");

        Mockito.when(restTemplate.getForObject(Mockito.anyString(), Mockito.eq(LocationDto.class)))
                .thenThrow(new Exception("Some issue with location API."));
        service.registerUser(user);
    }
}
