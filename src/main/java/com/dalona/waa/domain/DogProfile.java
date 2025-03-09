package com.dalona.waa.domain;

import com.dalona.waa.enums.BooleanStatus;
import com.dalona.waa.enums.LevelStatus;
import com.dalona.waa.enums.PottyTraining;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "dog_profile")
public class DogProfile {

    @Id
    @Column(name = "dog_id")
    private Integer dogId;

    /* 기본 정보 */
    @Column(name = "adoption_address")
    private String adoptionAddress;

    @Column(name = "rescue_date", nullable = false)
    private LocalDate rescueDate; // 구조일시

    @Column(name = "rescue_location", nullable = false)
    private String rescueLocation; // 구조 장소

    @Column(nullable = false)
    private Integer weight; // 몸무게 (g 단위)

    @Enumerated(EnumType.STRING)
    private BooleanStatus neutered; // 중성화 여부

    /* 건강 정보 */
    @Enumerated(EnumType.STRING)
    private BooleanStatus heartworm; // 사상충 감염 여부

    @Enumerated(EnumType.STRING)
    @Column(name = "kennel_cough")
    private BooleanStatus kennelCough; // 켄넬코프 여부

    @Enumerated(EnumType.STRING)
    @Column(name = "dental_scaling")
    private BooleanStatus dentalScaling; // 이빨 스케일링 여부

    @Column(name = "health_notes", columnDefinition = "TEXT")
    private String healthNotes; // 기타 건강 특이사항

    /* 반려인 생활 참고 정보 */
    @Enumerated(EnumType.STRING)
    @Column(name = "barking_level")
    private LevelStatus barkingLevel; // 짖음 정도 (0~5)

    @Enumerated(EnumType.STRING)
    @Column(name = "separation_anxiety")
    private LevelStatus separationAnxiety; // 분리 불안 정도 (0~5)

    @Enumerated(EnumType.STRING)
    @Column(name = "potty_training")
    private PottyTraining pottyTraining; // 배변

    @Column(name = "behavior_notes", columnDefinition = "TEXT")
    private String behaviorNotes; // 기타 생활 특이사항

    /* 사연/스토리 */
    @Column(name = "rescue_context", columnDefinition = "TEXT")
    private String rescueContext; // 구조 맥락

    @Column(name = "additional_story", columnDefinition = "TEXT")
    private String additionalStory; // 기타 사연

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "created_by", nullable = false)
    private Integer createdBy;

    @Column(name = "updated_by")
    private Integer updatedBy;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    @Builder
    public DogProfile(
            Integer dogId, String adoptionAddress, LocalDate rescueDate, String rescueLocation,
            Integer weight, BooleanStatus neutered, BooleanStatus heartworm, BooleanStatus kennelCough,
            BooleanStatus dentalScaling, String healthNotes, LevelStatus barkingLevel,
            LevelStatus separationAnxiety, PottyTraining pottyTraining, String behaviorNotes,
            String rescueContext, String additionalStory, Integer createdBy
    ) {
        this.dogId = dogId;
        this.adoptionAddress = adoptionAddress;
        this.rescueDate = rescueDate;
        this.rescueLocation = rescueLocation;
        this.weight = weight;
        this.neutered = neutered;
        this.heartworm = heartworm;
        this.kennelCough = kennelCough;
        this.dentalScaling = dentalScaling;
        this.healthNotes = healthNotes;
        this.barkingLevel = barkingLevel;
        this.separationAnxiety = separationAnxiety;
        this.pottyTraining = pottyTraining;
        this.behaviorNotes = behaviorNotes;
        this.rescueContext = rescueContext;
        this.additionalStory = additionalStory;
        this.createdBy = createdBy;
    }

    public void update(
            String adoptionAddress, LocalDate rescueDate, String rescueLocation,
            Integer weight, String neutered, String heartworm, String kennelCough,
            String dentalScaling, String healthNotes, String barkingLevel,
            String separationAnxiety, String pottyTraining, String behaviorNotes,
            String rescueContext, String additionalStory, Integer updatedBy
    ) {
        this.adoptionAddress = adoptionAddress;
        if (rescueDate != null) this.rescueDate = rescueDate;
        if (rescueLocation != null) this.rescueLocation = rescueLocation;
        if (weight != null) this.weight = weight;
        if (neutered != null) this.neutered = BooleanStatus.valueOf(neutered);
        if (heartworm != null) this.heartworm = BooleanStatus.valueOf(heartworm);
        if (kennelCough != null) this.kennelCough = BooleanStatus.valueOf(kennelCough);
        if (dentalScaling != null) this.dentalScaling = BooleanStatus.valueOf(dentalScaling);
        if (healthNotes != null) this.healthNotes = healthNotes;
        if (barkingLevel != null) this.barkingLevel = LevelStatus.valueOf(barkingLevel);
        if (separationAnxiety != null) this.separationAnxiety = LevelStatus.valueOf(separationAnxiety);
        if (pottyTraining != null) this.pottyTraining = PottyTraining.valueOf(pottyTraining);
        if (behaviorNotes != null) this.behaviorNotes = behaviorNotes;
        if (rescueContext != null) this.rescueContext = rescueContext;
        if (additionalStory != null) this.additionalStory = additionalStory;
        if (updatedBy != null) this.updatedBy = updatedBy;
    }
}
