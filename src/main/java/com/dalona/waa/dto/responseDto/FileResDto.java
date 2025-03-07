package com.dalona.waa.dto.responseDto;

import com.dalona.waa.domain.File;
import lombok.Data;

@Data
public class FileResDto {

    private Integer id;

    private Integer organizationId;

    private String originalName;

    private String contentType;

    private String url;

    public FileResDto(File file) {
        this.id = file.getId();
        this.organizationId = file.getOrganizationId();
        this.originalName = file.getOriginalName();
        this.contentType = file.getContentType();
        this.url = file.getUrl();
    }
}
