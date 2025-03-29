package com.dalona.waa.dto.requestDto;

import com.dalona.waa.decorator.EnumValidator;
import com.dalona.waa.domain.Dog;
import com.dalona.waa.domain.DogProfile;
import com.dalona.waa.enums.BooleanStatus;
import com.dalona.waa.enums.DogGender;
import com.dalona.waa.enums.DogStatus;
import com.dalona.waa.enums.LevelStatus;
import com.dalona.waa.enums.PottyTraining;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
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

    @EnumValidator(enumClass = BooleanStatus.class)
    @NotNull(message = "birthDateIsEstimated is required")
    @Schema(description = "생일 추정 여부", example = "true")
    private String birthDateIsEstimated;

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

    @EnumValidator(enumClass = BooleanStatus.class)
    @Schema(description = "중성화 여부", example = "TRUE")
    private String neutered;

    @EnumValidator(enumClass = BooleanStatus.class)
    @Schema(description = "사상충 감염 여부", example = "TRUE")
    private String heartworm;

    @EnumValidator(enumClass = BooleanStatus.class)
    @Schema(description = "켄넬 코프 여부", example = "TRUE")
    private String kennelCough;

    @EnumValidator(enumClass = BooleanStatus.class)
    @Schema(description = "이빨 스케일링 여부", example = "TRUE")
    private String dentalScaling;

    @Schema(description = "기타 건강 관련 특이사항", example = "아나플라즈마 양성이 떠서 현재 약을 먹으면서 치료중입니다.")
    private String healthNotes;

    @EnumValidator(enumClass = BooleanStatus.class)
    @Schema(description = "짖음 정도(0~5)", example = "LEVEL_2")
    private String barkingLevel;

    @EnumValidator(enumClass = BooleanStatus.class)
    @Schema(description = "분리불안 정도(0~5)", example = "UNKNOWN")
    private String separationAnxiety;

    @EnumValidator(enumClass = PottyTraining.class)
    @Schema(description = "실내/실외 배변", example = "OUTDOOR")
    private String pottyTraining;

    @Schema(description = "기타 생활 참고 정보", example = "손, 앉아, 엎드려, 브이, 하이파이브할 정도로 천재임.")
    private String behaviorNotes;

    @Schema(description = "구조 맥락", example = "아산시 보호소에서 안락사 직전 상태에 놓여있던 다로나를 데려왔어요.")
    private String rescueContext;

    @Schema(description = "기타 사연", example = "벌써 임보처를 3곳을 떠돌고 있어요. 이제는 진짜 평생가족을 찾아갔으면 좋겠어요.")
    private String additionalStory;

    /* dog file */
    @Schema(description = "업로드한 강아지 이미지 파일 Id", example = "[1,2,3]")
    private List<Integer> imageFileIds;

    public Dog toDogEntity(String registrationNo) {
        return Dog.builder()
                .registrationNo(registrationNo)
                .organizationId(1)
                .name(this.name)
                .gender(DogGender.valueOf(this.gender))
                .birthDate(this.birthDate)
                .birthDateIsEstimated(BooleanStatus.valueOf(this.birthDateIsEstimated))
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
                .neutered(BooleanStatus.valueOf(this.neutered))
                .heartworm(BooleanStatus.valueOf(this.heartworm))
                .kennelCough(BooleanStatus.valueOf(this.kennelCough))
                .dentalScaling(BooleanStatus.valueOf(this.dentalScaling))
                .healthNotes(this.healthNotes)
                .barkingLevel(LevelStatus.valueOf(this.barkingLevel))
                .separationAnxiety(LevelStatus.valueOf(this.separationAnxiety))
                .pottyTraining(this.pottyTraining != null ? PottyTraining.valueOf(this.pottyTraining) : null)
                .behaviorNotes(this.behaviorNotes)
                .rescueContext(this.rescueContext)
                .additionalStory(this.additionalStory)
                .createdBy(1)
                .build();
    }
}
