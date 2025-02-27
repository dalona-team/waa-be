package com.dalona.waa.domain;

import com.dalona.waa.enums.DogGender;
import com.dalona.waa.enums.DogStatus;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "dog")
public class Dog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "organization_id", nullable = false)
    private Integer organizationId;

    @Column(nullable = false, length = 50)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "dog_gender", nullable = false)
    private DogGender gender;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "birth_date_is_estimated", nullable = false)
    private Boolean birthDateIsEstimated;

    @Enumerated(EnumType.STRING)
    @Column(name = "dog_status", nullable = false)
    private DogStatus status;

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
    public Dog(Integer organizationId, String name, DogGender gender, LocalDate birthDate, boolean birthDateIsEstimated,
               DogStatus status, Integer createdBy) {
        this.organizationId = organizationId;
        this.name = name;
        this.gender = gender;
        this.birthDate = birthDate;
        this.birthDateIsEstimated = birthDateIsEstimated;
        this.status = status;
        this.createdBy = createdBy;
    }
}
