package com.atomize.Controller;


import com.atomize.dtos.SignUpRequest;
import com.atomize.entity.Dos;
import com.atomize.errors.ApiException.exception.ApiRequestException;
import com.atomize.services.DOSService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dos")
@RequiredArgsConstructor
@Validated
public class DosController {
    private final DOSService service;

    @GetMapping("/testing")
    public  ResponseEntity<?> testApi(){
        return  ResponseEntity.ok().body("testing ");
    }
    @PostMapping("/create")
    public ResponseEntity<Dos> createDos(@Valid @RequestBody SignUpRequest signUpRequest) {
        return ResponseEntity.ok().body(service.createDos(signUpRequest));
    }

//    not done yet
    @GetMapping("/all")
    public  ResponseEntity<?> getAllDos(){
        return  null;
    }

}
