package com.example.learnwave.controller;

import com.example.learnwave.model.entity.DocumentoVerificacao;
import com.example.learnwave.service.DocumentoVerificacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/documentos-verificacao")
public class DocumentoVerificacaoController {

    @Autowired
    private DocumentoVerificacaoService documentoVerificacaoService;

    @PostMapping
    public ResponseEntity<DocumentoVerificacao> uploadDocumento(@RequestBody DocumentoVerificacao documento) {
        DocumentoVerificacao documentoSalvo = documentoVerificacaoService.salvarDocumento(documento);
        return ResponseEntity.ok(documentoSalvo);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<DocumentoVerificacao>> listarPorUsuario(@PathVariable Integer usuarioId) {
        List<DocumentoVerificacao> documentos = documentoVerificacaoService.listarPorUsuario(usuarioId);
        
        // Se não há documentos na tabela específica, buscar do usuário
        if (documentos.isEmpty()) {
            // Buscar documento_url do usuário
            // Implementar lógica para buscar documento do usuário se necessário
        }
        
        return ResponseEntity.ok(documentos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocumentoVerificacao> buscarPorId(@PathVariable Integer id) {
        DocumentoVerificacao documento = documentoVerificacaoService.buscarPorId(id);
        if (documento == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(documento);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirDocumento(@PathVariable Integer id) {
        if (!documentoVerificacaoService.excluir(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }
}