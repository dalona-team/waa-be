package com.dalona.waa.dto.requestDto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import lombok.Data;

@Data
public class ContentsReqDto {

    @NotNull(message = "templateId is required")
    @Schema(description = "게시글 생성에 사용할 프롬프트 템플릿 id", example = "1")
    private Integer templateId;

    @NotNull(message = "dogId is required")
    @Schema(description = "게시글을 생성할 강아지의 id", example = "1")
    private Integer dogId;
}
