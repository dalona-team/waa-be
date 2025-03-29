package com.dalona.waa.repository;

import com.dalona.waa.domain.DogFile;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DogFileRepository extends JpaRepository<DogFile, Integer> {

    DogFile findFirstByDogIdOrderByCreatedAtAsc(Integer dogId);

    List<DogFile> findAllByDogId(Integer dogId);
}
