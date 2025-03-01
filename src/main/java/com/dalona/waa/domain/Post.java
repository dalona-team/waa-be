package com.dalona.waa.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "dog_id", nullable = false)
    private Integer dogId;

    @Column(name = "instagram_url", nullable = false)
    private String instagramUrl;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "created_by", nullable = false)
    private Integer createdBy;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @Builder
    public Post(Integer dogId, String instagramUrl, Integer createdBy) {
        this.dogId = dogId;
        this.instagramUrl = instagramUrl;
        this.createdBy = createdBy;
    }
}
