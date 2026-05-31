package com.example.learnwave.service;

import com.example.learnwave.dao.ProgressoVideoaulaDAO;
import com.example.learnwave.enums.StatusProgresso;
import com.example.learnwave.model.entity.ProgressoVideoaula;
import com.example.learnwave.repository.ProgressoVideoaulaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProgressoVideoaulaService {

    @Autowired
    private ProgressoVideoaulaDAO progressoVideoaulaDAO;

    @Autowired
    private ProgressoVideoaulaRepository progressoVideoaulaRepository;

    public ProgressoVideoaula iniciarVideoaula(ProgressoVideoaula progresso) {
        // Verificar se já existe progresso para este aluno/videoaula
        Optional<ProgressoVideoaula> existente = progressoVideoaulaRepository
                .findByAlunoIdAndVideoaulaId(progresso.getAlunoId(), progresso.getVideoaulaId());
        
        if (existente.isPresent()) {
            return existente.get();
        }

        progresso.setStatus(StatusProgresso.EM_ANDAMENTO);
        progresso.setDataInicio(LocalDateTime.now());
        return progressoVideoaulaDAO.salvar(progresso);
    }

    public List<ProgressoVideoaula> listarPorAluno(Integer alunoId) {
        return progressoVideoaulaDAO.listarPorAluno(alunoId);
    }

    public List<ProgressoVideoaula> listarPorVideoaula(Integer videoaulaId) {
        return progressoVideoaulaDAO.listarPorVideoaula(videoaulaId);
    }

    public ProgressoVideoaula atualizarProgresso(ProgressoVideoaula progresso) {
        return progressoVideoaulaDAO.atualizar(progresso);
    }

    public void atualizarTempo(Integer id, Integer tempoAssistido) {
        progressoVideoaulaDAO.atualizarTempo(id, tempoAssistido);
    }

    public void concluirVideoaula(Integer id) {
        progressoVideoaulaDAO.concluirVideoaula(id);
    }

    public ProgressoVideoaula buscarPorAlunoEVideoaula(Integer alunoId, Integer videoaulaId) {
        return progressoVideoaulaRepository.findByAlunoIdAndVideoaulaId(alunoId, videoaulaId).orElse(null);
    }
}
