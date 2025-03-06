package com.dalona.waa.repository;

import com.dalona.waa.domain.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Integer> {
}
