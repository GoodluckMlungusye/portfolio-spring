package com.goodamcodes.repository;

import com.goodamcodes.model.Explore;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExploreRepository extends JpaRepository<Explore, Long> {
    Optional<Explore> findByDescription(String description);
}
