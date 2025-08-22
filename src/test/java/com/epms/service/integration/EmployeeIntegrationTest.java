package com.epms.service.integration;

import com.epms.service.entity.ApiClient;
import com.epms.service.repository.ApiClientRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureWebMvc
@ActiveProfiles("test")
@Transactional
class EmployeeIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ApiClientRepository apiClientRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        
        // Create test client
        ApiClient testClient = new ApiClient();
        testClient.setClientId("test-client");
        testClient.setClientSecret("test-secret");
        testClient.setClientName("Test Client");
        testClient.setIsActive(true);
        apiClientRepository.save(testClient);
    }

    @Test
    void getEmployees_WithValidAuth_ReturnsSuccess() throws Exception {
        mockMvc.perform(get("/api/employees")
                .header("X-Client-Id", "test-client")
                .header("X-Client-Secret", "test-secret")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void getEmployees_WithoutAuth_ReturnsUnauthorized() throws Exception {
        mockMvc.perform(get("/api/employees")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getEmployees_WithInvalidAuth_ReturnsUnauthorized() throws Exception {
        mockMvc.perform(get("/api/employees")
                .header("X-Client-Id", "invalid-client")
                .header("X-Client-Secret", "invalid-secret")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getEmployeeDetails_WithValidId_ReturnsEmployeeDetails() throws Exception {
        mockMvc.perform(get("/api/employees/1")
                .header("X-Client-Id", "test-client")
                .header("X-Client-Secret", "test-secret")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void getEmployeeDetails_WithInvalidId_ReturnsNotFound() throws Exception {
        mockMvc.perform(get("/api/employees/999")
                .header("X-Client-Id", "test-client")
                .header("X-Client-Secret", "test-secret")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Employee Not Found"));
    }
}