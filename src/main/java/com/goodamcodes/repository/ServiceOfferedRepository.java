package com.goodamcodes.repository;

import com.goodamcodes.model.ServiceOffered;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ServiceOfferedRepository extends JpaRepository<ServiceOffered, Long> {
    Optional<ServiceOffered> findByName(String name);
}
