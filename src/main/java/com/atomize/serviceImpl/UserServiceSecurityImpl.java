package com.atomize.serviceImpl;

import com.atomize.errors.ApiException.exception.ApiRequestException;
import com.atomize.repository.DosRepository;
import com.atomize.services.UserServiceSecurity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceSecurityImpl implements UserServiceSecurity {
    private final DosRepository repository;

    // get user by username(email)
    @Override
    public UserDetailsService userDetailsService() {
        return username -> repository.findByEmail(username).orElseThrow(() -> new ApiRequestException("user doesn't exists", HttpStatus.NOT_FOUND));
    }


}
