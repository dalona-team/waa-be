package com.dalona.waa.repository;

import com.dalona.waa.domain.DogProfile;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DogProfileRepository extends JpaRepository<DogProfile, Integer> {

    List<DogProfile> findByDogIdIn(List<Integer> dogIds);
}
