package com.example.botfornka.service.impl;

import com.example.botfornka.service.RestService;
import com.example.botfornka.util.RowsFormatter;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Service
public class RestServiceImpl implements RestService {

    @Value("${tracked.url}")
    @Getter
    private String TRACKED_DEFAULT_URL;

    private final RestTemplate restTemplate;

    @Autowired
    public RestServiceImpl(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public ResponseEntity<String> doGetRequestByURL(String url) throws ResourceAccessException, HttpServerErrorException {
        try {
            return restTemplate.exchange(url, HttpMethod.GET, null, String.class);
        } catch (HttpClientErrorException | HttpServerErrorException httpException) {
            return ResponseEntity.status(httpException.getRawStatusCode()).body(httpException.getResponseBodyAsString());
        }
    }

    @Override
    public String receiveDefaultResponseSummary() {
        return receiveGetResponseSummaryByUrl(TRACKED_DEFAULT_URL);
    }

    @Override
    public String receiveGetResponseSummaryByUrl(String url) {
        ResponseEntity<String> response = doGetRequestByURL(url);
        return RowsFormatter.getResponseSummary(response, url);
    }
}