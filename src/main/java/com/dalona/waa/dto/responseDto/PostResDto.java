package com.dalona.waa.dto.responseDto;

import com.dalona.waa.domain.Post;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class PostResDto {

    private Integer id;

    private Integer dogId;

    private String instagramUrl;

    public PostResDto(Post post) {
        this.id = post.getId();
        this.dogId = post.getDogId();
        this.instagramUrl = post.getInstagramUrl();
    }
}
