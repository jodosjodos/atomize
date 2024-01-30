package com.atomize.dto;

public record LoginResponse<T>(T entity, String token) {
}
