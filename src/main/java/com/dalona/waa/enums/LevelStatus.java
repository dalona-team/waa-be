package com.dalona.waa.enums;

import lombok.Getter;

@Getter
public enum LevelStatus {
    LEVEL_0("없음"),
    LEVEL_1("약함"),
    LEVEL_2("보통"),
    LEVEL_3("강함"),
    LEVEL_4("매우 강함"),
    LEVEL_5("최강"),
    UNKNOWN("모름");

    private final String description;

    LevelStatus(String description) {
        this.description = description;
    }

}
