package com.dalona.waa.domain;

import com.dalona.waa.converter.ChatMessageListConverter;
import com.dalona.waa.dto.ChatMessage;
import jakarta.persistence.*;
import java.util.List;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "template")
public class Template {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "organization_id", nullable = false)
    private Integer organizationId;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String system;

    @Convert(converter = ChatMessageListConverter.class)
    @Column(columnDefinition = "TEXT")
    private List<ChatMessage> chat;

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
    public Template(Integer organizationId, String requestId, String title, String system, List<ChatMessage> chat, Integer createdBy) {
        this.organizationId = organizationId;
        this.title = title;
        this.system = system;
        this.chat = chat;
        this.createdBy = createdBy;
    }
}
