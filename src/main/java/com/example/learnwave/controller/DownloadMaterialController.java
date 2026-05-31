package com.example.learnwave.controller;

import com.example.learnwave.model.entity.DownloadMaterial;
import com.example.learnwave.service.DownloadMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/downloads")
public class DownloadMaterialController {

    @Autowired
    private DownloadMaterialService downloadMaterialService;

    @PostMapping
    public ResponseEntity<DownloadMaterial> registrarDownload(@RequestBody DownloadMaterial download) {
        DownloadMaterial downloadRegistrado = downloadMaterialService.registrarDownload(download);
        return ResponseEntity.ok(downloadRegistrado);
    }

    @GetMapping("/aluno/{alunoId}")
    public ResponseEntity<List<DownloadMaterial>> listarPorAluno(@PathVariable Integer alunoId) {
        return ResponseEntity.ok(downloadMaterialService.listarPorAluno(alunoId));
    }

    @GetMapping("/material/{materialId}")
    public ResponseEntity<List<DownloadMaterial>> listarPorMaterial(@PathVariable Integer materialId) {
        return ResponseEntity.ok(downloadMaterialService.listarPorMaterial(materialId));
    }

    @GetMapping("/material/{materialId}/count")
    public ResponseEntity<Long> contarDownloads(@PathVariable Integer materialId) {
        return ResponseEntity.ok(downloadMaterialService.contarDownloads(materialId));
    }
}