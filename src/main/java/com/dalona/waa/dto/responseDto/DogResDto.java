package com.dalona.waa.dto.responseDto;

import com.dalona.waa.domain.Dog;
import com.dalona.waa.enums.BooleanStatus;
import com.dalona.waa.enums.DogGender;
import com.dalona.waa.enums.DogStatus;
import java.time.LocalDate;
import lombok.Data;

@Data
public class DogResDto {

    private Integer id;

    private Integer organizationId;

    private String name;

    private DogGender gender;

    private LocalDate birthDate;

    private BooleanStatus birthDateIsEstimated;

    private DogStatus status;

    public DogResDto(Dog dog) {
        this.id = dog.getId();
        this.organizationId = dog.getOrganizationId();
        this.name = dog.getName();
        this.gender = dog.getGender();
        this.birthDate = dog.getBirthDate();
        this.birthDateIsEstimated = dog.getBirthDateIsEstimated();
        this.status = dog.getStatus();
    }
}
