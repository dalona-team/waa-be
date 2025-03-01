package com.dalona.waa.dto.responseDto;

import com.dalona.waa.domain.DogProfile;
import com.dalona.waa.enums.PottyTraining;
import java.time.LocalDate;
import lombok.Data;

@Data
public class DogProfileResDto {

    private Integer dogId;

    private String adoptionAddress;

    private LocalDate rescueDate;

    private String rescueLocation;

    private Integer weight;

    private Boolean neutered;

    private Boolean heartworm;

    private Boolean kennelCough; // 켄넬코프 여부

    private Boolean dentalScaling; // 이빨 스케일링 여부

    private String healthNotes; // 기타 건강 특이사항

    private Integer barkingLevel; // 짖음 정도 (0~5)

    private Integer separationAnxiety; // 분리 불안 정도 (0~5)

    private PottyTraining pottyTraining; // 배변

    private String behaviorNotes; // 기타 생활 특이사항

    private String rescueContext; // 구조 맥락

    private String additionalStory; // 기타 사연

    public DogProfileResDto(DogProfile dogProfile) {
        this.dogId = dogProfile.getDogId();
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
