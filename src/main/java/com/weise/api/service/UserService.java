package com.weise.api.service;

import com.weise.api.dto.request.UserCreateDTO;
import com.weise.api.model.Category;
import com.weise.api.model.User;
import com.weise.api.repository.CategoryRepository;
import com.weise.api.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, CategoryRepository categoryRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // --- Método que estava faltando ---
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }
    // ----------------------------------

    public User registerUser(UserCreateDTO data) {
        if (userRepository.findByEmail(data.email()).isPresent()) {
            throw new RuntimeException("E-mail já em uso.");
        }

        User newUser = new User();
        newUser.setName(data.name());
        newUser.setEmail(data.email());
        newUser.setPassword(passwordEncoder.encode(data.password()));

        User savedUser = userRepository.save(newUser);

        createDefaultCategories(savedUser);

        return savedUser;
    }

    private void createDefaultCategories(User user) {
        // Lista de DESPESAS
        String[] expenses = {
                "Alimentação", "Assinatura", "Casa", "Compras", "Educação",
                "Eletrônicos", "Lazer", "Operação bancária", "Outros",
                "Pix", "Saúde", "Serviços", "Supermercado", "Transporte",
                "Vestuário", "Viagem"
        };

        for (String name : expenses) {
            saveCategory(user, name, "EXPENSE");
        }

        // Lista de RECEITAS
        String[] incomes = {
                "Bonificação", "Empréstimo", "Investimento", "Outros", "Pix",
                "Projeto", "Presente", "Renda extra", "Salário", "Transferência Bancaria"
        };

        for (String name : incomes) {
            saveCategory(user, name, "INCOME");
        }
    }

    private void saveCategory(User user, String name, String type) {
        Category c = new Category();
        c.setName(name);
        c.setUser(user);
        c.setType(type);
        categoryRepository.save(c);
    }
}