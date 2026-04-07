package com.springboot.BookManagementSystem.controller;

import com.springboot.BookManagementSystem.dto.UserSignUpDto;
import com.springboot.BookManagementSystem.service.AdminService;
import com.springboot.BookManagementSystem.utility.JwtUtill;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AdminService adminService;

    @PostMapping("/sign-up")
    public ResponseEntity<?> addUser(@RequestBody @Valid UserSignUpDto dto){
        adminService.addUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    private final JwtUtill jwtUtility;
    @GetMapping("/login")
    public ResponseEntity<?> login(Principal principal){
        String loggedInUser = principal.getName();
        Map<String,String> map = new HashMap<>();
        map.put("token", jwtUtility.generateToken(loggedInUser));
        return ResponseEntity.status(HttpStatus.OK)
                .body(map);
    }
}
