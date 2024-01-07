package com.atomize.Controller;


import com.atomize.dtos.SignUpRequest;
import com.atomize.entity.Dos;
import com.atomize.errors.ApiException.exception.ApiRequestException;
import com.atomize.services.DOSService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dos")
@RequiredArgsConstructor
@Validated
public class DosController {
    private final DOSService service;

    @PostMapping("/create")
    public ResponseEntity<Dos> createDos(@Valid @RequestBody SignUpRequest signUpRequest) {
        return ResponseEntity.ok().body(service.createDos(signUpRequest));
    }


}
