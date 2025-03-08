package com.dalona.waa.controller;

import com.dalona.waa.dto.requestDto.CreateDogDto;
import com.dalona.waa.dto.responseDto.DogInfoResDto;
import com.dalona.waa.dto.responseDto.DogResDto;
import com.dalona.waa.service.DogService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/dog")
@Validated
@RequiredArgsConstructor
public class DogController {

    private final DogService dogService;

    @PostMapping
    @Operation(summary = "강아지 등록 API", description = "대시보드에 새로운 강아지의 정보를 등록합니다.")
    public ResponseEntity<DogInfoResDto> registerDog(@Valid @RequestBody CreateDogDto createDogDto) {
        DogInfoResDto dogInfoResDto = dogService.register(createDogDto);

        return new ResponseEntity<>(dogInfoResDto, HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "구조단체의 강아지 정보 목록 조회 API", description = "구조단체의 강아지 정보 목록을 조회합니다.")
    public ResponseEntity<List<DogResDto>> getOrganizationDogList(
            @RequestParam(name = "organizationId") Integer organizationId
    ) {
        List<DogResDto> dogInfoList = dogService.getDogListByOrganizationId(organizationId);

        return new ResponseEntity<>(dogInfoList, HttpStatus.OK);
    }
}
