package com.example.botfornka.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;

@Service
@Slf4j
public class ScheduledPingService {

    @Value("${tracked.url}")
    private String URL;

    private Boolean isActiveTask = true;

    private final RestTemplateService restTemplateService;

    private final MailingService mailingService;

    @Autowired
    public ScheduledPingService(RestTemplateService restTemplateService, MailingService mailingService) {
        this.restTemplateService = restTemplateService;
        this.mailingService = mailingService;
    }

    @Scheduled(fixedRateString = "${fixedRate.tracking.milliseconds}")
    public void trackCurrentServerState() {
        try {
            throwExceptionIfServerError(restTemplateService.doGetRequestByURL(URL));
            notifyAboutRestored();
        } catch (ResourceAccessException | HttpServerErrorException exception) {
            log.error("CAN NOT GET RESPONSE FROM " + URL);
            notifyAboutLostConnection();
        }
    }

    private void throwExceptionIfServerError(ResponseEntity<String> response) {
        if (response.getStatusCode().is5xxServerError())
            throw new HttpServerErrorException(response.getStatusCode());
    }

    private void notifyAboutRestored() {
        if (!isActiveTask) {
            isActiveTask = true;
            log.info("-----------Connection restored-----------");
            mailingService.sendRestoredConnectionMailing();
        }
    }

    private void notifyAboutLostConnection() {
        if (isActiveTask) {
            log.error("-----------Connection LOST-----------");
            mailingService.sendLostConnectingMailing();
            isActiveTask = false;
        }
    }

}
