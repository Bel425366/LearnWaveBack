package com.example.learnwave.controller;

import com.example.learnwave.model.entity.ProgressoAtividade;
import com.example.learnwave.service.ProgressoAtividadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/progresso-atividades")
public class ProgressoAtividadeController {

    @Autowired
    private ProgressoAtividadeService progressoAtividadeService;

    @PostMapping
    public ResponseEntity<ProgressoAtividade> iniciarAtividade(@RequestBody ProgressoAtividade progresso) {
        ProgressoAtividade progressoCriado = progressoAtividadeService.iniciarAtividade(progresso);
        return ResponseEntity.ok(progressoCriado);
    }

    @GetMapping("/aluno/{alunoId}")
    public ResponseEntity<List<ProgressoAtividade>> listarPorAluno(@PathVariable Integer alunoId) {
        return ResponseEntity.ok(progressoAtividadeService.listarPorAluno(alunoId));
    }

    @GetMapping("/atividade/{atividadeId}")
    public ResponseEntity<List<ProgressoAtividade>> listarPorAtividade(@PathVariable Integer atividadeId) {
        return ResponseEntity.ok(progressoAtividadeService.listarPorAtividade(atividadeId));
    }

    @GetMapping("/aluno/{alunoId}/atividade/{atividadeId}")
    public ResponseEntity<ProgressoAtividade> buscarPorAlunoEAtividade(
            @PathVariable Integer alunoId, @PathVariable Integer atividadeId) {
        ProgressoAtividade progresso = progressoAtividadeService.buscarPorAlunoEAtividade(alunoId, atividadeId);
        if (progresso == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(progresso);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProgressoAtividade> atualizarProgresso(@PathVariable Integer id, @RequestBody ProgressoAtividade progresso) {
        progresso.setId(id);
        ProgressoAtividade progressoAtualizado = progressoAtividadeService.atualizarProgresso(progresso);
        return ResponseEntity.ok(progressoAtualizado);
    }

    @PatchMapping("/{id}/concluir")
    public ResponseEntity<Void> concluirAtividade(@PathVariable Integer id, @RequestParam Double nota) {
        progressoAtividadeService.concluirAtividade(id, nota);
        return ResponseEntity.ok().build();
    }
}
