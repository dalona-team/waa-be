package com.dalona.waa.converter;

import com.dalona.waa.dto.ChatMessage;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.io.IOException;
import java.util.List;

@Converter
public class ChatMessageListConverter implements AttributeConverter<List<ChatMessage>, String> {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<ChatMessage> chatList) {
        try {
            return objectMapper.writeValueAsString(chatList);
        } catch (IOException e) {
            throw new RuntimeException("JSON writing error", e);
        }
    }

    @Override
    public List<ChatMessage> convertToEntityAttribute(String json) {
        try {
            return objectMapper.readValue(json, new TypeReference<List<ChatMessage>>() {});
        } catch (IOException e) {
            throw new RuntimeException("JSON reading error", e);
        }
    }
}
