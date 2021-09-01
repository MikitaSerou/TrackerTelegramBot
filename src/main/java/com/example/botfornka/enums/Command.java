package com.example.botfornka.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.Arrays;

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

    public static Command getByText(String text) throws IllegalArgumentException {
        return Arrays.stream(Command.values()).filter(v ->
                v.getText().equals(text)).findFirst().orElseThrow(() ->
                new IllegalArgumentException(String.format("Unknown Command text: '%s'", text)));
    }
}