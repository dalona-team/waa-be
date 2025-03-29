package com.dalona.waa.service;

import com.dalona.waa.domain.Dog;
import com.dalona.waa.domain.DogFile;
import com.dalona.waa.domain.DogProfile;
import com.dalona.waa.dto.requestDto.CreateDogDto;
import com.dalona.waa.dto.requestDto.UpdateDogDto;
import com.dalona.waa.dto.responseDto.DogInfoResDto;
import com.dalona.waa.dto.responseDto.DogResDto;
import com.dalona.waa.dto.responseDto.FileUrlResDto;
import com.dalona.waa.repository.DogFileRepository;
import com.dalona.waa.repository.DogProfileRepository;
import com.dalona.waa.repository.DogRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class DogService {

    private final FileService fileService;
    private final DogRepository dogRepository;
    private final DogProfileRepository dogProfileRepository;
    private final DogFileRepository dogFileRepository;

    public DogInfoResDto register(CreateDogDto createDogDto) {
        Dog dogEntity = createDogDto.toDogEntity(generateRegistrationNo());
        Dog dog = dogRepository.save(dogEntity);

        DogProfile profileEntity = createDogDto.toDogProfileEntity(dog.getId());
        DogProfile dogProfile = dogProfileRepository.save(profileEntity);

        if (createDogDto.getImageFileIds() != null) {
            Set<Integer> imageFileIds = new HashSet<>(createDogDto.getImageFileIds());
            fileService.copyObjectToPublic(imageFileIds);
            createDogFiles(dog.getId(), imageFileIds);
        }

        return new DogInfoResDto(dog, dogProfile, null);
    }

    private String generateRegistrationNo() {
        LocalDateTime now = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMddHHmm");
        String timestamp = now.format(formatter);

        Random random = new Random();
        int randomNumber = random.nextInt(1000); // 0 ~ 999
        String randomString = String.format("%03d", randomNumber);

        return timestamp + randomString;
    }

    public void createDogFiles(Integer dogId, Set<Integer> fileIds) {
        List<DogFile> dogFiles = fileIds.stream()
                .map(fileId -> DogFile.builder()
                        .dogId(dogId)
                        .fileId(fileId)
                        .build())
                .collect(Collectors.toList());
        dogFileRepository.saveAll(dogFiles);
    }

    public List<DogResDto> getDogListByOrganizationId(Integer organizationId) {
        List<Dog> dogs = dogRepository.findAllByOrganizationId(organizationId);

        return dogs.stream()
                .map(dog -> {
                    DogFile dogFile = dogFileRepository.findFirstByDogIdOrderByCreatedAtAsc(dog.getId());
                    String imageUrl = null;
                    if (dogFile != null) {
                        imageUrl = fileService.generatePreSignedUrl(dogFile.getFileId());
                    }
                    return new DogResDto(dog, imageUrl);
                })
                .collect(Collectors.toList());
    }

    public DogInfoResDto getDogInfo(Integer id) {
        Dog dog = dogRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("DOG_NOT_FOUND"));
        DogProfile dogProfile = dogProfileRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("DOG_PROFILE_NOT_FOUND"));

        List<DogFile> dogFiles = dogFileRepository.findAllByDogId(id);
        List<FileUrlResDto> imageFiles = dogFiles.stream()
                .map(file -> fileService.getFileUrlRes(file.getFileId()))
                .toList();

        return new DogInfoResDto(dog, dogProfile, imageFiles);
    }

    public DogInfoResDto update(Integer id, UpdateDogDto updateDogDto) {
        Dog dog = dogRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("DOG_NOT_FOUND"));
        DogProfile dogProfile = dogProfileRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("DOG_PROFILE_NOT_FOUND"));

        dog.updateWithDto(updateDogDto, 1);
        dogProfile.updateWithDto(updateDogDto, 1);

        dogRepository.save(dog);
        dogProfileRepository.save(dogProfile);

        if (updateDogDto.getImageFileIds() != null) {
            updateDogImages(id, updateDogDto.getImageFileIds());
        }

        return new DogInfoResDto(dog, dogProfile, null);
    }

    public void updateDogImages(Integer dogId, List<Integer> newFileIds) {
        List<DogFile> existingMappings = dogFileRepository.findAllByDogId(dogId);
        Set<Integer> existingFileIds = existingMappings.stream()
                .map(DogFile::getFileId)
                .collect(Collectors.toSet());

        Set<Integer> newFileIdSet = new HashSet<>(newFileIds);

        Set<Integer> toDelete = new HashSet<>(existingFileIds);
        toDelete.removeAll(newFileIdSet);
        dogFileRepository.deleteByDogIdAndFileIdIn(dogId, toDelete);

        Set<Integer> toAdd = new HashSet<>(newFileIdSet);
        toAdd.removeAll(existingFileIds);
        fileService.copyObjectToPublic(toAdd);
        createDogFiles(dogId, toAdd);
    }
}
