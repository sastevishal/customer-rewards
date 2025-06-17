package com.retail.controller;

import com.retail.dto.CustomerRewardResponse;
import com.retail.service.RewardService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = RewardController.class)
public class RewardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RewardService rewardService;

    @Test
    @DisplayName("Test getting rewards for all customers")
    void testGetAllCustomerRewards() throws Exception {
        List<CustomerRewardResponse> mockResponse = Arrays.asList(
                new CustomerRewardResponse(1L, "John", 120),
                new CustomerRewardResponse(2L, "Jane", 80)
        );

        LocalDate startDate = LocalDate.of(2024, 3, 1);
        LocalDate endDate = LocalDate.of(2024, 6, 1);

        when(rewardService.getAllCustomerRewards(startDate, endDate)).thenReturn(mockResponse);

        mockMvc.perform(get("/api/rewards")
                        .param("startDate", startDate.toString())
                        .param("endDate", endDate.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].customerId").value(1L))
                .andExpect(jsonPath("$[0].customerName").value("John"))
                .andExpect(jsonPath("$[0].rewardPoints").value(120));
    }

    @Test
    @DisplayName("Test getting rewards for specific customer")
    void testGetCustomerRewardById() throws Exception {
        CustomerRewardResponse mockResponse = new CustomerRewardResponse(1L, "John", 150);

        LocalDate startDate = LocalDate.of(2024, 3, 1);
        LocalDate endDate = LocalDate.of(2024, 6, 1);

        when(rewardService.getCustomerRewardById(1L, startDate, endDate)).thenReturn(mockResponse);

        mockMvc.perform(get("/api/rewards/1")
                        .param("startDate", startDate.toString())
                        .param("endDate", endDate.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").value(1L))
                .andExpect(jsonPath("$.customerName").value("John"))
                .andExpect(jsonPath("$.rewardPoints").value(150));
    }

    @Test
    @DisplayName("Test validation failure: missing startDate")
    void testMissingStartDateParam() throws Exception {
        mockMvc.perform(get("/api/rewards")
                        .param("endDate", "2024-06-01")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Test validation failure: invalid date format")
    void testInvalidDateParam() throws Exception {
        mockMvc.perform(get("/api/rewards")
                        .param("startDate", "invalid-date")
                        .param("endDate", "2024-06-01")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
