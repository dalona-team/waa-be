package com.dalona.waa.enums;

import lombok.Getter;

@Getter
public enum BooleanStatus {
    TRUE("YES"),
    FALSE("NO"),
    UNKNOWN("모름");

    private final String description;

    BooleanStatus(String description) {
        this.description = description;
    }
}
