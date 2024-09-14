package com.atomize.services;

import com.atomize.dto.LoginResponse;
import com.atomize.dto.TeacherLoginDto;
import com.atomize.dto.TeacherSignUpDto;
import com.atomize.entity.Teacher;

import jakarta.validation.Valid;

public interface TeacherService {
    Teacher createTeacher(TeacherSignUpDto signUpDto);

    LoginResponse<Teacher> login(@Valid TeacherLoginDto loginDto);
}
