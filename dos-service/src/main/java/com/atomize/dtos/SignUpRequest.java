package com.atomize.dtos;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public record SignUpRequest(String name, String email, String password, String schoolName, String phoneNumber) {
}
