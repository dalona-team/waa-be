package com.dalona.waa.service;

import com.dalona.waa.domain.File;
import com.dalona.waa.dto.responseDto.FileResDto;
import com.dalona.waa.dto.responseDto.FileUrlResDto;
import com.dalona.waa.repository.FileRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Set;
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
                .organizationId(1)
                .originalName(multipartFile.getOriginalFilename())
                .contentType(multipartFile.getContentType())
                .key(fileKey)
                .build();
        File file = fileRepository.save(entity);

        return new FileResDto(file);
    }

    public void copyObjectToPublic(Set<Integer> fileIds) {
        List<File> files = fileRepository.findAllById(fileIds);

        files.stream()
                .map(File::getKey)
                .forEach(s3Service::copyObject);
    }

    public String generatePreSignedUrl(Integer fileId) {
        File file = fileRepository.findById(fileId)
                .orElseThrow(() -> new EntityNotFoundException("FILE_NOT_FOUND"));

        return s3Service.generatePreSignedUrl(file.getKey());
    }

    public FileUrlResDto getFileUrlRes(Integer fileId) {
        String signedUrl = generatePreSignedUrl(fileId);
        return new FileUrlResDto(fileId, signedUrl);
    }
}
