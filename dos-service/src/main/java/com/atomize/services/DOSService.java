package com.atomize.services;


import com.atomize.dtos.SignUpRequest;
import com.atomize.entity.Dos;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

// dos service
public interface DOSService {
    Dos createDos(SignUpRequest signUpRequest);

    List<Dos> getAllDos();

    Dos deleteDos(String dosEmail);

//    deleteDos();
}
