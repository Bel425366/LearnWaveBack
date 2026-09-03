package com.example.learnwave.dao.impl;

import com.example.learnwave.dao.ProgressoAtividadeDAO;
import com.example.learnwave.enums.StatusProgresso;
import com.example.learnwave.model.entity.ProgressoAtividade;
import com.example.learnwave.repository.ProgressoAtividadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class ProgressoAtividadeDAOImpl implements ProgressoAtividadeDAO {

    @Autowired
    private ProgressoAtividadeRepository progressoAtividadeRepository;

    @Override
    public ProgressoAtividade salvar(ProgressoAtividade progresso) {
        if (progresso.getStatus() == null) progresso.setStatus(StatusProgresso.NAO_INICIADO);
        if (progresso.getTentativas() == null) progresso.setTentativas(0);
        if (progresso.getDataInicio() == null) progresso.setDataInicio(LocalDateTime.now());
        progresso.setDataAtualizacao(LocalDateTime.now());
        return progressoAtividadeRepository.save(progresso);
    }

    @Override
    public List<ProgressoAtividade> listarPorAluno(Integer alunoId) {
        return progressoAtividadeRepository.findByAlunoId(alunoId);
    }

    @Override
    public List<ProgressoAtividade> listarPorAtividade(Integer atividadeId) {
        return progressoAtividadeRepository.findByAtividadeId(atividadeId);
    }

    @Override
    public ProgressoAtividade atualizar(ProgressoAtividade progresso) {
        progresso.setDataAtualizacao(LocalDateTime.now());
        return progressoAtividadeRepository.save(progresso);
    }

    @Override
    public void concluirAtividade(Integer id, Double nota, String respostaAluno) {
        ProgressoAtividade progresso = progressoAtividadeRepository.findById(id).orElse(null);
        if (progresso != null) {
            progresso.setStatus(StatusProgresso.CONCLUIDO);
            progresso.setNota(BigDecimal.valueOf(nota));
            if (respostaAluno != null) progresso.setRespostaAluno(respostaAluno);
            progresso.setDataConclusao(LocalDateTime.now());
            progresso.setDataAtualizacao(LocalDateTime.now());
            progresso.setTentativas(progresso.getTentativas() + 1);
            progressoAtividadeRepository.save(progresso);
        }
    }
}
