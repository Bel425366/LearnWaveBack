package com.example.learnwave.controller;

import com.example.learnwave.model.entity.Atividade;
import com.example.learnwave.service.AtividadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/atividades")
@CrossOrigin(origins = "*")
public class AtividadeController {

    @Autowired
    private AtividadeService atividadeService;

    @PostMapping
    public ResponseEntity<Atividade> criarAtividade(@RequestBody Atividade atividade) {
        Atividade atividadeCriada = atividadeService.criarAtividade(atividade);
        return ResponseEntity.ok(atividadeCriada);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Atividade> buscarAtividade(@PathVariable Integer id) {
        Atividade atividade = atividadeService.buscarPorId(id);
        if (atividade == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(atividade);
    }

    @GetMapping
    public ResponseEntity<List<Atividade>> listarAtividades() {
        return ResponseEntity.ok(atividadeService.listarTodas());
    }

    @GetMapping("/professor/{professorId}")
    public ResponseEntity<List<Atividade>> listarAtividadesPorProfessor(@PathVariable Integer professorId) {
        return ResponseEntity.ok(atividadeService.listarPorProfessor(professorId));
    }

    @GetMapping("/area/{area}")
    public ResponseEntity<List<Atividade>> listarAtividadesPorArea(@PathVariable String area) {
        return ResponseEntity.ok(atividadeService.listarPorArea(area));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Atividade> atualizarAtividade(@PathVariable Integer id, @RequestBody Atividade atividade) {
        atividade.setId(id);
        Atividade atividadeAtualizada = atividadeService.atualizar(atividade);
        return ResponseEntity.ok(atividadeAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirAtividade(@PathVariable Integer id) {
        if (!atividadeService.deletar(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/lixeira")
    public ResponseEntity<Void> moverParaLixeira(@PathVariable Integer id) {
        Atividade a = atividadeService.buscarPorId(id);
        if (a == null) return ResponseEntity.notFound().build();
        a.setSituacao("lixeira");
        atividadeService.atualizar(a);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/restaurar")
    public ResponseEntity<Void> restaurar(@PathVariable Integer id) {
        Atividade a = atividadeService.buscarPorId(id);
        if (a == null) return ResponseEntity.notFound().build();
        a.setSituacao("ativo");
        atividadeService.atualizar(a);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/publicar")
    public ResponseEntity<Void> publicarAtividade(@PathVariable Integer id) {
        if (!atividadeService.publicar(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/arquivar")
    public ResponseEntity<Void> arquivarAtividade(@PathVariable Integer id) {
        if (!atividadeService.arquivar(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    // Rotas específicas baseadas no script SQL
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Atividade>> buscarPorStatus(@PathVariable String status) {
        return ResponseEntity.ok(atividadeService.buscarPorStatus(status));
    }

    @PatchMapping("/{id}/rascunho")
    public ResponseEntity<Void> voltarParaRascunho(@PathVariable Integer id) {
        if (!atividadeService.voltarParaRascunho(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/publicadas")
    public ResponseEntity<List<Atividade>> listarPublicadas() {
        return ResponseEntity.ok(atividadeService.listarPublicadas());
    }
}
