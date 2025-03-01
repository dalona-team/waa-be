package com.dalona.waa.dto.requestDto;

import com.dalona.waa.domain.Post;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreatePostDto {

    @NotNull(message = "dogId is required")
    @Schema(description = "게시글 대상 강아지 id", example = "1")
    private Integer dogId;

    @NotNull(message = "instagramUrl is required")
    @Schema(description = "업로드된 인스타그램 게시글 링크", example = "https://www.instagram.com/p/<post_id>")
    private String instagramUrl;

    public Post toEntity() {
        return Post.builder()
                .dogId(this.dogId)
                .instagramUrl(this.instagramUrl)
                .createdBy(1)
                .build();
    }
}
