package com.atomize.dtos;

import com.atomize.entity.Dos;

public record LoginResponse(Dos Dos, String token ) {
}
