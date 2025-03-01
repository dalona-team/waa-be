package com.dalona.waa.controller;

import com.dalona.waa.dto.requestDto.ContentsReqDto;
import com.dalona.waa.dto.responseDto.ContentsResDto;
import com.dalona.waa.service.ClovaStudioService;
import com.dalona.waa.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/post")
@Validated
@RequiredArgsConstructor
public class PostController {

    private final ClovaStudioService clovaStudioService;

    @PostMapping("/generate")
    @Operation(summary = "인스타그램 게시글 콘텐츠 생성 API", description = "선택한 템플릿과 강아지 정보를 기반으로 Clova X AI를 활용하여 게시글 콘텐츠를 생성합니다.")
    public ResponseEntity<ContentsResDto> generateContents(@Valid @RequestBody ContentsReqDto contentsReqDto) {
        ContentsResDto contentsResDto = clovaStudioService.generateContents(contentsReqDto);

        return new ResponseEntity<>(contentsResDto, HttpStatus.CREATED);
    }
}
