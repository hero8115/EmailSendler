package com.example.emailsendler.controller;

import com.example.emailsendler.entity.User;
import com.example.emailsendler.payload.ApiResponse;
import com.example.emailsendler.payload.LoginDto;
import com.example.emailsendler.payload.UserDto;
import com.example.emailsendler.repository.UserRepository;
import com.example.emailsendler.servise.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/auth")
public class UserController {
    @Autowired
    AuthService authService;

    @GetMapping
    public List<User> userList(){
        return authService.userList();
    }

    @PostMapping
    public ApiResponse saveUser(@Valid @RequestBody UserDto userDto){
       return authService.saveUser(userDto);
    }
    @GetMapping("/verifyEmail")
    public ApiResponse verifyEmail(@RequestParam String  emailCode, @RequestParam String email){
          return   authService.verifyEmail(email,emailCode);
    }

    @PutMapping("{id}")
    public ApiResponse update(@PathVariable UUID id, @RequestBody UserDto userDto){
        return authService.update(id,userDto);
    }

    @DeleteMapping("/{email}")
    public ApiResponse delete(@PathVariable String email){
        return authService.delete(email);
    }

    @PostMapping("/login")
    public ApiResponse login(@RequestBody LoginDto loginDto){
        return authService.login(loginDto);
    }

    @PreAuthorize(value = "hasAnyAuthority('ADMIN')")
    @PostMapping("/{id}")
    public ResponseEntity<String> addManager(@PathVariable UUID id){
        return authService.addManager(id);
    }

}