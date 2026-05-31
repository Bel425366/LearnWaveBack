package com.example.learnwave.dao.impl;

import com.example.learnwave.dao.MaterialDAO;
import com.example.learnwave.enums.StatusConteudo;
import com.example.learnwave.model.entity.Material;
import com.example.learnwave.repository.DownloadMaterialRepository;
import com.example.learnwave.repository.MaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class MaterialDAOImpl implements MaterialDAO {

    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private DownloadMaterialRepository downloadMaterialRepository;

    @Override
    public Material salvar(Material material) {
        if (material.getStatus() == null) material.setStatus(StatusConteudo.RASCUNHO);
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
        return materialRepository.findByStatusNot(StatusConteudo.LIXEIRA);
    }

    @Override
    public Material atualizar(Material material) {
        Material existente = buscarPorId(material.getId());
        if (existente != null) {
            if (material.getDataCriacao() == null) material.setDataCriacao(existente.getDataCriacao());
            if (material.getStatus() == null) material.setStatus(existente.getStatus());
        }
        material.setDataAtualizacao(LocalDateTime.now());
        return materialRepository.save(material);
    }

    @Override
    public boolean deletar(Integer id) {
        Material m = buscarPorId(id);
        if (m == null) return false;
        // Soft delete: mover para lixeira
        m.setStatus(StatusConteudo.LIXEIRA);
        m.setDataAtualizacao(LocalDateTime.now());
        materialRepository.save(m);
        return true;
    }

    @Override
    public List<Material> buscarPorProfessor(Integer professorId) {
        return materialRepository.findByProfessorIdAndStatusNot(professorId, StatusConteudo.LIXEIRA);
    }

    @Override
    public List<Material> buscarPorAreaEStatus(String area, StatusConteudo status) {
        return materialRepository.findByAreaAndStatus(area, status);
    }

    @Override
    public boolean publicar(Integer id) {
        Material m = buscarPorId(id);
        if (m == null) return false;
        m.setStatus(StatusConteudo.PUBLICADO);
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
        m.setDataAtualizacao(LocalDateTime.now());
        materialRepository.save(m);
        return true;
    }

    @Override
    public List<Material> buscarPublicados() {
        return materialRepository.findByStatus(StatusConteudo.PUBLICADO);
    }

    @Override
    public List<Material> buscarPorArea(String area) {
        return materialRepository.findByAreaAndStatus(area, StatusConteudo.PUBLICADO);
    }

    @Override
    public List<Material> buscarPorStatus(StatusConteudo status) {
        return materialRepository.findByStatus(status);
    }

    @Override
    public List<Material> buscarPorTipoArquivo(String tipoArquivo) {
        return materialRepository.findByTipoArquivo(tipoArquivo);
    }

    @Override
    public long contarDownloads(Integer materialId) {
        return downloadMaterialRepository.countByMaterialId(materialId);
    }

    @Override
    public long contarPorStatus(StatusConteudo status) {
        return materialRepository.findByStatus(status).size();
    }

    @Override
    public long contarPorArea(String area) {
        return materialRepository.findByAreaAndStatus(area, StatusConteudo.PUBLICADO).size();
    }

    @Override
    public long contarPorProfessor(Integer professorId) {
        return materialRepository.findByProfessorIdAndStatusNot(professorId, StatusConteudo.LIXEIRA).size();
    }

    public List<Material> buscarNaLixeiraPorProfessor(Integer professorId) {
        return materialRepository.findByProfessorIdAndStatus(professorId, StatusConteudo.LIXEIRA);
    }
}
