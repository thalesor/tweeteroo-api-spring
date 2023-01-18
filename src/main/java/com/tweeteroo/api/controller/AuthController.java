package com.tweeteroo.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tweeteroo.api.dto.AuthDTO;
import com.tweeteroo.api.models.User;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("api/auth/sign-up")
public class AuthController {

    public static User signedUser;

    public static User getSignedUser() {
        return signedUser;
    }

    @PostMapping()
    public ResponseEntity<String> signUp(@RequestBody AuthDTO request) {
        if (!request.username().isEmpty() && !request.avatar().isEmpty()) {
            this.signedUser = new User(request.username(), request.avatar());
            return ResponseEntity.ok("OK");
        } else
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Erro ao realizar login");

    }

}
