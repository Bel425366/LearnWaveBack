package com.example.learnwave.controller;

import com.example.learnwave.model.entity.Material;
import com.example.learnwave.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/materiais")
public class MaterialController {

    @Autowired
    private MaterialService materialService;

    @PostMapping
    public ResponseEntity<Material> criarMaterial(@RequestBody Material material) {
        Material materialCriado = materialService.criarMaterial(material);
        return ResponseEntity.ok(materialCriado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Material> buscarMaterial(@PathVariable Integer id) {
        Material material = materialService.buscarPorId(id);
        if (material == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(material);
    }

    @GetMapping
    public ResponseEntity<List<Material>> listarMateriais() {
        return ResponseEntity.ok(materialService.listarTodos());
    }

    @GetMapping("/professor/{professorId}")
    public ResponseEntity<List<Material>> listarMateriaisPorProfessor(@PathVariable Integer professorId) {
        return ResponseEntity.ok(materialService.listarPorProfessor(professorId));
    }

    @GetMapping("/area/{area}")
    public ResponseEntity<List<Material>> listarMateriaisPorArea(@PathVariable String area) {
        return ResponseEntity.ok(materialService.listarPorArea(area));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Material> atualizarMaterial(@PathVariable Integer id, @RequestBody Material material) {
        material.setId(id);
        Material materialAtualizado = materialService.atualizar(material);
        return ResponseEntity.ok(materialAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirMaterial(@PathVariable Integer id) {
        if (!materialService.deletar(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/publicar")
    public ResponseEntity<Void> publicarMaterial(@PathVariable Integer id) {
        if (!materialService.publicar(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/arquivar")
    public ResponseEntity<Void> arquivarMaterial(@PathVariable Integer id) {
        if (!materialService.arquivar(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Material>> buscarPorStatus(@PathVariable String status) {
        return ResponseEntity.ok(materialService.buscarPorStatus(status));
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<Material>> buscarPorTipoArquivo(@PathVariable String tipo) {
        return ResponseEntity.ok(materialService.buscarPorTipoArquivo(tipo));
    }

    @PatchMapping("/{id}/rascunho")
    public ResponseEntity<Void> voltarParaRascunho(@PathVariable Integer id) {
        if (!materialService.voltarParaRascunho(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/publicados")
    public ResponseEntity<List<Material>> listarPublicados() {
        return ResponseEntity.ok(materialService.listarPublicados());
    }

    @GetMapping("/{id}/downloads/count")
    public ResponseEntity<Long> contarDownloads(@PathVariable Integer id) {
        return ResponseEntity.ok(materialService.contarDownloads(id));
    }

    /**
     * Move material para a lixeira (soft delete).
     */
    @PatchMapping("/{id}/lixeira")
    public ResponseEntity<Void> moverParaLixeira(@PathVariable Integer id) {
        if (!materialService.deletar(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    /**
     * Restaura material da lixeira, voltando para RASCUNHO.
     * Retorna o material atualizado para o front atualizar o estado local.
     */
    @PatchMapping("/{id}/restaurar")
    public ResponseEntity<Material> restaurar(@PathVariable Integer id) {
        if (!materialService.voltarParaRascunho(id)) {
            return ResponseEntity.notFound().build();
        }
        Material atualizado = materialService.buscarPorId(id);
        return ResponseEntity.ok(atualizado);
    }

    @GetMapping("/lixeira/professor/{professorId}")
    public ResponseEntity<List<Material>> listarLixeiraPorProfessor(@PathVariable Integer professorId) {
        return ResponseEntity.ok(materialService.listarNaLixeiraPorProfessor(professorId));
    }
}
