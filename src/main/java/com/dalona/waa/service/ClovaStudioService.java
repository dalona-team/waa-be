package com.dalona.waa.service;

import com.dalona.waa.dto.ChatMessage;
import com.dalona.waa.dto.clovaDto.ClovaChatMessage;
import com.dalona.waa.dto.clovaDto.ClovaRequestBody;
import com.dalona.waa.dto.clovaDto.ClovaResponse;
import com.dalona.waa.dto.requestDto.ContentsReqDto;
import com.dalona.waa.dto.responseDto.ContentsResDto;
import com.dalona.waa.dto.responseDto.DogInfoResDto;
import com.dalona.waa.dto.responseDto.TemplateResDto;
import com.dalona.waa.enums.BooleanStatus;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClovaStudioService {

    private final TemplateService templateService;
    private final DogService dogService;
    private final RestTemplate restTemplate;

    @Value("${clova.api.url}")
    private String clovaApiUrl;
    @Value("${clova.api.key}")
    private String clovaApiKey;

    public ContentsResDto generateContents(ContentsReqDto contentsReqDto) {
        TemplateResDto template = templateService.getTemplateById(contentsReqDto.getTemplateId());
        DogInfoResDto dogInfo = dogService.getDogInfo(contentsReqDto.getDogId());

        ClovaRequestBody clovaRequestBody = buildRequestBody(
                template.getSystem(),
                template.getChat(),
                formatDogProfile(dogInfo)
        );
        String contents = requestChatCompletion(clovaRequestBody);

        return ContentsResDto.builder().contents(contents).build();
    }

    private String formatDogProfile(DogInfoResDto dogInfo) {
        StringBuilder sb = new StringBuilder();
        sb.append("강아지 정보 :\n");
        sb.append("\n- 이름 : ").append(dogInfo.getName());
        sb.append("\n- 성별 : ").append(dogInfo.getGender().getDescription());
        sb.append("\n- 몸무게 : ").append(dogInfo.getWeight() / 1000).append("kg");
        sb.append("\n- 출생연월 : ").append(dogInfo.getBirthDate())
                .append(dogInfo.getBirthDateIsEstimated().equals(BooleanStatus.TRUE) ? " 추정" : "");
        sb.append("\n- 구조일시 : ").append(dogInfo.getRescueDate());
        sb.append("\n- 구조 위치 : ").append(dogInfo.getRescueLocation());

        if (dogInfo.getNeutered() != null) {
            sb.append("\n- 중성화 여부 : ").append(dogInfo.getNeutered().getDescription());
        }
        if (dogInfo.getHeartworm() != null) {
            sb.append("\n- 심장사상충 감염 여부 : ").append(dogInfo.getHeartworm().getDescription());
        }
        if (dogInfo.getKennelCough() != null) {
            sb.append("\n- 켄넬 코프 여부 : ").append(dogInfo.getKennelCough().getDescription());
        }
        if (dogInfo.getDentalScaling() != null) {
            sb.append("\n- 이빨 스케일링 : ").append(dogInfo.getDentalScaling().getDescription());
        }
        if (dogInfo.getBarkingLevel() != null) {
            sb.append("\n- 짖음 정도(1~5) : ").append(dogInfo.getBarkingLevel().getDescription());
        }
        if (dogInfo.getSeparationAnxiety() != null) {
            sb.append("\n- 분리불안 정도(1~5) : ").append(dogInfo.getSeparationAnxiety().getDescription());
        }
        if (dogInfo.getPottyTraining() != null) {
            sb.append("\n- 배변활동 : ").append(dogInfo.getPottyTraining().getDescription());
        }
        if (dogInfo.getBehaviorNotes() != null && !dogInfo.getBehaviorNotes().isBlank()) {
            sb.append("\n- 성격 : ").append(dogInfo.getBehaviorNotes());
        }
        if (dogInfo.getRescueContext() != null && !dogInfo.getRescueContext().isBlank()) {
            sb.append("\n- 구조 사연 : ").append(dogInfo.getRescueContext());
        }
        if (dogInfo.getAdditionalStory() != null && !dogInfo.getAdditionalStory().isBlank()) {
            sb.append("\n- 추가 스토리 : ").append(dogInfo.getAdditionalStory());
        }

        sb.append("\n공고 : ");

        return sb.toString();
    }

    private ClovaRequestBody buildRequestBody(String system, List<ChatMessage> chat, String dogProfile) {
        ArrayList<ClovaChatMessage> messages = new ArrayList<>();
        messages.add(ClovaChatMessage.builder().role("system").content(system).build());

        for (ChatMessage message : chat) {
            messages.add(ClovaChatMessage.builder().role("user").content(message.getUser()).build());
            messages.add(ClovaChatMessage.builder().role("assistant").content(message.getUser()).build());
        }
        messages.add(ClovaChatMessage.builder().role("user").content(dogProfile).build());

        return ClovaRequestBody.builder()
                .messages(messages)
                .topP(0.8)
                .topK(0)
                .maxTokens(500)
                .temperature(0.5)
                .repeatPenalty(5.0)
                .stopBefore(new ArrayList<>())
                .includeAiFilters(true)
                .build();
    }

    private String requestChatCompletion(ClovaRequestBody requestBody) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + clovaApiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ClovaRequestBody> httpEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<ClovaResponse> response = restTemplate.exchange(
                clovaApiKey,
                HttpMethod.POST,
                httpEntity,
                ClovaResponse.class
        );
        ClovaResponse clovaResponse = response.getBody();

        if (!(clovaResponse.getStatus().getCode()).equals("2000")) {
            throw new RestClientException("Clova API 오류 발생: " + clovaResponse.getStatus().getMessage());
        }

        return clovaResponse.getResult().getMessage().getContent();
    }
}
