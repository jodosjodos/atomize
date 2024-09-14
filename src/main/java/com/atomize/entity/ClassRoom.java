package com.atomize.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Table(name = "classRoom")
@Entity
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class ClassRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "classRoom",cascade = CascadeType.REMOVE)
    private  List<Student> student;
    @OneToMany(mappedBy = "classRoom")
    private  List<Course> course;
    @ManyToOne
    private  Teacher headTeacher;
}
