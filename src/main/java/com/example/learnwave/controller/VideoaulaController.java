package com.example.learnwave.controller;

import com.example.learnwave.model.entity.Videoaula;
import com.example.learnwave.service.VideoaulaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/videoaulas")
public class VideoaulaController {

    @Autowired
    private VideoaulaService videoaulaService;

    @PostMapping
    public ResponseEntity<Videoaula> criarVideoaula(@RequestBody Videoaula videoaula) {
        Videoaula videoaulaCriada = videoaulaService.criarVideoaula(videoaula);
        return ResponseEntity.ok(videoaulaCriada);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Videoaula> buscarVideoaula(@PathVariable Integer id) {
        Videoaula videoaula = videoaulaService.buscarPorId(id);
        if (videoaula == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(videoaula);
    }

    @GetMapping
    public ResponseEntity<List<Videoaula>> listarVideoaulas() {
        return ResponseEntity.ok(videoaulaService.listarTodas());
    }

    @GetMapping("/professor/{professorId}")
    public ResponseEntity<List<Videoaula>> listarVideoaulasPorProfessor(@PathVariable Integer professorId) {
        return ResponseEntity.ok(videoaulaService.listarPorProfessor(professorId));
    }

    @GetMapping("/area/{area}")
    public ResponseEntity<List<Videoaula>> listarVideoaulasPorArea(@PathVariable String area) {
        return ResponseEntity.ok(videoaulaService.listarPorArea(area));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Videoaula> atualizarVideoaula(@PathVariable Integer id, @RequestBody Videoaula videoaula) {
        videoaula.setId(id);
        Videoaula videoaulaAtualizada = videoaulaService.atualizar(videoaula);
        return ResponseEntity.ok(videoaulaAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirVideoaula(@PathVariable Integer id) {
        if (!videoaulaService.deletar(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/publicar")
    public ResponseEntity<Void> publicarVideoaula(@PathVariable Integer id) {
        if (!videoaulaService.publicar(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/arquivar")
    public ResponseEntity<Void> arquivarVideoaula(@PathVariable Integer id) {
        if (!videoaulaService.arquivar(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/lixeira")
    public ResponseEntity<Void> moverParaLixeira(@PathVariable Integer id) {
        if (!videoaulaService.moverParaLixeira(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/restaurar")
    public ResponseEntity<Void> restaurar(@PathVariable Integer id) {
        if (!videoaulaService.restaurar(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/lixeira/professor/{professorId}")
    public ResponseEntity<List<Videoaula>> listarLixeiraPorProfessor(@PathVariable Integer professorId) {
        return ResponseEntity.ok(videoaulaService.listarNaLixeiraPorProfessor(professorId));
    }

    // Rotas específicas
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Videoaula>> buscarPorStatus(@PathVariable String status) {
        return ResponseEntity.ok(videoaulaService.buscarPorStatus(status));
    }

    @PatchMapping("/{id}/rascunho")
    public ResponseEntity<Void> voltarParaRascunho(@PathVariable Integer id) {
        if (!videoaulaService.voltarParaRascunho(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/publicadas")
    public ResponseEntity<List<Videoaula>> listarPublicadas() {
        return ResponseEntity.ok(videoaulaService.listarPublicadas());
    }

    @GetMapping("/duracao/{duracao}")
    public ResponseEntity<List<Videoaula>> buscarPorDuracao(@PathVariable String duracao) {
        return ResponseEntity.ok(videoaulaService.buscarPorDuracao(duracao));
    }

    /**
     * Retorna lista de IDs de professores que possuem videoaulas publicadas.
     * O frontend pode usar isso para filtrar quais professores exibir.
     */
    @GetMapping("/professores-com-videoaulas")
    public ResponseEntity<List<Integer>> listarProfessoresComVideoaulas() {
        return ResponseEntity.ok(videoaulaService.listarProfessoresComVideoaulasPublicadas());
    }
}
