package com.dalona.waa.enums;

import lombok.Getter;

@Getter
public enum DogGender {
    MALE("수컷"),
    FEMALE("암컷");

    private final String description;

    DogGender(String description) {
        this.description = description;
    }
}
