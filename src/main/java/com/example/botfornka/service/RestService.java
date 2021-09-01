package com.example.botfornka.service;

import org.springframework.http.ResponseEntity;

public interface RestService {

    ResponseEntity<String> doGetRequestByURL(String url);

    String receiveGetResponseSummaryByUrl(String url);

    String receiveDefaultResponseSummary();

    String getTRACKED_DEFAULT_URL();
}