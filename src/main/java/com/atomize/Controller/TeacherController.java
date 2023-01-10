package com.atomize.Controller;

import com.atomize.dtos.TeacherSignUpDto;
import com.atomize.services.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/teacher")
@RequiredArgsConstructor
public class TeacherController {

    private  final TeacherService service;
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody TeacherSignUpDto signUpDto) {
        return ResponseEntity.ok().body(service.createTeacher(signUpDto));
    }
    @GetMapping("/login")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok().body("reaching out");
    }

}
