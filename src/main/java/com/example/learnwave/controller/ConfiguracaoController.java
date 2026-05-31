package com.example.learnwave.controller;

import com.example.learnwave.model.entity.Configuracao;
import com.example.learnwave.service.ConfiguracaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/configuracoes")
public class ConfiguracaoController {

    @Autowired
    private ConfiguracaoService configuracaoService;

    @PostMapping
    public ResponseEntity<Configuracao> criarConfiguracao(@RequestBody Configuracao configuracao) {
        Configuracao configuracaoCriada = configuracaoService.salvar(configuracao);
        return ResponseEntity.ok(configuracaoCriada);
    }

    @GetMapping
    public ResponseEntity<List<Configuracao>> listarTodas() {
        return ResponseEntity.ok(configuracaoService.listarTodas());
    }

    @GetMapping("/{chave}")
    public ResponseEntity<Configuracao> buscarPorChave(@PathVariable String chave) {
        Configuracao configuracao = configuracaoService.buscarPorChave(chave);
        if (configuracao == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(configuracao);
    }

    @PutMapping("/{chave}")
    public ResponseEntity<Configuracao> atualizarConfiguracao(@PathVariable String chave, @RequestBody Configuracao configuracao) {
        configuracao.setChave(chave);
        Configuracao configuracaoAtualizada = configuracaoService.atualizar(configuracao);
        return ResponseEntity.ok(configuracaoAtualizada);
    }

    @DeleteMapping("/{chave}")
    public ResponseEntity<Void> excluirConfiguracao(@PathVariable String chave) {
        if (!configuracaoService.excluir(chave)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }
}