package com.example.learnwave.dao.impl;

import com.example.learnwave.dao.ProgressoVideoaulaDAO;
import com.example.learnwave.enums.StatusProgresso;
import com.example.learnwave.model.entity.ProgressoVideoaula;
import com.example.learnwave.repository.ProgressoVideoaulaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class ProgressoVideoaulaDAOImpl implements ProgressoVideoaulaDAO {

    @Autowired
    private ProgressoVideoaulaRepository progressoVideoaulaRepository;

    @Override
    public ProgressoVideoaula salvar(ProgressoVideoaula progresso) {
        if (progresso.getStatus() == null) progresso.setStatus(StatusProgresso.NAO_INICIADO);
        if (progresso.getTempoAssistido() == null) progresso.setTempoAssistido(0);
        if (progresso.getDataInicio() == null) progresso.setDataInicio(LocalDateTime.now());
        progresso.setDataAtualizacao(LocalDateTime.now());
        return progressoVideoaulaRepository.save(progresso);
    }

    @Override
    public List<ProgressoVideoaula> listarPorAluno(Integer alunoId) {
        return progressoVideoaulaRepository.findByAlunoId(alunoId);
    }

    @Override
    public List<ProgressoVideoaula> listarPorVideoaula(Integer videoaulaId) {
        return progressoVideoaulaRepository.findByVideoaulaId(videoaulaId);
    }

    @Override
    public ProgressoVideoaula atualizar(ProgressoVideoaula progresso) {
        progresso.setDataAtualizacao(LocalDateTime.now());
        return progressoVideoaulaRepository.save(progresso);
    }

    @Override
    public void atualizarTempo(Integer id, Integer tempoAssistido) {
        ProgressoVideoaula progresso = progressoVideoaulaRepository.findById(id).orElse(null);
        if (progresso != null) {
            progresso.setTempoAssistido(tempoAssistido);
            if (progresso.getStatus() == StatusProgresso.NAO_INICIADO) {
                progresso.setStatus(StatusProgresso.EM_ANDAMENTO);
            }
            progresso.setDataAtualizacao(LocalDateTime.now());
            progressoVideoaulaRepository.save(progresso);
        }
    }

    @Override
    public void concluirVideoaula(Integer id) {
        ProgressoVideoaula progresso = progressoVideoaulaRepository.findById(id).orElse(null);
        if (progresso != null) {
            progresso.setStatus(StatusProgresso.CONCLUIDO);
            progresso.setDataConclusao(LocalDateTime.now());
            progresso.setDataAtualizacao(LocalDateTime.now());
            progressoVideoaulaRepository.save(progresso);
        }
    }
}
