package com.dalona.waa.dto.responseDto;

import com.dalona.waa.domain.File;
import lombok.Data;

@Data
public class FileResDto {

    private Integer id;

    public FileResDto(File file) {
        this.id = file.getId();
    }
}
