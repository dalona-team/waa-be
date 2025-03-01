package com.dalona.waa.dto.clovaDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClovaChatMessage {

    private String role;
    private String content;
}
