package com.atomize.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/teacher")
public class TeacherController {

    @GetMapping("/login")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok().body("reaching out");
    }
    @GetMapping("/create")
    public ResponseEntity<?> create() {
        return ResponseEntity.ok().body("reaching out create");
    }
}
