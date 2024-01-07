package com.atomize.services;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserServiceSecurity {
    UserDetailsService userDetailsService();
}
