package com.atomize.Controller;

import com.atomize.dto.LoginResponse;
import com.atomize.dto.SignInRequest;
import com.atomize.dto.SignUpRequest;
import com.atomize.entity.Dos;
import com.atomize.services.DOSService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

// dos controller
@RestController
@RequestMapping("/api/dos")
@RequiredArgsConstructor
@Validated
public class DosController {
    private final DOSService service;

    // create dos
    @PostMapping("/create")
    public ResponseEntity<Dos> createDos(@Valid @RequestBody SignUpRequest signUpRequest) {
        return ResponseEntity.ok().body(service.createDos(signUpRequest));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Dos>> getAllDos() {
        return ResponseEntity.ok().body(service.getAllDos());
    }

    // delete dos
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteDos(@RequestParam("email") String dosEmail) {
        return ResponseEntity.ok().body(service.deleteDos(dosEmail));
    }

    // login
    @PostMapping("/login")
    public ResponseEntity<LoginResponse<Dos>> loginDos(@RequestBody SignInRequest signInRequest) {
        return ResponseEntity.ok().body(service.loginDos(signInRequest));
    }

    @GetMapping("/getTeachers")
    public ResponseEntity<?> getAllRelatedTeacher() {
        return ResponseEntity.ok().body(service.getAllTeachers());
    }

}
