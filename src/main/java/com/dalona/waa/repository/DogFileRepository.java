package com.dalona.waa.repository;

import com.dalona.waa.domain.DogFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DogFileRepository extends JpaRepository<DogFile, Integer> {
}
