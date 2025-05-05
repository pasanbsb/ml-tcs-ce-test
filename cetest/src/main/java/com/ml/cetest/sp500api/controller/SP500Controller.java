package com.ml.cetest.sp500api.controller;

import com.ml.cetest.sp500api.service.SP500Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sp500")
public class SP500Controller {

    private static final Logger logger = LoggerFactory.getLogger(SP500Controller.class);

    private final SP500Service service;

    public SP500Controller(SP500Service service) {
        this.service = service;
    }

    // Endpoint to get the latest S&P 500 price
    @GetMapping("/lastprice")
    public ResponseEntity<?> getLastPrice() {
        logger.info("Fetching last S&P 500 price");
        return ResponseEntity.ok(service.getLastPrice());
    }

    // Endpoint to get the last 1-month history
    @GetMapping("/history")
    public ResponseEntity<?> getOneMonthHistory() {
        logger.info("Fetching 1-month history of S&P 500 prices");
        return ResponseEntity.ok(service.getOneMonthHistory());
    }
}
