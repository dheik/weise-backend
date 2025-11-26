package com.weise.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data // Gera Getters, Setters, toString, equals, hashcode automaticamente (Lombok)
@NoArgsConstructor // Gera construtor vazio (Obrigatório para JPA)
@AllArgsConstructor // Gera construtor com todos os argumentos
@Entity // Diz ao Spring que isso vira uma tabela no banco
@Table(name = "users") // Nome da tabela no plural (boas práticas)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID) // Vamos usar UUID para ser mais profissional/seguro que ID numérico
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true) // E-mail não pode repetir
    private String email;

    @Column(nullable = false)
    private String password; // Aqui guardaremos o hash, nunca a senha pura

    @CreationTimestamp // O Hibernate preenche a data de criação sozinho
    @Column(updatable = false)
    private LocalDateTime createdAt;
}