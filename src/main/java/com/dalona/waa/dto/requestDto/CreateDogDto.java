package com.dalona.waa.dto.requestDto;

import com.dalona.waa.decorator.EnumValidator;
import com.dalona.waa.domain.Dog;
import com.dalona.waa.domain.DogProfile;
import com.dalona.waa.enums.DogGender;
import com.dalona.waa.enums.DogStatus;
import com.dalona.waa.enums.PottyTraining;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Data;

@Data
public class CreateDogDto {

    /* dog entity */
    @NotNull(message = "name is required")
    @Schema(description = "이름", example = "다로나")
    private String name;

    @EnumValidator(enumClass = DogGender.class)
    @NotNull(message = "gender is required")
    @Schema(description = "성별", example = "FEMALE")
    private String gender;

    @NotNull(message = "birthDate is required")
    @Schema(description = "생일(나이)", example = "2024-03-08")
    private LocalDate birthDate;

    @NotNull(message = "birthDateIsEstimated is required")
    @Schema(description = "생일 추정 여부", example = "true")
    private Boolean birthDateIsEstimated;

    @EnumValidator(enumClass = DogStatus.class)
    @NotNull(message = "status is required")
    @Schema(description = "임보/입양 상태", example = "ADOPTED")
    private String status;

    /* dog profile entity*/
    @Schema(description = "임보/입양처 주소", example = "수원시 영통구 OO아파트 101동 101호")
    private String adoptionAddress;

    @NotNull(message = "rescueDate is required")
    @Schema(description = "구조 일시", example = "2025-02-08")
    private LocalDate rescueDate;

    @NotNull(message = "rescueLocation is required")
    @Schema(description = "구조 장소", example = "아산시 보호소")
    private String rescueLocation;

    @NotNull(message = "weight is required")
    @Schema(description = "몸무게(g)", example = "8500")
    private Integer weight;

    @Schema(description = "중성화 여부(모름: null)", example = "true")
    private Boolean neutered;

    @Schema(description = "사상충 감염 여부(모름: null)", example = "true")
    private Boolean heartworm;

    @Schema(description = "켄넬 코프 여부(모름: null)", example = "true")
    private Boolean kennelCough;

    @Schema(description = "이빨 스케일링 여부(모름: null)", example = "true")
    private Boolean dentalScaling;

    @Schema(description = "기타 건강 관련 특이사항", example = "아나플라즈마 양성이 떠서 현재 약을 먹으면서 치료중입니다.")
    private String healthNotes;

    @Min(value = 0, message = "짖음 정도는 0~5 사이 숫자로만 저장할 수 있습니다.")
    @Max(value = 5, message = "짖음 정도는 0~5 사이 숫자로만 저장할 수 있습니다.")
    @Schema(description = "짖음 정도(0~5, 모름: null)", example = "2")
    private Integer barkingLevel;

    @Min(value = 0, message = "분리불안 정도는 0~5 사이 숫자로만 저장할 수 있습니다.")
    @Max(value = 5, message = "분리불안 정도는 0~5 사이 숫자로만 저장할 수 있습니다.")
    @Schema(description = "분리불안 정도(0~5, 모름: null)", example = "2")
    private Integer separationAnxiety;

    @EnumValidator(enumClass = PottyTraining.class)
    @Schema(description = "실내/실외 배변(모름: null)", example = "OUTDOOR")
    private String pottyTraining;

    @Schema(description = "기타 생활 참고 정보", example = "손, 앉아, 엎드려, 브이, 하이파이브할 정도로 천재임.")
    private String behaviorNotes;

    @Schema(description = "구조 맥락", example = "아산시 보호소에서 안락사 직전 상태에 놓여있던 다로나를 데려왔어요.")
    private String rescueContext;

    @Schema(description = "기타 사연", example = "벌써 임보처를 3곳을 떠돌고 있어요. 이제는 진짜 평생가족을 찾아갔으면 좋겠어요.")
    private String additionalStory;

    public Dog toDogEntity(String registrationNo) {
        return Dog.builder()
                .registrationNo(registrationNo)
                .organizationId(1)
                .name(this.name)
                .gender(DogGender.valueOf(this.gender))
                .birthDate(this.birthDate)
                .birthDateIsEstimated(this.birthDateIsEstimated)
                .status(DogStatus.valueOf(this.status))
                .build();
    }

    public DogProfile toDogProfileEntity(int dogId) {
        return DogProfile.builder()
                .dogId(dogId)
                .adoptionAddress(this.adoptionAddress)
                .rescueDate(this.rescueDate)
                .rescueLocation(this.rescueLocation)
                .weight(this.weight)
                .neutered(this.neutered)
                .heartworm(this.heartworm)
                .kennelCough(this.kennelCough)
                .dentalScaling(this.dentalScaling)
                .healthNotes(this.healthNotes)
                .barkingLevel(this.barkingLevel)
                .separationAnxiety(this.separationAnxiety)
                .pottyTraining(this.pottyTraining != null ? PottyTraining.valueOf(this.pottyTraining) : null)
                .behaviorNotes(this.behaviorNotes)
                .rescueContext(this.rescueContext)
                .additionalStory(this.additionalStory)
                .createdBy(1)
                .build();
    }
}
