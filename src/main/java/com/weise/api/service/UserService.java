package com.weise.api.service;

import com.weise.api.dto.request.UserCreateDTO;
import com.weise.api.model.User;
import com.weise.api.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public User registerUser(UserCreateDTO data) {
        // Regra 1: Não permitir e-mails duplicados
        if (userRepository.findByEmail(data.email()).isPresent()) {
            throw new RuntimeException("Este e-mail já está em uso.");
        }

        // Regra 2: Criptografar a senha antes de salvar
        User newUser = new User();
        newUser.setName(data.name());
        newUser.setEmail(data.email());
        newUser.setPassword(passwordEncoder.encode(data.password()));

        return userRepository.save(newUser);
    }
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }
}