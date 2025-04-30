package com.goodamcodes.repository;

import com.goodamcodes.model.SubSkill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubSkillRepository extends JpaRepository<SubSkill, Long> {
    Optional<SubSkill> findByName(String name);
}
