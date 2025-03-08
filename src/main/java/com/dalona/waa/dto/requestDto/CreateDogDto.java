package com.dalona.waa.dto.requestDto;

import com.dalona.waa.decorator.EnumValidator;
import com.dalona.waa.domain.Dog;
import com.dalona.waa.domain.DogProfile;
import com.dalona.waa.enums.DogGender;
import com.dalona.waa.enums.DogStatus;
import com.dalona.waa.enums.PottyTraining;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "성별", example = "다로나")
    private String gender;

    @NotNull(message = "birthDate is required")
    @Schema(description = "생일(나이)", example = "다로나")
    private LocalDate birthDate;

    @NotNull(message = "birthDateIsEstimated is required")
    @Schema(description = "생일 추정 여부", example = "다로나")
    private Boolean birthDateIsEstimated;

    @EnumValidator(enumClass = DogStatus.class)
    @NotNull(message = "status is required")
    @Schema(description = "임보/입양 상태", example = "다로나")
    private String status;

    /* dog profile entity*/
    @Schema(description = "임보/입양처 주소", example = "다로나")
    private String adoptionAddress;

    @NotNull(message = "rescueDate is required")
    @Schema(description = "구조 일시", example = "다로나")
    private LocalDate rescueDate;

    @NotNull(message = "rescueLocation is required")
    @Schema(description = "구조 장소", example = "다로나")
    private String rescueLocation;

    @NotNull(message = "weight is required")
    @Schema(description = "몸무게", example = "다로나")
    private Integer weight;

    @Schema(description = "중성화 여부(모름: null)", example = "다로나")
    private Boolean neutered;

    @Schema(description = "사상충 감염 여부(모름: null)", example = "다로나")
    private Boolean heartworm;

    @Schema(description = "켄넬 코프 여부(모름: null)", example = "다로나")
    private Boolean kennelCough;

    @Schema(description = "이빨 스케일링 여부(모름: null)", example = "다로나")
    private Boolean dentalScaling;

    @Schema(description = "기타 건강 관련 특이사항", example = "다로나")
    private String healthNotes;

    @Schema(description = "짖음 정도(0~5), 모름: null", example = "다로나")
    private Integer barkingLevel;

    @Schema(description = "분리불안 정도(0~5), 모름: null", example = "다로나")
    private Integer separationAnxiety;

    @EnumValidator(enumClass = PottyTraining.class)
    @Schema(description = "실내/실외 배변(모름: null)", example = "다로나")
    private String pottyTraining;

    @Schema(description = "기타 생활 참고 정보", example = "다로나")
    private String behaviorNotes;

    @Schema(description = "구조 맥락", example = "다로나")
    private String rescueContext;

    @Schema(description = "기타 사연", example = "다로나")
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
