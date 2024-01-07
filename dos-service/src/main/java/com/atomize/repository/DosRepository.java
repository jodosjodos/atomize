package com.atomize.repository;

import com.atomize.entity.Dos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DosRepository extends JpaRepository<Long, Dos> {
    Optional<Dos> finByEmail(String email);
}
