package com.example.learnwave.controller;

import com.example.learnwave.enums.StatusConteudo;
import com.example.learnwave.model.entity.Atividade;
import com.example.learnwave.service.AtividadeService;
import com.example.learnwave.service.NotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/atividades")
public class AtividadeController {

    @Autowired
    private AtividadeService atividadeService;

    @Autowired
    private NotaService notaService;

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

    /**
     * Move atividade para a lixeira (soft delete).
     * A atividade deixa de aparecer para alunos.
     * As notas são desconsideradas da média (mas não apagadas).
     */
    @PatchMapping("/{id}/lixeira")
    public ResponseEntity<Void> moverParaLixeira(@PathVariable Integer id) {
        if (!atividadeService.deletar(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    /**
     * Restaura atividade da lixeira, voltando para RASCUNHO.
     * Retorna a atividade atualizada para o front atualizar o estado local.
     */
    @PatchMapping("/{id}/restaurar")
    public ResponseEntity<Atividade> restaurar(@PathVariable Integer id) {
        if (!atividadeService.voltarParaRascunho(id)) {
            return ResponseEntity.notFound().build();
        }
        Atividade atualizada = atividadeService.buscarPorId(id);
        return ResponseEntity.ok(atualizada);
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

    @GetMapping("/lixeira/professor/{professorId}")
    public ResponseEntity<List<Atividade>> listarLixeiraPorProfessor(@PathVariable Integer professorId) {
        return ResponseEntity.ok(atividadeService.listarNaLixeiraPorProfessor(professorId));
    }
}
