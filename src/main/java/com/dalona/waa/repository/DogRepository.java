package com.dalona.waa.repository;

import com.dalona.waa.domain.Dog;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DogRepository extends JpaRepository<Dog, Integer> {

    List<Dog> findAllByOrganizationId(Integer organizationId);
}
