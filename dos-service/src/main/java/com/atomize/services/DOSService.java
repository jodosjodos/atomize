package com.atomize.services;


import com.atomize.dtos.SignUpRequest;
import com.atomize.entity.Dos;

public interface DOSService {
    Dos createDos(SignUpRequest signUpRequest);
}
