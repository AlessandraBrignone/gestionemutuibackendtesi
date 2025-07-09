package it.bribank.gestioneMutui.controller;

import it.bribank.gestioneMutui.dto.LoginRequestDto;
import it.bribank.gestioneMutui.dto.LoginResponseDto;
import it.bribank.gestioneMutui.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto request) {
        Map<String, Object> loginResult = authService.loginWithUser(request.getEmail(), request.getPassword());
        return ResponseEntity.ok(loginResult); // <-- include token + utente
    }
}
