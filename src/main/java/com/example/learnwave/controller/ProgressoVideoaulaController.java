package com.example.learnwave.controller;

import com.example.learnwave.model.entity.ProgressoVideoaula;
import com.example.learnwave.service.ProgressoVideoaulaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/progresso-videoaulas")
public class ProgressoVideoaulaController {

    @Autowired
    private ProgressoVideoaulaService progressoVideoaulaService;

    @PostMapping
    public ResponseEntity<ProgressoVideoaula> iniciarVideoaula(@RequestBody ProgressoVideoaula progresso) {
        ProgressoVideoaula progressoCriado = progressoVideoaulaService.iniciarVideoaula(progresso);
        return ResponseEntity.ok(progressoCriado);
    }

    @GetMapping("/aluno/{alunoId}")
    public ResponseEntity<List<ProgressoVideoaula>> listarPorAluno(@PathVariable Integer alunoId) {
        return ResponseEntity.ok(progressoVideoaulaService.listarPorAluno(alunoId));
    }

    @GetMapping("/videoaula/{videoaulaId}")
    public ResponseEntity<List<ProgressoVideoaula>> listarPorVideoaula(@PathVariable Integer videoaulaId) {
        return ResponseEntity.ok(progressoVideoaulaService.listarPorVideoaula(videoaulaId));
    }

    @GetMapping("/aluno/{alunoId}/videoaula/{videoaulaId}")
    public ResponseEntity<ProgressoVideoaula> buscarPorAlunoEVideoaula(
            @PathVariable Integer alunoId, @PathVariable Integer videoaulaId) {
        ProgressoVideoaula progresso = progressoVideoaulaService.buscarPorAlunoEVideoaula(alunoId, videoaulaId);
        if (progresso == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(progresso);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProgressoVideoaula> atualizarProgresso(@PathVariable Integer id, @RequestBody ProgressoVideoaula progresso) {
        progresso.setId(id);
        ProgressoVideoaula progressoAtualizado = progressoVideoaulaService.atualizarProgresso(progresso);
        return ResponseEntity.ok(progressoAtualizado);
    }

    @PatchMapping("/{id}/tempo")
    public ResponseEntity<Void> atualizarTempo(@PathVariable Integer id, @RequestParam Integer tempoAssistido) {
        progressoVideoaulaService.atualizarTempo(id, tempoAssistido);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/concluir")
    public ResponseEntity<Void> concluirVideoaula(@PathVariable Integer id) {
        progressoVideoaulaService.concluirVideoaula(id);
        return ResponseEntity.ok().build();
    }
}
