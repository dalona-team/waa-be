package com.dalona.waa.service;

import com.dalona.waa.domain.Dog;
import com.dalona.waa.domain.DogFile;
import com.dalona.waa.domain.DogProfile;
import com.dalona.waa.dto.requestDto.CreateDogDto;
import com.dalona.waa.dto.requestDto.UpdateDogDto;
import com.dalona.waa.dto.responseDto.DogInfoResDto;
import com.dalona.waa.dto.responseDto.DogResDto;
import com.dalona.waa.repository.DogFileRepository;
import com.dalona.waa.repository.DogProfileRepository;
import com.dalona.waa.repository.DogRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;
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

        List<Integer> fileIds = createDogDto.getFileIds();
        if (fileIds != null) {
            fileService.copyObjectToPublic(fileIds);
            createDogFiles(dog.getId(), fileIds);
        }

        return new DogInfoResDto(dog, dogProfile);
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

    public void createDogFiles(Integer dogId, List<Integer> fileIds) {
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
                .map(DogResDto::new)
                .collect(Collectors.toList());
    }

    public DogInfoResDto getDogInfo(Integer id) {
        Dog dog = dogRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("DOG_NOT_FOUND"));
        DogProfile dogProfile = dogProfileRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("DOG_PROFILE_NOT_FOUND"));

        return new DogInfoResDto(dog, dogProfile);
    }

    public DogInfoResDto update(Integer id, UpdateDogDto updateDogDto) {
        Dog dog = dogRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("DOG_NOT_FOUND"));
        DogProfile dogProfile = dogProfileRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("DOG_PROFILE_NOT_FOUND"));

        dog.update(
                updateDogDto.getName(),
                updateDogDto.getGender(),
                updateDogDto.getBirthDate(),
                updateDogDto.getBirthDateIsEstimated(),
                updateDogDto.getStatus(),
                1
        );
        dogProfile.update(
                updateDogDto.getAdoptionAddress(),
                updateDogDto.getRescueDate(),
                updateDogDto.getRescueLocation(),
                updateDogDto.getWeight(),
                updateDogDto.getNeutered(),
                updateDogDto.getHeartworm(),
                updateDogDto.getKennelCough(),
                updateDogDto.getDentalScaling(),
                updateDogDto.getHealthNotes(),
                updateDogDto.getBarkingLevel(),
                updateDogDto.getSeparationAnxiety(),
                updateDogDto.getPottyTraining(),
                updateDogDto.getBehaviorNotes(),
                updateDogDto.getRescueContext(),
                updateDogDto.getAdditionalStory(),
                1
        );

        dogRepository.save(dog);
        dogProfileRepository.save(dogProfile);
        return new DogInfoResDto(dog, dogProfile);
    }
}
