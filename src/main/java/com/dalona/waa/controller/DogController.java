package com.dalona.waa.controller;

import com.dalona.waa.dto.responseDto.DogInfoResDto;
import com.dalona.waa.service.DogService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/dog")
@Validated
@RequiredArgsConstructor
public class DogController {

    private final DogService dogService;

    @GetMapping
    @Operation(summary = "구조단체의 강아지 정보 목록 조회 API", description = "구조단체의 강아지 정보 목록을 조회합니다.")
    public ResponseEntity<List<DogInfoResDto>> getOrganizationDogList(
            @RequestParam(name = "organizationId") Integer organizationId) {
        List<DogInfoResDto> dogInfoList = dogService.getDogInfoListByOrganizationId(organizationId);

        return new ResponseEntity<>(dogInfoList, HttpStatus.OK);
    }
}
