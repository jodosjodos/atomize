package com.atomize.services;

import com.atomize.dto.LoginResponse;
import com.atomize.dto.SignInRequest;
import com.atomize.dto.SignUpRequest;
import com.atomize.entity.Dos;
import com.atomize.entity.Teacher;

import java.util.ArrayList;
import java.util.List;

// dos service
public interface DOSService {
    Dos createDos(SignUpRequest signUpRequest);

    List<Dos> getAllDos();

    Dos deleteDos(String dosEmail);

    LoginResponse<Dos> loginDos(SignInRequest signInRequest);

    ArrayList<Teacher> getAllTeachers();

    // deleteDos();
}
