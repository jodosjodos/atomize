package com.atomize.dtos;

import java.util.Date;

public record TeacherSignUpDto(String fullName,
                               String email,
                               String phoneNumber,
                               String password,
                               String degree,
                               Date dateOfBirth) {
}
