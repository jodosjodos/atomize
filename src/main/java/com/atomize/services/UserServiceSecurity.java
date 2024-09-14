package com.atomize.services;

import org.springframework.security.core.userdetails.UserDetailsService;

// user details service
public interface UserServiceSecurity {
    UserDetailsService userDetailsService();
}
