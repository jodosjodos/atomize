package com.atomize.dto;

import java.util.Date;

import com.atomize.entity.Course;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TeacherSignUpDto (
    @Valid
    @NotNull(message = "full name is required")

    String fullName,
    @Email(message = "please provided valid email")
    String email,
    @Size(min = 4, max = 15, message = " please provide valid phoneNumber")
    String phoneNumber,
    @Size(min = 4, message = "please provide strong password")
    String password,
    @NotNull(message = "degree is required")
    String degree,
    Course course,
    @NotNull
    Date dateOfBirth
){}
