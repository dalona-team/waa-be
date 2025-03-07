package com.dalona.waa.service;

import io.awspring.cloud.s3.ObjectMetadata;
import io.awspring.cloud.s3.S3Operations;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class S3Service {

    private static final String BUCKET = System.getenv("AWS_S3_BUCKET");

    private final S3Operations s3Operations;

    public String upload(MultipartFile multipartFile) throws IOException {
        String fileKey = "";
        String contentType = multipartFile.getContentType();

        validateContentType(contentType);

        try (InputStream is = multipartFile.getInputStream()) {
            s3Operations.upload(BUCKET, fileKey, is, ObjectMetadata.builder().contentType(contentType).build());
        }

        return fileKey;
    }

    private void validateContentType(String contentType) throws BadRequestException {
        ArrayList<String> imageTypes = new ArrayList<>(
                List.of(MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE));

        if (!imageTypes.contains(contentType)) {
            throw new BadRequestException("NOT_IMAGE_TYPE_FILE");
        }
    }
}
