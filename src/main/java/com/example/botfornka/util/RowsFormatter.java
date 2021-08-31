package com.example.botfornka.util;

import com.vdurmont.emoji.EmojiParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class RowsFormatter {


    public static String getResponseSummary(ResponseEntity<String> response) {
        return "Status: " + generateStatusRow(response.getStatusCode()) + "\n" +
                "Response body: \n\n" + response.getBody();
    }

    private static String generateStatusRow(HttpStatus status) {
        return status.is2xxSuccessful() ?
                EmojiParser.parseToUnicode(":white_check_mark:" + status) :
                generateIrregularStatusRows(status);
    }

    private static String generateIrregularStatusRows(HttpStatus status) {
        return (status.is5xxServerError() || status.is4xxClientError()) ?
                EmojiParser.parseToUnicode(":x:" + status) :
                EmojiParser.parseToUnicode(":sunny:" + status);
    }
}
