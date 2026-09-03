package com.example.learnwave.dao;

import com.example.learnwave.model.entity.ProgressoAtividade;

import java.util.List;

public interface ProgressoAtividadeDAO {

    ProgressoAtividade salvar(ProgressoAtividade progresso);
    List<ProgressoAtividade> listarPorAluno(Integer alunoId);
    List<ProgressoAtividade> listarPorAtividade(Integer atividadeId);
    ProgressoAtividade atualizar(ProgressoAtividade progresso);
    void concluirAtividade(Integer id, Double nota, String respostaAluno);
}