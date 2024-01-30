package com.atomize.repository;

import com.atomize.entity.Teacher;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

public interface TeacherRepository extends JpaRepository<Teacher, UUID> {
    Optional<Teacher> findByEmail(String email);
    List<Teacher> findByCreatorDos_Id(UUID dosId);
}
