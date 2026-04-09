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
        if (atividade.getSituacao() == null) atividade.setSituacao("ativo");
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
        return atividadeRepository.findBySituacaoNot("excluido");
    }

    @Override
    public Atividade atualizar(Atividade atividade) {
        atividade.setDataAtualizacao(LocalDateTime.now());
        return atividadeRepository.save(atividade);
    }

    @Override
    public boolean deletar(Integer id) {
        Atividade a = buscarPorId(id);
        if (a == null) return false;
        // Se já está na lixeira, marca como excluido definitivamente
        if ("lixeira".equals(a.getSituacao())) {
            a.setSituacao("excluido");
        } else {
            a.setSituacao("lixeira");
        }
        a.setDataAtualizacao(LocalDateTime.now());
        atividadeRepository.save(a);
        return true;
    }

    @Override
    public List<Atividade> buscarPorProfessor(Integer professorId) {
        return atividadeRepository.findByProfessorIdAndSituacaoNot(professorId, "excluido");
    }

    public List<Atividade> buscarNaLixeiraPorProfessor(Integer professorId) {
        return atividadeRepository.findByProfessorIdAndSituacao(professorId, "lixeira");
    }

    @Override
    public List<Atividade> buscarPorArea(String area) {
        return atividadeRepository.findByAreaAndStatusAndSituacao(area, StatusConteudo.PUBLICADO, "ativo");
    }

    @Override
    public List<Atividade> buscarPorStatus(StatusConteudo status) {
        return atividadeRepository.findByStatusAndSituacao(status, "ativo");
    }

    @Override
    public List<Atividade> buscarPorAreaEStatus(String area, StatusConteudo status) {
        return atividadeRepository.findByAreaAndStatusAndSituacao(area, status, "ativo");
    }

    @Override
    public boolean publicar(Integer id) {
        Atividade a = buscarPorId(id);
        if (a == null) return false;
        a.setStatus(StatusConteudo.PUBLICADO);
        a.setSituacao("ativo");
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
        a.setSituacao("ativo");
        a.setDataAtualizacao(LocalDateTime.now());
        atividadeRepository.save(a);
        return true;
    }

    @Override
    public List<Atividade> buscarPublicadas() {
        return atividadeRepository.findByStatusAndSituacao(StatusConteudo.PUBLICADO, "ativo");
    }

    @Override
    public long contarPorStatus(StatusConteudo status) {
        return atividadeRepository.findByStatusAndSituacao(status, "ativo").size();
    }

    @Override
    public long contarPorArea(String area) {
        return atividadeRepository.findByAreaAndStatusAndSituacao(area, StatusConteudo.PUBLICADO, "ativo").size();
    }

    @Override
    public long contarPorProfessor(Integer professorId) {
        return atividadeRepository.findByProfessorIdAndSituacaoNot(professorId, "excluido").size();
    }
}