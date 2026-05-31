package com.example.learnwave.dao.impl;

import com.example.learnwave.dao.AtividadeDAO;
import com.example.learnwave.enums.StatusConteudo;
import com.example.learnwave.model.entity.Atividade;
import com.example.learnwave.repository.AtividadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class AtividadeDAOImpl implements AtividadeDAO {

    @Autowired
    private AtividadeRepository atividadeRepository;

    @Override
    public Atividade salvar(Atividade atividade) {
        if (atividade.getStatus() == null) atividade.setStatus(StatusConteudo.RASCUNHO);
        atividade.setDataCriacao(LocalDateTime.now());
        atividade.setDataAtualizacao(LocalDateTime.now());
        return atividadeRepository.save(atividade);
    }

    @Override
    public Atividade buscarPorId(Integer id) {
        return atividadeRepository.findById(id).orElse(null);
    }

    @Override
    public List<Atividade> listarTodas() {
        return atividadeRepository.findByStatusNot(StatusConteudo.LIXEIRA);
    }

    @Override
    public Atividade atualizar(Atividade atividade) {
        Atividade existente = buscarPorId(atividade.getId());
        if (existente != null) {
            if (atividade.getDataCriacao() == null) atividade.setDataCriacao(existente.getDataCriacao());
            if (atividade.getStatus() == null) atividade.setStatus(existente.getStatus());
        }
        atividade.setDataAtualizacao(LocalDateTime.now());
        return atividadeRepository.save(atividade);
    }

    @Override
    public boolean deletar(Integer id) {
        Atividade a = buscarPorId(id);
        if (a == null) return false;
        // Soft delete: mover para lixeira
        a.setStatus(StatusConteudo.LIXEIRA);
        a.setDataAtualizacao(LocalDateTime.now());
        atividadeRepository.save(a);
        return true;
    }

    @Override
    public List<Atividade> buscarPorProfessor(Integer professorId) {
        return atividadeRepository.findByProfessorIdAndStatusNot(professorId, StatusConteudo.LIXEIRA);
    }

    public List<Atividade> buscarNaLixeiraPorProfessor(Integer professorId) {
        return atividadeRepository.findByProfessorIdAndStatus(professorId, StatusConteudo.LIXEIRA);
    }

    @Override
    public List<Atividade> buscarPorArea(String area) {
        return atividadeRepository.findByAreaAndStatus(area, StatusConteudo.PUBLICADO);
    }

    @Override
    public List<Atividade> buscarPorStatus(StatusConteudo status) {
        return atividadeRepository.findByStatus(status);
    }

    @Override
    public List<Atividade> buscarPorAreaEStatus(String area, StatusConteudo status) {
        return atividadeRepository.findByAreaAndStatus(area, status);
    }

    @Override
    public boolean publicar(Integer id) {
        Atividade a = buscarPorId(id);
        if (a == null) return false;
        a.setStatus(StatusConteudo.PUBLICADO);
        a.setDataAtualizacao(LocalDateTime.now());
        atividadeRepository.save(a);
        return true;
    }

    @Override
    public boolean arquivar(Integer id) {
        Atividade a = buscarPorId(id);
        if (a == null) return false;
        a.setStatus(StatusConteudo.ARQUIVADO);
        a.setDataAtualizacao(LocalDateTime.now());
        atividadeRepository.save(a);
        return true;
    }

    @Override
    public boolean voltarParaRascunho(Integer id) {
        Atividade a = buscarPorId(id);
        if (a == null) return false;
        a.setStatus(StatusConteudo.RASCUNHO);
        a.setDataAtualizacao(LocalDateTime.now());
        atividadeRepository.save(a);
        return true;
    }

    @Override
    public List<Atividade> buscarPublicadas() {
        return atividadeRepository.findByStatus(StatusConteudo.PUBLICADO);
    }

    @Override
    public long contarPorStatus(StatusConteudo status) {
        return atividadeRepository.findByStatus(status).size();
    }

    @Override
    public long contarPorArea(String area) {
        return atividadeRepository.findByAreaAndStatus(area, StatusConteudo.PUBLICADO).size();
    }

    @Override
    public long contarPorProfessor(Integer professorId) {
        return atividadeRepository.findByProfessorIdAndStatusNot(professorId, StatusConteudo.LIXEIRA).size();
    }
}
