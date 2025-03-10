package com.dalona.waa.service;

import com.dalona.waa.domain.File;
import com.dalona.waa.dto.responseDto.FileResDto;
import com.dalona.waa.repository.FileRepository;
import jakarta.transaction.Transactional;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;
    private final S3Service s3Service;

    public FileResDto upload(MultipartFile multipartFile) throws IOException {
        String fileKey = s3Service.upload(multipartFile);
        return createFile(multipartFile, fileKey);
    }

    private FileResDto createFile(MultipartFile multipartFile, String fileKey) {
        File entity = File.builder()
                .originalName(multipartFile.getOriginalFilename())
                .contentType(multipartFile.getContentType())
                .build();
        File file = fileRepository.save(entity);

        return new FileResDto(file);
    }
}
