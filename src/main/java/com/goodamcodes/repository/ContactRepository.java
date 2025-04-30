package com.goodamcodes.repository;

import com.goodamcodes.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    Optional<Contact> findByMedium(String medium);
}
