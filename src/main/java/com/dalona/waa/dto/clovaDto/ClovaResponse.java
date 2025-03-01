package com.dalona.waa.dto.clovaDto;

import lombok.Data;

@Data
public class ClovaResponse {
    private Status status;
    private Result result;


    @Data
    public static class Status {
        String code;
        String message;
    }

    @Data
    public static class Result {
        Message message;
    }

    @Data
    public static class Message {
        String role;
        String content;
    }
}
