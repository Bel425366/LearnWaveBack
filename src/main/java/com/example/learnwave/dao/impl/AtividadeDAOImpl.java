package com.example.learnwave.dao.impl;

import com.example.learnwave.dao.AtividadeDAO;
import com.example.learnwave.enums.StatusConteudo;
import com.example.learnwave.model.entity.Atividade;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class AtividadeDAOImpl implements AtividadeDAO {

    @Override
    public Atividade salvar(Atividade atividade) {
        return atividade;
    }

    @Override
    public Atividade buscarPorId(Integer id) {
        return null;
    }

    @Override
    public List<Atividade> listarTodas() {
        return new ArrayList<>();
    }

    @Override
    public Atividade atualizar(Atividade atividade) {
        return atividade;
    }

    @Override
    public boolean deletar(Integer id) {
        return true;
    }

    @Override
    public List<Atividade> buscarPorProfessor(Integer professorId) {
        return new ArrayList<>();
    }

    @Override
    public List<Atividade> buscarPorArea(String area) {
        return new ArrayList<>();
    }

    @Override
    public List<Atividade> buscarPorStatus(StatusConteudo status) {
        return new ArrayList<>();
    }

    @Override
    public List<Atividade> buscarPorAreaEStatus(String area, StatusConteudo status) {
        return new ArrayList<>();
    }

    @Override
    public boolean publicar(Integer id) {
        return true;
    }

    @Override
    public boolean arquivar(Integer id) {
        return true;
    }

    @Override
    public long contarPorStatus(StatusConteudo status) {
        return 0;
    }

    @Override
    public long contarPorArea(String area) {
        return 0;
    }

    @Override
    public long contarPorProfessor(Integer professorId) {
        return 0;
    }

    @Override
    public boolean voltarParaRascunho(Integer id) {
        return true;
    }

    @Override
    public List<Atividade> buscarPublicadas() {
        return new ArrayList<>();
    }


}