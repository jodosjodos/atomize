package com.atomize.serviceImpl;

import com.atomize.errors.ApiException.exception.ApiRequestException;
import com.atomize.repository.DosRepository;
import com.atomize.services.UserServiceSecurity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceSecurityImpl implements UserServiceSecurity {
    private final DosRepository repository;

    // get user by username(email)
    @Override
    public UserDetailsService userDetailsService() {
        return username -> repository.findByEmail(username).orElseThrow(() -> new ApiRequestException("user doesn't exists", HttpStatus.NOT_FOUND));
    }


}
