package com.atomize.errors.ApiException;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Data
public class ApiException {
private  final  String message;
private  final  Throwable throwable;
private  final HttpStatus httpStatus;
private  final ZonedDateTime timestamp;
}
