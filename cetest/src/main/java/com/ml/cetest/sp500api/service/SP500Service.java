package com.ml.cetest.sp500api.service;

import com.ml.cetest.sp500api.model.SP500Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class SP500Service {
    private static final Logger logger = LoggerFactory.getLogger(SP500Service.class);

    @Value("${stlouis.api.key}")
    private String apiKey;

    @Value("${stlouis.base.url}")
    private String baseURL;

    @Value("${stlouis.api.url}")
    private String apiURL;

    private final RestTemplate restTemplate;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public SP500Service(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Fetch the latest S&P 500 price
    public SP500Response getLastPrice() {
        Integer limit = 1;

        String url = String.format(apiURL,
                baseURL, apiKey, 1);

        logger.info("Requesting last price from URL: {}", url);
        SP500Response response = restTemplate.getForObject(url, SP500Response.class);
        if (response == null || response.getObservations().isEmpty()) {
            logger.warn("No observations found in API response");
            throw new RuntimeException("No data found for the specified date range");
        }
        return response;
    }

    // Fetch 1-month historical S&P 500 prices
    public SP500Response getOneMonthHistory() {
        Integer limit = 30;

        String url = String.format(apiURL,
                baseURL, apiKey, limit);

        logger.info("Requesting 1-month history from URL: {}", url);
        SP500Response response = restTemplate.getForObject(url, SP500Response.class);
        if (response == null) {
            logger.error("Failed to retrieve history from external API");
            throw new RuntimeException("Failed to fetch historical data");
        }
        return response;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getBaseURL() {
        return baseURL;
    }

    public void setBaseURL(String baseURL) {
        this.baseURL = baseURL;
    }

    public String getApiURL() {
        return apiURL;
    }

    public void setApiURL(String apiURL) {
        this.apiURL = apiURL;
    }

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }
}
