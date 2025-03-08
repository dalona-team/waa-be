package com.dalona.waa.enums;

import lombok.Getter;

@Getter
public enum PottyTraining {
    OUTDOOR("실내배변"),
    INDOOR("실외배변"),
    BOTH("실내배변, 실외배변 둘 다");

    private final String description;

    PottyTraining(String description) {
        this.description = description;
    }

}
