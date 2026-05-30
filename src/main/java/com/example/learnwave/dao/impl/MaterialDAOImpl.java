package com.example.learnwave.dao.impl;

import com.example.learnwave.dao.MaterialDAO;
import com.example.learnwave.enums.StatusConteudo;
import com.example.learnwave.model.entity.Material;
import com.example.learnwave.repository.MaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class MaterialDAOImpl implements MaterialDAO {

    @Autowired
    private MaterialRepository materialRepository;

    @Override
    public Material salvar(Material material) {
        if (material.getStatus() == null) material.setStatus(StatusConteudo.RASCUNHO);
        if (material.getSituacao() == null) material.setSituacao("ativo");
        material.setDataCriacao(LocalDateTime.now());
        material.setDataAtualizacao(LocalDateTime.now());
        return materialRepository.save(material);
    }

    @Override
    public Material buscarPorId(Integer id) {
        return materialRepository.findById(id).orElse(null);
    }

    @Override
    public List<Material> listarTodos() {
        return materialRepository.findBySituacaoNot("excluido");
    }

    @Override
    public Material atualizar(Material material) {
        Material existente = buscarPorId(material.getId());
        if (existente != null) {
            if (material.getSituacao() == null) material.setSituacao(existente.getSituacao());
            if (material.getDataCriacao() == null) material.setDataCriacao(existente.getDataCriacao());
        }
        material.setDataAtualizacao(LocalDateTime.now());
        return materialRepository.save(material);
    }

    @Override
    public boolean deletar(Integer id) {
        Material m = buscarPorId(id);
        if (m == null) return false;
        if ("lixeira".equals(m.getSituacao())) {
            m.setSituacao("excluido");
        } else {
            m.setSituacao("lixeira");
        }
        m.setDataAtualizacao(LocalDateTime.now());
        materialRepository.save(m);
        return true;
    }

    @Override
    public List<Material> buscarPorProfessor(Integer professorId) {
        return materialRepository.findByProfessorIdAndSituacaoNot(professorId, "excluido");
    }

    @Override
    public List<Material> buscarPorAreaEStatus(String area, StatusConteudo status) {
        return materialRepository.findByAreaAndStatusAndSituacao(area, status, "ativo");
    }

    @Override
    public boolean publicar(Integer id) {
        Material m = buscarPorId(id);
        if (m == null) return false;
        m.setStatus(StatusConteudo.PUBLICADO);
        m.setSituacao("ativo");
        m.setDataAtualizacao(LocalDateTime.now());
        materialRepository.save(m);
        return true;
    }

    @Override
    public boolean arquivar(Integer id) {
        Material m = buscarPorId(id);
        if (m == null) return false;
        m.setStatus(StatusConteudo.ARQUIVADO);
        m.setDataAtualizacao(LocalDateTime.now());
        materialRepository.save(m);
        return true;
    }

    @Override
    public boolean voltarParaRascunho(Integer id) {
        Material m = buscarPorId(id);
        if (m == null) return false;
        m.setStatus(StatusConteudo.RASCUNHO);
        m.setSituacao("ativo");
        m.setDataAtualizacao(LocalDateTime.now());
        materialRepository.save(m);
        return true;
    }

    @Override
    public List<Material> buscarPublicados() {
        return materialRepository.findByStatusAndSituacao(StatusConteudo.PUBLICADO, "ativo");
    }

    @Override
    public List<Material> buscarPorArea(String area) {
        return materialRepository.findByAreaAndStatusAndSituacao(area, StatusConteudo.PUBLICADO, "ativo");
    }

    @Override
    public List<Material> buscarPorStatus(StatusConteudo status) {
        return materialRepository.findByStatusAndSituacao(status, "ativo");
    }

    @Override
    public List<Material> buscarPorTipoArquivo(String tipoArquivo) {
        return materialRepository.findByTipoArquivo(tipoArquivo);
    }

    @Override
    public long contarDownloads(Integer materialId) {
        return 0;
    }

    @Override
    public long contarPorStatus(StatusConteudo status) {
        return materialRepository.findByStatusAndSituacao(status, "ativo").size();
    }

    @Override
    public long contarPorArea(String area) {
        return materialRepository.findByAreaAndStatusAndSituacao(area, StatusConteudo.PUBLICADO, "ativo").size();
    }

    @Override
    public long contarPorProfessor(Integer professorId) {
        return materialRepository.findByProfessorIdAndSituacaoNot(professorId, "excluido").size();
    }
}
