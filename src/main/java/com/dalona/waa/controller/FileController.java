package com.dalona.waa.controller;

import com.dalona.waa.dto.responseDto.FileResDto;
import com.dalona.waa.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/file")
@Validated
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "이미지 파일 업로드 API", description = "s3 임시 폴더에 form data로 전송한 이미지를 업로드합니다.")
    public ResponseEntity<FileResDto> uploadImage(
            @Parameter(
                    description = "multipart/form-data 타입의 이미지 데이터. (key: file)"
            )
            @RequestPart(value = "file")
            MultipartFile multipartFile
    ) throws BadRequestException {
        try {
            FileResDto file = fileService.upload(multipartFile);
            return new ResponseEntity<>(file, HttpStatus.OK);
        } catch (IOException e) {
            throw new BadRequestException(e);
        }
    }
}
