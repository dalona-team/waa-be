package com.dalona.waa.dto.responseDto;

import com.dalona.waa.domain.Dog;
import com.dalona.waa.domain.DogProfile;
import com.dalona.waa.enums.DogGender;
import com.dalona.waa.enums.DogStatus;
import com.dalona.waa.enums.PottyTraining;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class DogInfoResDto {

    private Integer id;

    private Integer organizationId;

    private String name;

    private DogGender gender;

    private LocalDate birthDate;

    private Boolean birthDateIsEstimated;

    private DogStatus status;

    private String adoptionAddress;

    private LocalDate rescueDate; // 구조일시

    private String rescueLocation; // 구조 장소

    private Integer weight; // 몸무게 (g 단위)

    private Boolean neutered; // 중성화 여부

    private Boolean heartworm; // 사상충 감염 여부

    private Boolean kennelCough; // 켄넬코프 여부

    private Boolean dentalScaling; // 이빨 스케일링 여부

    private String healthNotes; // 기타 건강 특이사항

    private Integer barkingLevel; // 짖음 정도 (0~5)

    private Integer separationAnxiety; // 분리 불안 정도 (0~5)

    private PottyTraining pottyTraining; // 배변

    private String behaviorNotes; // 기타 생활 특이사항

    private String rescueContext; // 구조 맥락

    private String additionalStory; // 기타 사연

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Integer createdBy;

    private Integer updatedBy;

    public DogInfoResDto(Dog dog, DogProfile dogProfile) {
        this.id = dog.getId();
        this.organizationId = dog.getOrganizationId();
        this.name = dog.getName();
        this.gender = dog.getGender();
        this.birthDate = dog.getBirthDate();
        this.birthDateIsEstimated = dog.getBirthDateIsEstimated();
        this.status = dog.getStatus();
        this.createdAt = dog.getCreatedAt();
        this.updatedAt = dog.getUpdatedAt();
        this.createdBy = dog.getCreatedBy();
        this.updatedBy = dog.getUpdatedBy();

        this.adoptionAddress = dogProfile.getAdoptionAddress();
        this.rescueDate = dogProfile.getRescueDate();
        this.rescueLocation = dogProfile.getRescueLocation();
        this.weight = dogProfile.getWeight();
        this.neutered = dogProfile.getNeutered();
        this.heartworm = dogProfile.getHeartworm();
        this.kennelCough = dogProfile.getKennelCough();
        this.dentalScaling = dogProfile.getDentalScaling();
        this.healthNotes = dogProfile.getHealthNotes();
        this.barkingLevel = dogProfile.getBarkingLevel();
        this.separationAnxiety = dogProfile.getSeparationAnxiety();
        this.pottyTraining = dogProfile.getPottyTraining();
        this.behaviorNotes = dogProfile.getBehaviorNotes();
        this.rescueContext = dogProfile.getRescueContext();
        this.additionalStory = dogProfile.getAdditionalStory();
    }

}
