package com.dalona.waa.dto.responseDto;

import com.dalona.waa.domain.Dog;
import com.dalona.waa.domain.DogProfile;
import com.dalona.waa.enums.BooleanStatus;
import com.dalona.waa.enums.DogGender;
import com.dalona.waa.enums.DogStatus;
import com.dalona.waa.enums.LevelStatus;
import com.dalona.waa.enums.PottyTraining;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Data
public class DogInfoResDto {

    private Integer id;

    private String registrationNo;

    private Integer organizationId;

    private String name;

    private DogGender gender;

    private LocalDate birthDate;

    private BooleanStatus birthDateIsEstimated;

    private DogStatus status;

    private String adoptionAddress;

    private LocalDate rescueDate; // 구조일시

    private String rescueLocation; // 구조 장소

    private Integer weight; // 몸무게 (g 단위)

    private BooleanStatus neutered; // 중성화 여부

    private BooleanStatus heartworm; // 사상충 감염 여부

    private BooleanStatus kennelCough; // 켄넬코프 여부

    private BooleanStatus dentalScaling; // 이빨 스케일링 여부

    private String healthNotes; // 기타 건강 특이사항

    private LevelStatus barkingLevel; // 짖음 정도 (0~5)

    private LevelStatus separationAnxiety; // 분리 불안 정도 (0~5)

    private PottyTraining pottyTraining; // 배변

    private String behaviorNotes; // 기타 생활 특이사항

    private String rescueContext; // 구조 맥락

    private String additionalStory; // 기타 사연

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private List<FileUrlResDto> imageFiles;

    public DogInfoResDto(Dog dog, DogProfile dogProfile, List<FileUrlResDto> imageFiles) {
        this.id = dog.getId();
        this.registrationNo = dog.getRegistrationNo();
        this.organizationId = dog.getOrganizationId();
        this.name = dog.getName();
        this.gender = dog.getGender();
        this.birthDate = dog.getBirthDate();
        this.birthDateIsEstimated = dog.getBirthDateIsEstimated();
        this.status = dog.getStatus();
        this.createdAt = dog.getCreatedAt();
        this.updatedAt = dog.getUpdatedAt();

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

        this.imageFiles = imageFiles;
    }
}
