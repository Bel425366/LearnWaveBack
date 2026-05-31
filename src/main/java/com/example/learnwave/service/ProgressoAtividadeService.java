package com.example.learnwave.service;

import com.example.learnwave.dao.ProgressoAtividadeDAO;
import com.example.learnwave.enums.StatusProgresso;
import com.example.learnwave.model.entity.ProgressoAtividade;
import com.example.learnwave.repository.ProgressoAtividadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProgressoAtividadeService {

    @Autowired
    private ProgressoAtividadeDAO progressoAtividadeDAO;

    @Autowired
    private ProgressoAtividadeRepository progressoAtividadeRepository;

    public ProgressoAtividade iniciarAtividade(ProgressoAtividade progresso) {
        // Verificar se já existe progresso para este aluno/atividade
        Optional<ProgressoAtividade> existente = progressoAtividadeRepository
                .findByAlunoIdAndAtividadeId(progresso.getAlunoId(), progresso.getAtividadeId());
        
        if (existente.isPresent()) {
            // Retorna o existente sem criar duplicata
            return existente.get();
        }

        progresso.setStatus(StatusProgresso.EM_ANDAMENTO);
        progresso.setDataInicio(LocalDateTime.now());
        return progressoAtividadeDAO.salvar(progresso);
    }

    public List<ProgressoAtividade> listarPorAluno(Integer alunoId) {
        return progressoAtividadeDAO.listarPorAluno(alunoId);
    }

    public List<ProgressoAtividade> listarPorAtividade(Integer atividadeId) {
        return progressoAtividadeDAO.listarPorAtividade(atividadeId);
    }

    public ProgressoAtividade atualizarProgresso(ProgressoAtividade progresso) {
        return progressoAtividadeDAO.atualizar(progresso);
    }

    public void concluirAtividade(Integer id, Double nota) {
        progressoAtividadeDAO.concluirAtividade(id, nota);
    }

    public ProgressoAtividade buscarPorAlunoEAtividade(Integer alunoId, Integer atividadeId) {
        return progressoAtividadeRepository.findByAlunoIdAndAtividadeId(alunoId, atividadeId).orElse(null);
    }
}
