package com.example.flexcity_api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class FlexcityApiApplicationTests {

    @MockBean
    private ActivationController activationController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void contextLoads() {
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void validRequest() throws Exception {
        ActivationRequest activationRequest = new ActivationRequest("2024-02-22", 100);

        List<DesactivatedAsset> assetReturn = new ArrayList<>();
        assetReturn.add(new DesactivatedAsset("A6", 70, 9.0));
        assetReturn.add(new DesactivatedAsset("A4", 20, 15.0));
        assetReturn.add(new DesactivatedAsset("A5", 12, 17.0));

        // Mock the behavior of the controller method
        when(activationController.activateAssets(any(ActivationRequest.class)))
                .thenReturn(ResponseEntity.ok(assetReturn));

        // Perform the request and assert the response
        ResultActions result = mockMvc.perform(post("/activation/activate")
                .contentType("application/json")
                .content(asJsonString(activationRequest)));

        result.andExpect(status().isOk())
                .andExpect(status().is(200))
                .andExpect(content().contentType("application/json"));
    }

    @Test
    void validRequestWithNullVolume() throws Exception {
        ActivationRequest activationRequest = new ActivationRequest("2024-02-22", 0);

        List<DesactivatedAsset> assetReturn = new ArrayList<>();

        // Mock the behavior of the controller method
        when(activationController.activateAssets(any(ActivationRequest.class)))
                .thenReturn(ResponseEntity.ok(assetReturn));

        // Perform the request and assert the response
        ResultActions result = mockMvc.perform(post("/activation/activate")
                .contentType("application/json")
                .content(asJsonString(activationRequest)));

        result.andExpect(status().isOk())
                .andExpect(status().is(200))
                .andExpect(content().contentType("application/json"));
    }

    @Test
    void validRequestWithLimitVolume() throws Exception {
        ActivationRequest activationRequest = new ActivationRequest("2024-02-23", 90);

        List<DesactivatedAsset> assetReturn = new ArrayList<>();
        assetReturn.add(new DesactivatedAsset("A6", 70, 9.0));
        assetReturn.add(new DesactivatedAsset("A4", 20, 15.0));

        // Mock the behavior of the controller method
        when(activationController.activateAssets(any(ActivationRequest.class)))
                .thenReturn(ResponseEntity.ok(assetReturn));

        // Perform the request and assert the response
        ResultActions result = mockMvc.perform(post("/activation/activate")
                .contentType("application/json")
                .content(asJsonString(activationRequest)));

        result.andExpect(status().isOk())
                .andExpect(status().is(200))
                .andExpect(content().contentType("application/json"));
    }

    @Test
    void validRequestNotEnoughVolume() throws Exception {
        ActivationRequest activationRequest = new ActivationRequest("2024-02-23", 91);

        List<DesactivatedAsset> assetReturn = new ArrayList<>();

        // Mock the behavior of the controller method
        when(activationController.activateAssets(any(ActivationRequest.class)))
                .thenReturn(ResponseEntity.ok(assetReturn));

        // Perform the request and assert the response
        ResultActions result = mockMvc.perform(post("/activation/activate")
                .contentType("application/json")
                .content(asJsonString(activationRequest)));

        result.andExpect(status().isOk())
                .andExpect(status().is(200))
                .andExpect(content().contentType("application/json"));
    }

    @Test
    void validRequestNotEnoughVolumeMax() throws Exception {
        ActivationRequest activationRequest = new ActivationRequest("2024-02-23", 1000);

        List<DesactivatedAsset> assetReturn = new ArrayList<>();

        // Mock the behavior of the controller method
        when(activationController.activateAssets(any(ActivationRequest.class)))
                .thenReturn(ResponseEntity.ok(assetReturn));

        // Perform the request and assert the response
        ResultActions result = mockMvc.perform(post("/activation/activate")
                .contentType("application/json")
                .content(asJsonString(activationRequest)));

        result.andExpect(status().isOk())
                .andExpect(status().is(200))
                .andExpect(content().contentType("application/json"));
    }

}
