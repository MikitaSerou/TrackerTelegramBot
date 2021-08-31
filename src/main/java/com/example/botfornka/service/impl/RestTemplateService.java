package com.example.botfornka.service.impl;

import com.example.botfornka.service.RestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestTemplateService implements RestService {

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
    public String receiveGetResponseSummaryByUrl(String url) {
        ResponseEntity<String> response = doGetRequestByURL(url);
        return "Status: " + getStatusCode(response) +
                "\nBody: \n" + getBodyAsString(response);
    }

    @Override
    public HttpStatus getStatusCode(ResponseEntity<String> response) {
        return response.getStatusCode();
    }

    @Override
    public String getBodyAsString(ResponseEntity<String> response) {
        String responseBody = response.getBody();
        if (responseBody != null && !responseBody.isBlank()) {
            return response.getBody();
        }
        return "Response body is missing";
    }
}