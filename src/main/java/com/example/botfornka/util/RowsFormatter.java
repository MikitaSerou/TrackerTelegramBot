package com.example.botfornka.util;

import com.vdurmont.emoji.EmojiParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class RowsFormatter {

    public static String getResponseSummary(ResponseEntity<String> response) {
        return "STATUS: " + generateStatusRow(response.getStatusCode()) + "\n" +
                "\nHEADERS:\n" + response.getHeaders() +
                "\n\nBODY:\n" + response.getBody();
    }

    private static String generateStatusRow(HttpStatus status) {
        return status.is2xxSuccessful() ?
                EmojiParser.parseToUnicode(status + ":white_check_mark:") :
                generateIrregularStatusRows(status);
    }

    private static String generateIrregularStatusRows(HttpStatus status) {
        return (status.is5xxServerError() || status.is4xxClientError()) ?
                EmojiParser.parseToUnicode(status + ":x:") :
                EmojiParser.parseToUnicode(status + ":sunny:");
    }
}
