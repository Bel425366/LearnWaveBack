package com.example.learnwave.service;

import com.example.learnwave.dao.AtividadeDAO;
import com.example.learnwave.enums.StatusConteudo;
import com.example.learnwave.model.entity.Atividade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AtividadeService {

    @Autowired
    private AtividadeDAO atividadeDAO;

    public Atividade criarAtividade(Atividade atividade) {
        return atividadeDAO.salvar(atividade);
    }

    public Atividade buscarPorId(Integer id) {
        return atividadeDAO.buscarPorId(id);
    }

    public List<Atividade> listarTodas() {
        return atividadeDAO.listarTodas();
    }

    public List<Atividade> listarPorProfessor(Integer professorId) {
        return atividadeDAO.buscarPorProfessor(professorId);
    }

    public List<Atividade> listarPorArea(String area) {
        return atividadeDAO.buscarPorAreaEStatus(area, StatusConteudo.PUBLICADO);
    }

    public Atividade atualizar(Atividade atividade) {
        return atividadeDAO.atualizar(atividade);
    }

    public boolean deletar(Integer id) {
        return atividadeDAO.deletar(id);
    }

    public boolean publicar(Integer id) {
        return atividadeDAO.publicar(id);
    }

    public boolean arquivar(Integer id) {
        return atividadeDAO.arquivar(id);
    }

    public List<Atividade> buscarPorStatus(String status) {
        StatusConteudo statusEnum = StatusConteudo.fromString(status);
        return atividadeDAO.buscarPorStatus(statusEnum);
    }

    public boolean voltarParaRascunho(Integer id) {
        return atividadeDAO.voltarParaRascunho(id);
    }

    public List<Atividade> listarPublicadas() {
        return atividadeDAO.buscarPublicadas();
    }
}
