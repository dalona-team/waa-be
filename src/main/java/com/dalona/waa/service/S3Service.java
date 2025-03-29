package com.dalona.waa.service;

import io.awspring.cloud.s3.ObjectMetadata;
import io.awspring.cloud.s3.S3Operations;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CopyObjectRequest;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class S3Service {

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucket;

    private final S3Operations s3Operations;
    private final S3Client s3Client;

    private static final String PUBLIC_FILE_FOLDER = "public/";
    private static final String TEMP_FILE_FOLDER = "temp/";

    public String upload(MultipartFile multipartFile) throws IOException {
        String contentType = multipartFile.getContentType();
        validateContentType(contentType);

        String fileKey = UUID.randomUUID() + "_" + getUrlSafeName(multipartFile.getOriginalFilename());
        String path = TEMP_FILE_FOLDER + fileKey;

        try (InputStream is = multipartFile.getInputStream()) {
            s3Operations.upload(bucket, path, is, ObjectMetadata.builder().contentType(contentType).build());
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

    private String getUrlSafeName(String originalFileName) {
        return originalFileName.replaceAll("[^a-zA-Z0-9.\\-]", "_");
    }

    public void copyObject(String fileKey) {
        String tempFilePath = TEMP_FILE_FOLDER + fileKey;
        String publicFileKey = PUBLIC_FILE_FOLDER + fileKey;

        CopyObjectRequest copyObjectRequest = CopyObjectRequest.builder()
                .sourceBucket(bucket)
                .sourceKey(tempFilePath)
                .destinationBucket(bucket)
                .destinationKey(publicFileKey)
                .build();

        s3Client.copyObject(copyObjectRequest);
    }

    public String generatePreSignedUrl(String fileKey) {
        String path = PUBLIC_FILE_FOLDER + fileKey;
        return s3Operations.createSignedGetURL(bucket, path, Duration.ofMinutes(1)).toString();
    }
}
