package com.example.learnwave.controller;

import com.example.learnwave.service.NotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/api/notas")
public class NotaController {

    @Autowired
    private NotaService notaService;

    /**
     * Retorna a média do aluno (apenas atividades publicadas e ativas).
     */
    @GetMapping("/media/{alunoId}")
    public ResponseEntity<Map<String, Object>> obterMedia(@PathVariable Integer alunoId) {
        BigDecimal media = notaService.calcularMediaAluno(alunoId);
        return ResponseEntity.ok(Map.of("alunoId", alunoId, "media", media));
    }

    /**
     * Retorna o progresso geral do aluno com percentual e média.
     */
    @GetMapping("/progresso/{alunoId}")
    public ResponseEntity<Map<String, Object>> obterProgresso(@PathVariable Integer alunoId) {
        return ResponseEntity.ok(notaService.obterProgressoAluno(alunoId));
    }

    /**
     * Retorna notas detalhadas do aluno, indicando quais contam na média.
     */
    @GetMapping("/detalhado/{alunoId}")
    public ResponseEntity<Map<String, Object>> obterNotasDetalhadas(@PathVariable Integer alunoId) {
        return ResponseEntity.ok(notaService.obterNotasDetalhadas(alunoId));
    }
}
