package com.example.learnwave.dao;


import com.example.learnwave.enums.StatusConteudo;
import com.example.learnwave.model.entity.Atividade;

import java.util.List;

public interface AtividadeDAO {


    Atividade salvar(Atividade atividade);
    Atividade buscarPorId(Integer id);
    List<Atividade> listarTodas();
    Atividade atualizar(Atividade atividade);
    boolean deletar(Integer id);


    List<Atividade> buscarPorProfessor(Integer professorId);
    List<Atividade> buscarPorArea(String area);
    List<Atividade> buscarPorStatus(StatusConteudo status);
    List<Atividade> buscarPublicadas();
    List<Atividade> buscarPorAreaEStatus(String area, StatusConteudo status);

    // Operações de status
    boolean publicar(Integer id);
    boolean arquivar(Integer id);
    boolean voltarParaRascunho(Integer id);

    // Estatísticas
    long contarPorProfessor(Integer professorId);
    long contarPorArea(String area);
    long contarPorStatus(StatusConteudo status);
}