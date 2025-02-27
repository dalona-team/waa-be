package com.dalona.waa.domain;

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

    private Boolean neutered; // 중성화 여부

    /* 건강 정보 */
    private Boolean heartworm; // 사상충 감염 여부

    @Column(name = "kennel_cough")
    private Boolean kennelCough; // 켄넬코프 여부

    @Column(name = "dental_scaling")
    private Boolean dentalScaling; // 이빨 스케일링 여부

    @Column(name = "health_notes", columnDefinition = "TEXT")
    private String healthNotes; // 기타 건강 특이사항

    /* 반려인 생활 참고 정보 */
    @Column(name = "barking_level")
    private Integer barkingLevel; // 짖음 정도 (0~5)

    @Column(name = "separation_anxiety")
    private Integer separationAnxiety; // 분리 불안 정도 (0~5)

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
    public DogProfile(Integer dogId, String adoptionAddress, LocalDate rescueDate, String rescueLocation,
                      Integer weight, Boolean neutered, Boolean heartworm, Boolean kennelCough,
                      Boolean dentalScaling, String healthNotes, Integer barkingLevel, Integer separationAnxiety,
                      PottyTraining pottyTraining, String behaviorNotes, String rescueContext,
                      String additionalStory, Integer createdBy) {
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
}
