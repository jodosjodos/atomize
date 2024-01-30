package com.atomize.repository;

import com.atomize.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    Optional<Teacher> findByEmail(String email);
    // public ArrayList<Teacher> findByDosId(Long dosId) ;
}
