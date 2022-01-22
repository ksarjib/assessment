package com.kforce.assessment;

import com.kforce.assessment.controller.RegistrationController;
import com.kforce.assessment.domain.User;
import com.kforce.assessment.dto.RegistrationResponseDto;
import com.kforce.assessment.exception.MyControllerAdvice;
import com.kforce.assessment.service.RegistrationService;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
public class RegistrationControllerTest extends AbstractTest {
    private MockMvc mockMvc;

    @Mock
    RegistrationService service;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    RegistrationController controller;

    private static final String BASE_URL= "http://ip-api.com/json";

    @Before
    public void setup() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new MyControllerAdvice())
                .build();
    }

    @Test
    public void registerUserValid_thenSuccess() throws Exception {
        String uri = "/auth/register";
        User user = new User();
        user.setUsername("sarjib");
        user.setPassword("Sarjib#1");
        user.setIpAddress("24.48.0.1");
        RegistrationResponseDto response = RegistrationResponseDto.builder()
                .message("Welcome sarjib. You registered from Montreal city.")
                .build();
        Mockito.when(service.registerUser(Mockito.any(User.class))).thenReturn(response);
        String inputJson = super.mapToJson(user);
        mockMvc.perform(MockMvcRequestBuilders
                .post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(inputJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is("Welcome sarjib. You registered from Montreal city.")));
      }

    @Test
    public void registerUsernameNull_thenFailure() throws Exception {
        String uri = "/auth/register";
        User user = new User();
        user.setPassword("Sarjib#1");
        user.setIpAddress("24.48.0.1");
        RegistrationResponseDto response = RegistrationResponseDto.builder()
                .message("Username is required.")
                .build();
        Mockito.when(service.registerUser(Mockito.any(User.class))).thenReturn(response);
        String inputJson = super.mapToJson(user);
        mockMvc.perform(MockMvcRequestBuilders
                .post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(inputJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorMessage", Matchers.is("Username is required.")))
                .andReturn();
    }

    @Test
    public void registerPasswordNull_thenFailure() throws Exception {
        String uri = "/auth/register";
        User user = new User();
        user.setUsername("sarjib");
        user.setIpAddress("24.48.0.1");
        RegistrationResponseDto response = RegistrationResponseDto.builder()
                .message("Password is required.")
                .build();
        Mockito.when(service.registerUser(Mockito.any(User.class))).thenReturn(response);
        String inputJson = super.mapToJson(user);
        mockMvc.perform(MockMvcRequestBuilders
                .post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(inputJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorMessage", Matchers.is("Password is required.")));
    }

    @Test
    public void registerPasswordInvalid_oneCapitalLetterRequired_thenFailure() throws Exception {
        String uri = "/auth/register";
        User user = new User();
        user.setUsername("sarjib");
        user.setPassword("sarjib1.");
        user.setIpAddress("24.48.0.1");
        RegistrationResponseDto response = RegistrationResponseDto.builder()
                .message("Password must contain one capital letter, one number and one special character among '$ # % .'")
                .build();
        Mockito.when(service.registerUser(Mockito.any(User.class))).thenReturn(response);
        String inputJson = super.mapToJson(user);
        mockMvc.perform(MockMvcRequestBuilders
                .post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(inputJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorMessage", Matchers.is("Password must contain one capital letter, one number and one special character among '$ # % .'")));
    }

    @Test
    public void registerPasswordInvalid_oneNumberRequired_thenFailure() throws Exception {
        String uri = "/auth/register";
        User user = new User();
        user.setUsername("sarjib");
        user.setPassword("sarjibb.");
        user.setIpAddress("24.48.0.1");
        RegistrationResponseDto response = RegistrationResponseDto.builder()
                .message("Password must contain one capital letter, one number and one special character among '$ # % .'")
                .build();
        Mockito.when(service.registerUser(Mockito.any(User.class))).thenReturn(response);
        String inputJson = super.mapToJson(user);
        mockMvc.perform(MockMvcRequestBuilders
                .post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(inputJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorMessage", Matchers.is("Password must contain one capital letter, one number and one special character among '$ # % .'")));
    }

    @Test
    public void registerPasswordInvalid_oneSpecialCharacterRequired_thenFailure() throws Exception {
        String uri = "/auth/register";
        User user = new User();
        user.setUsername("sarjib");
        user.setPassword("Sarjibb1");
        user.setIpAddress("24.48.0.1");
        RegistrationResponseDto response = RegistrationResponseDto.builder()
                .message("Password must contain one capital letter, one number and one special character among '$ # % .'")
                .build();
        Mockito.when(service.registerUser(Mockito.any(User.class))).thenReturn(response);
        String inputJson = super.mapToJson(user);
        mockMvc.perform(MockMvcRequestBuilders
                .post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(inputJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorMessage", Matchers.is("Password must contain one capital letter, one number and one special character among '$ # % .'")));
    }

    @Test
    public void registerIpAddressNull_thenFailure() throws Exception {
        String uri = "/auth/register";
        User user = new User();
        user.setUsername("sarjib");
        user.setPassword("Sarjib#12");
        RegistrationResponseDto response = RegistrationResponseDto.builder()
                .message("IpAddress is required.")
                .build();
        Mockito.when(service.registerUser(Mockito.any(User.class))).thenReturn(response);
        String inputJson = super.mapToJson(user);
        mockMvc.perform(MockMvcRequestBuilders
                .post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(inputJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorMessage", Matchers.is("IpAddress is required.")));
    }

}
