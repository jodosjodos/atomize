package com.atomize.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.List;

import com.atomize.dto.Role;

@Table(name = "teacher")
@Entity
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String password;
    private Date dateOfBirth;
    private String degree;
    private Role role;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dosId", referencedColumnName = "id")
    private Dos creatorDos;
    @OneToMany(mappedBy = "teacher")
    private List<Course> course;
    @OneToMany(mappedBy = "headTeacher")
    private List<ClassRoom> classRoom;
    @OneToMany(mappedBy = "headTeacher")
    private List<Student> student;

}
