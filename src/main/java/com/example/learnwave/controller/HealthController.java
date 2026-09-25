package com.example.learnwave.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Endpoint simples de health check.
 * Responde na raiz "/" e em "/health" para que o health check do Render
 * não gere erro "No static resource" no log.
 */
@RestController
public class HealthController {

    @GetMapping("/")
    public Map<String, String> raiz() {
        return Map.of(
            "status", "online",
            "servico", "LearnWave API"
        );
    }

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of("status", "ok");
    }
}
