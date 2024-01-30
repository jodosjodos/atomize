package com.atomize.Controller;

import com.atomize.dto.TeacherLoginDto;
import com.atomize.dto.TeacherSignUpDto;
import com.atomize.entity.Teacher;
import com.atomize.services.TeacherService;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/teacher")
@RequiredArgsConstructor
@Validated
public class TeacherController {

    private final TeacherService service;

    @PostMapping("/create")
    public ResponseEntity<Teacher> create(@RequestBody @Valid TeacherSignUpDto signUpDto, BindingResult bindingResult) {

        return ResponseEntity.ok().body(service.createTeacher(signUpDto));

    }

    @PostMapping("/login")
    public ResponseEntity<?>login (@RequestBody @Valid TeacherLoginDto loginDto, BindingResult bindingResult) {
        return ResponseEntity.ok().body(service.login(loginDto));
    }

    // handle validation of inputs
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ MethodArgumentNotValidException.class, ConstraintViolationException.class })
    public Map<String, String> handleValidationException(Exception ex) {
        Map<String, String> errors = new HashMap<>();

        if (ex instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException validationException = (MethodArgumentNotValidException) ex;

            validationException.getBindingResult().getAllErrors().forEach((err) -> {
                String fieldName = ((FieldError) err).getField();
                String errorMessage = err.getDefaultMessage();
                errors.put(formatFieldName(fieldName), errorMessage);
            });
        } else if (ex instanceof ConstraintViolationException) {
            ConstraintViolationException constraintViolationException = (ConstraintViolationException) ex;

            constraintViolationException.getConstraintViolations().forEach(violation -> {
                String fieldName = violation.getPropertyPath().toString();
                String errorMessage = violation.getMessage();
                errors.put(formatFieldName(fieldName), errorMessage);
            });
        }

        return errors;
    }

    private String formatFieldName(String fieldName) {
        // Customize the format as needed, e.g., removing 'create.' prefix
        return fieldName.replace("create.", "").replace("signUpDto", "").replace(".", "");
    }

}
