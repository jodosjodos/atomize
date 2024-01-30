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

    private String name;
    @OneToMany
    private    List<Student> student;
    @ManyToOne()
    private     Teacher teacher;
    @OneToMany
    private    List<ClassRoom> classRoom;
}
