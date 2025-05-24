package com.example.cybersecurity_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    // ❌ Vulnerável a SQL Injection
    @GetMapping("/search")
    public List<Map<String, Object>> searchUser(@RequestParam String name) {
        String sql = "SELECT * FROM user WHERE name = '" + name + "'";
        return jdbcTemplate.queryForList(sql);
    }

    // ✅ Segura com PreparedStatement
    @GetMapping("/safe-search")
    public List<Map<String, Object>> safeSearchUser(@RequestParam String name) {
        String sql = "SELECT * FROM user WHERE name = ?";
        return jdbcTemplate.queryForList(sql, name);
    }
}
