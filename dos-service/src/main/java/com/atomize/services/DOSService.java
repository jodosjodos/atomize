package com.atomize.services;


import com.atomize.dtos.SignUpRequest;
import com.atomize.entity.Dos;
// dos service
public interface DOSService {
    Dos createDos(SignUpRequest signUpRequest);
}
