package com.example.cybersecurity_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    // ❌ A01 – Broken Access Control
    // Nenhum controle de autenticação
    @GetMapping("/all")
    public List<Map<String, Object>> getAllUsers() {
        return jdbcTemplate.queryForList("SELECT * FROM users");
    }

    // ❌ Vulnerável a SQL Injection
    @GetMapping("/search")
    public List<Map<String, Object>> searchUser(@RequestParam String name) {
        String sql = "SELECT * FROM user WHERE name = '" + name + "'";
        return jdbcTemplate.queryForList(sql);
    }

    // ❌ A07 – Senha salva sem hash
    @PostMapping("/register")
    public String register(@RequestBody Map<String, String> body) {
        String name = body.get("name");
        String email = body.get("email");
        String password = body.get("password"); // ❌ senha em texto plano

        // ❌ A02 – Falha Criptográfica
        String sql = "INSERT INTO users (name, email, password) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, name, email, password);

        return "Usuário registrado (inseguro)";
    }
    

    // ✅ Segura com PreparedStatement
    @GetMapping("/safe-search")
    public List<Map<String, Object>> safeSearchUser(@RequestParam String name) {
        String sql = "SELECT * FROM user WHERE name = ?";
        return jdbcTemplate.queryForList(sql, name);
    }
}
