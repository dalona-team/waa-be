package com.dalona.waa.dto.clovaDto;

import java.util.ArrayList;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClovaRequestBody {

    private ArrayList<ClovaChatMessage> messages;
    private Double temperature;
    private Integer topK;
    private Double topP;
    private Double repeatPenalty;
    private ArrayList<String> stopBefore;
    private Integer maxTokens;
    private Boolean includeAiFilters;
    private Integer seed;
}
