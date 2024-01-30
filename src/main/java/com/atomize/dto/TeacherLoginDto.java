package com.atomize.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record TeacherLoginDto(
        @Email(message = "provide valid email address") String email,
        @Size(min = 4, message = "password must be  greater than 4 characters") String password) {

}
