package com.dalona.waa.dto.responseDto;

import lombok.Data;

@Data
public class FileUrlResDto {

    private Integer id;

    private String url;

    public FileUrlResDto(Integer id, String url) {
        this.id = id;
        this.url = url;
    }
}
