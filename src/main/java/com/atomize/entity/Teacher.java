package com.atomize.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.atomize.dto.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Table(name = "teacher")
@Entity
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String password;
    private Date dateOfBirth;
    private String degree;
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "dos_id")
    @JsonIgnore
    private Dos creatorDos;
    @OneToMany(mappedBy = "teacher")
    private List<Course> course;
    @OneToMany(mappedBy = "headTeacher")
    private List<ClassRoom> classRoom;
    @OneToMany(mappedBy = "headTeacher")
    private List<Student> student;

}
