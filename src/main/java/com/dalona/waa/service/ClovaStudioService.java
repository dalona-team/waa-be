package com.dalona.waa.service;

import com.dalona.waa.dto.ChatMessage;
import com.dalona.waa.dto.clovaDto.ClovaChatMessage;
import com.dalona.waa.dto.clovaDto.ClovaRequestBody;
import com.dalona.waa.dto.clovaDto.ClovaResponse;
import com.dalona.waa.dto.requestDto.ContentsReqDto;
import com.dalona.waa.dto.responseDto.ContentsResDto;
import com.dalona.waa.dto.responseDto.DogInfoResDto;
import com.dalona.waa.dto.responseDto.TemplateResDto;
import com.dalona.waa.enums.DogGender;
import com.dalona.waa.enums.PottyTraining;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    private static final String CLOVA_API_URL = System.getenv("CLOVA_API_URL");
    private static final String CLOVAL_API_KEY = System.getenv("CLOVA_API_KEY");

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
        sb.append("- 이름 : ").append(dogInfo.getName()).append("\n");
        sb.append("- 성별 : ").append(dogInfo.getGender().equals(DogGender.MALE) ? "수컷" : "암컷").append("\n");
        sb.append("- 몸무게 : ").append(dogInfo.getWeight() / 1000).append("kg").append("\n");
        sb.append("- 출생연월 : ").append(dogInfo.getBirthDate())
                .append(dogInfo.getBirthDateIsEstimated() ? " 추정" : "").append("\n");
        sb.append("- 구조일시 : ").append(dogInfo.getRescueDate()).append("\n");
        sb.append("- 구조 위치 : ").append(dogInfo.getRescueLocation()).append("\n");
        sb.append("- 중성화 여부 : ").append(dogInfo.getNeutered() ? "함" : "안함").append("\n");
        sb.append("- 심장사상충 감염 여부 : ").append(dogInfo.getHeartworm() ? "양성" : "음성").append("\n");
        sb.append("- 켄넬 코프 접종 여부 : ").append(dogInfo.getKennelCough() ? "맞음" : "안맞음").append("\n");
        sb.append("- 이빨 스케일링 : ").append(dogInfo.getDentalScaling() ? "함" : "안함").append("\n");
        sb.append("- 짖음 정도(1~5) : ").append(dogInfo.getBarkingLevel()).append("\n");
        sb.append("- 분리불안 정도(1~5) : ").append(dogInfo.getSeparationAnxiety()).append("\n");
        sb.append("- 배변활동 : ").append(dogInfo.getPottyTraining().equals(PottyTraining.BOTH) ? "실내배변, 실외배변 둘 다"
                : dogInfo.getPottyTraining().equals(PottyTraining.INDOOR) ? "실내배변" : "실외배변").append("\n");
        sb.append("- 성격 : ").append(dogInfo.getBehaviorNotes()).append("\n");
        sb.append("- 구조 사연 : ").append(dogInfo.getRescueContext()).append("\n");
        sb.append("- 추가 스토리 : ").append(dogInfo.getAdditionalStory()).append("\n");
        sb.append("공고 : ");

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
        headers.set("Authorization", "Bearer " + CLOVAL_API_KEY);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ClovaRequestBody> httpEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<ClovaResponse> response = restTemplate.exchange(
                CLOVA_API_URL,
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
