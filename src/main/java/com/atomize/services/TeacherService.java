package com.atomize.services;

import com.atomize.dtos.TeacherSignUpDto;
import com.atomize.entity.Teacher;

public interface TeacherService {
    Teacher createTeacher(TeacherSignUpDto signUpDto);
}
