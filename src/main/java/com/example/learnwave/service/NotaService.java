package com.example.learnwave.service;

import com.example.learnwave.enums.StatusConteudo;
import com.example.learnwave.model.entity.Atividade;
import com.example.learnwave.model.entity.ProgressoAtividade;
import com.example.learnwave.repository.AtividadeRepository;
import com.example.learnwave.repository.ProgressoAtividadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Serviço responsável pelo cálculo de notas e médias dos alunos.
 * 
 * Regras:
 * - Apenas atividades com status=PUBLICADO contam na média.
 * - Quando uma atividade vai para a lixeira (status=LIXEIRA), ela deixa de contar.
 * - Quando restaurada, as notas voltam a contar.
 * - Nenhuma resposta/nota é apagada — apenas desconsiderada temporariamente.
 */
@Service
public class NotaService {

    @Autowired
    private ProgressoAtividadeRepository progressoAtividadeRepository;

    @Autowired
    private AtividadeRepository atividadeRepository;

    /**
     * Calcula a média do aluno considerando apenas atividades publicadas.
     * Atividades na lixeira ou rascunho não contam.
     */
    public BigDecimal calcularMediaAluno(Integer alunoId) {
        BigDecimal media = progressoAtividadeRepository.calcularMediaAluno(alunoId);
        if (media == null) return BigDecimal.ZERO;
        return media.setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Retorna o progresso geral do aluno:
     * - totalAtividades: total de atividades publicadas
     * - atividadesConcluidas: quantas o aluno concluiu (dentre as publicadas)
     * - percentualConcluido: percentual de conclusão
     * - media: média das notas
     */
    public Map<String, Object> obterProgressoAluno(Integer alunoId) {
        Map<String, Object> resultado = new HashMap<>();

        BigDecimal media = calcularMediaAluno(alunoId);
        long concluidas = progressoAtividadeRepository.contarAtividadesConcluidasAtivas(alunoId);
        long totalAtividades = progressoAtividadeRepository.contarAtividadesPublicadasAtivas();

        double percentual = totalAtividades > 0 ? (double) concluidas / totalAtividades * 100 : 0;

        resultado.put("alunoId", alunoId);
        resultado.put("media", media);
        resultado.put("totalAtividades", totalAtividades);
        resultado.put("atividadesConcluidas", concluidas);
        resultado.put("percentualConcluido", BigDecimal.valueOf(percentual).setScale(1, RoundingMode.HALF_UP));

        return resultado;
    }

    /**
     * Retorna as notas detalhadas do aluno, indicando quais estão ativas na média.
     */
    public Map<String, Object> obterNotasDetalhadas(Integer alunoId) {
        Map<String, Object> resultado = new HashMap<>();

        List<ProgressoAtividade> todosProgressos = progressoAtividadeRepository.findByAlunoId(alunoId);
        List<Map<String, Object>> notas = new java.util.ArrayList<>();

        for (ProgressoAtividade progresso : todosProgressos) {
            Map<String, Object> notaInfo = new HashMap<>();
            notaInfo.put("progressoId", progresso.getId());
            notaInfo.put("atividadeId", progresso.getAtividadeId());
            notaInfo.put("nota", progresso.getNota());
            notaInfo.put("status", progresso.getStatus());
            notaInfo.put("tentativas", progresso.getTentativas());
            notaInfo.put("dataConclusao", progresso.getDataConclusao());

            // Verificar se a atividade está publicada (conta na média)
            Atividade atividade = atividadeRepository.findById(progresso.getAtividadeId()).orElse(null);
            boolean contaNaMedia = false;
            if (atividade != null) {
                contaNaMedia = StatusConteudo.PUBLICADO.equals(atividade.getStatus());
                notaInfo.put("atividadeTitulo", atividade.getTitulo());
                notaInfo.put("atividadeStatus", atividade.getStatus());
            }
            notaInfo.put("contaNaMedia", contaNaMedia);

            notas.add(notaInfo);
        }

        resultado.put("alunoId", alunoId);
        resultado.put("notas", notas);
        resultado.put("media", calcularMediaAluno(alunoId));

        return resultado;
    }
}
