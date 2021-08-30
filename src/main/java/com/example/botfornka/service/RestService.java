package com.example.botfornka.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface RestService {

    ResponseEntity<String> doGetRequestByURL(String url);

    String receiveGetResponseSummaryByUrl(String url);

    HttpStatus getStatusCode(ResponseEntity<String> response);

    String getBodyAsString(ResponseEntity<String> response);
}