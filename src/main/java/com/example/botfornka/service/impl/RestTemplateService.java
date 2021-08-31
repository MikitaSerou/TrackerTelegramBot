package com.example.botfornka.service.impl;

import com.example.botfornka.service.RestService;
import com.example.botfornka.util.RowsFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestTemplateService implements RestService {

    @Value("${tracked.url}")
    private String TRACKED_DEFAULT_URL;

    private final RestTemplate restTemplate;

    @Autowired
    public RestTemplateService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public ResponseEntity<String> doGetRequestByURL(String url) {
        return restTemplate.exchange(url, HttpMethod.GET, null, String.class);
    }

    @Override
    public String receiveDefaultResponseSummary() {
        return receiveGetResponseSummaryByUrl(TRACKED_DEFAULT_URL);
    }

    @Override
    public String receiveGetResponseSummaryByUrl(String url) {
        ResponseEntity<String> response = doGetRequestByURL(url);
        return RowsFormatter.getResponseSummary(response);
    }
}