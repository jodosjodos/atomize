package com.atomize.repository;

import com.atomize.entity.Dos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DosRepository extends JpaRepository<Dos, UUID> {
    Optional<Dos> findByEmail(String email);

    Optional<Dos> findBySchoolName(String schoolName);

    @Transactional
    void deleteByEmail(String dosEmail);
}
