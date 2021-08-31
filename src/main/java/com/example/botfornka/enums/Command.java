package com.example.botfornka.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum Command {
    START("/start"),
    STOP("/stop"),
    PING("/ping"),
    STATUS("/status");

    String text;

    Command(String commandMessage) {
        this.text = commandMessage;
    }

    public static List<String> getAllCommandsMessageText() {
        return Arrays.stream(values()).map(Command::getText).collect(Collectors.toList());
    }

    public static Command getByText(String text) throws Exception {
        return Arrays.stream(Command.values()).filter(v ->
                v.getText().equals(text)).findFirst().orElseThrow(() ->
                new Exception(String.format("Unknown Command text: '%s'", text)));
    }
}