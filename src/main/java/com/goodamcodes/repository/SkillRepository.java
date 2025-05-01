package com.goodamcodes.repository;

import com.goodamcodes.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;
import java.util.Optional;

public interface SkillRepository extends JpaRepository<Skill, Long> {

    @Query("SELECT DISTINCT s FROM Skill s LEFT JOIN FETCH s.subSkillList")
    List<Skill> findAllWithSubSkills();

    Optional<Skill> findByName(String name);
}
