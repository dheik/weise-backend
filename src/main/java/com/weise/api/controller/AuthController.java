package com.weise.api.controller;

import com.weise.api.dto.request.LoginDTO;
import com.weise.api.dto.request.UserCreateDTO;
import com.weise.api.dto.response.LoginResponseDTO;
import com.weise.api.model.User;
import com.weise.api.service.TokenService;
import com.weise.api.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    // Endpoint de Cadastro
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody @Valid UserCreateDTO data) {
        User user = userService.registerUser(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    // Endpoint de Login
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginDTO data) {
        // 1. Autentica via Spring Security (se a senha estiver errada, lança erro aqui)
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(data.email(), data.password())
        );

        // 2. Recupera o usuário do banco para gerar o token com os dados dele
        // Obs: Certifique-se de ter criado o método 'findByEmail' no UserService (veja nota abaixo)
        User user = userService.findByEmail(data.email());

        // 3. Gera o Token JWT
        String token = tokenService.generateToken(user);

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }
}