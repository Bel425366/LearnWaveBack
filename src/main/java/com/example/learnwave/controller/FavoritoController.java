package com.example.learnwave.controller;

import com.example.learnwave.model.entity.ProfessorFavorito;
import com.example.learnwave.service.FavoritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favoritos")
public class FavoritoController {

    @Autowired
    private FavoritoService favoritoService;

    /**
     * Favoritar um professor.
     * Body: { "alunoId": X, "professorId": Y }
     */
    @PostMapping
    public ResponseEntity<ProfessorFavorito> favoritar(@RequestBody ProfessorFavorito favorito) {
        ProfessorFavorito resultado = favoritoService.favoritar(favorito.getAlunoId(), favorito.getProfessorId());
        return ResponseEntity.ok(resultado);
    }

    /**
     * Desfavoritar um professor.
     */
    @DeleteMapping("/{alunoId}/{professorId}")
    public ResponseEntity<Void> desfavoritar(@PathVariable Integer alunoId, @PathVariable Integer professorId) {
        if (!favoritoService.desfavoritar(alunoId, professorId)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    /**
     * Listar todos os professores favoritos de um aluno.
     */
    @GetMapping("/aluno/{alunoId}")
    public ResponseEntity<List<ProfessorFavorito>> listarFavoritos(@PathVariable Integer alunoId) {
        return ResponseEntity.ok(favoritoService.listarFavoritos(alunoId));
    }

    /**
     * Verificar se um professor é favorito do aluno.
     */
    @GetMapping("/{alunoId}/{professorId}")
    public ResponseEntity<Boolean> verificarFavorito(@PathVariable Integer alunoId, @PathVariable Integer professorId) {
        return ResponseEntity.ok(favoritoService.isFavorito(alunoId, professorId));
    }
}
