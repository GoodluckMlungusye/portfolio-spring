package com.goodamcodes.repository;

import com.goodamcodes.model.NavigationLink;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NavigationLinkRepository extends JpaRepository<NavigationLink, Long> {

    Optional<NavigationLink> findByName(String name);
}
