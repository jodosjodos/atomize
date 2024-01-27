package com.atomize.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Table(name = "course")
@Entity
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    private    List<Student> student;
    @OneToMany
    private    List<Teacher> teacher;
    @OneToMany
    private    List<ClassRoom> classRoom;
}
