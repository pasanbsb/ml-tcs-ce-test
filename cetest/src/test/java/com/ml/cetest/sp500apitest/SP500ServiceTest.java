package com.ml.cetest.sp500apitest;

import com.ml.cetest.sp500api.model.SP500Response;
import com.ml.cetest.sp500api.service.SP500Service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SP500ServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private SP500Service sp500Service;

    private final String apiKey = "test-api-key";
    private final String baseURL = "https://api.test.com";
    private final String apiURL = "%s/data?api_key=%s&limit=%d";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sp500Service = new SP500Service(restTemplate);
        sp500Service.setApiKey(apiKey);
        sp500Service.setBaseURL(baseURL);
        sp500Service.setApiURL(apiURL);
    }

    @Test
    void testGetLastPrice_Success() {
        SP500Response mockResponse = new SP500Response();
        mockResponse.setObservations(List.of(new SP500Response.Observation()));
        String expectedUrl = String.format(apiURL, baseURL, apiKey, 1);

        when(restTemplate.getForObject(expectedUrl, SP500Response.class)).thenReturn(mockResponse);

        SP500Response response = sp500Service.getLastPrice();

        assertNotNull(response);
        assertFalse(response.getObservations().isEmpty());
        verify(restTemplate, times(1)).getForObject(expectedUrl, SP500Response.class);
    }

    @Test
    void testGetLastPrice_NoData() {
        String expectedUrl = String.format(apiURL, baseURL, apiKey, 1);

        when(restTemplate.getForObject(expectedUrl, SP500Response.class)).thenReturn(null);

        RuntimeException exception = assertThrows(RuntimeException.class, sp500Service::getLastPrice);

        assertEquals("No data found for the specified date range", exception.getMessage());
        verify(restTemplate, times(1)).getForObject(expectedUrl, SP500Response.class);
    }

    @Test
    void testGetOneMonthHistory_Success() {
        SP500Response mockResponse = new SP500Response();
        mockResponse.setObservations(List.of(new SP500Response.Observation()));
        String expectedUrl = String.format(apiURL, baseURL, apiKey, 30);

        when(restTemplate.getForObject(expectedUrl, SP500Response.class)).thenReturn(mockResponse);

        SP500Response response = sp500Service.getOneMonthHistory();

        assertNotNull(response);
        assertFalse(response.getObservations().isEmpty());
        verify(restTemplate, times(1)).getForObject(expectedUrl, SP500Response.class);
    }

    @Test
    void testGetOneMonthHistory_Failure() {
        String expectedUrl = String.format(apiURL, baseURL, apiKey, 30);

        when(restTemplate.getForObject(expectedUrl, SP500Response.class)).thenReturn(null);

        RuntimeException exception = assertThrows(RuntimeException.class, sp500Service::getOneMonthHistory);

        assertEquals("Failed to fetch historical data", exception.getMessage());
        verify(restTemplate, times(1)).getForObject(expectedUrl, SP500Response.class);
    }
}