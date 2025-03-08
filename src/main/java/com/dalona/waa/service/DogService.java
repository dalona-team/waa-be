package com.dalona.waa.service;

import com.dalona.waa.domain.Dog;
import com.dalona.waa.domain.DogProfile;
import com.dalona.waa.dto.requestDto.CreateDogDto;
import com.dalona.waa.dto.responseDto.DogInfoResDto;
import com.dalona.waa.dto.responseDto.DogProfileResDto;
import com.dalona.waa.dto.responseDto.DogResDto;
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

    private final DogRepository dogRepository;
    private final DogProfileRepository dogProfileRepository;

    public DogInfoResDto register(CreateDogDto createDogDto) {
        Dog dogEntity = createDogDto.toDogEntity(generateRegistrationNo());
        Dog dog = dogRepository.save(dogEntity);

        DogProfile profileEntity = createDogDto.toDogProfileEntity(dog.getId());
        DogProfile dogProfile = dogProfileRepository.save(profileEntity);

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

    public DogResDto getDogById(Integer id) {
        Dog dog = dogRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("DOG_NOT_FOUND"));
        return new DogResDto(dog);
    }

    public DogProfileResDto getDogProfileByDogId(Integer dogId) {
        DogProfile dogProfile = dogProfileRepository.findById(dogId)
                .orElseThrow(() -> new EntityNotFoundException("DOG_PROFILE_NOT_FOUND"));
        return new DogProfileResDto(dogProfile);
    }

    public List<DogResDto> getDogListByOrganizationId(Integer organizationId) {
        List<Dog> dogs = dogRepository.findAllByOrganizationId(organizationId);

        return dogs.stream()
                .map(DogResDto::new)
                .collect(Collectors.toList());
    }
}
