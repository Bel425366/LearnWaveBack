package com.example.learnwave.service;

import com.example.learnwave.dao.MaterialDAO;
import com.example.learnwave.enums.StatusConteudo;
import com.example.learnwave.model.entity.Material;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MaterialService {

    @Autowired
    private MaterialDAO materialDAO;

    public Material criarMaterial(Material material) {
        return materialDAO.salvar(material);
    }

    public Material buscarPorId(Integer id) {
        return materialDAO.buscarPorId(id);
    }

    public List<Material> listarTodos() {
        return materialDAO.listarTodos();
    }

    public List<Material> listarPorProfessor(Integer professorId) {
        return materialDAO.buscarPorProfessor(professorId);
    }

    public List<Material> listarPorArea(String area) {
        return materialDAO.buscarPorAreaEStatus(area, StatusConteudo.PUBLICADO);
    }

    public Material atualizar(Material material) {
        return materialDAO.atualizar(material);
    }

    public boolean deletar(Integer id) {
        return materialDAO.deletar(id);
    }

    public boolean publicar(Integer id) {
        return materialDAO.publicar(id);
    }

    public boolean arquivar(Integer id) {
        return materialDAO.arquivar(id);
    }

    public List<Material> buscarPorStatus(String status) {
        StatusConteudo statusEnum = StatusConteudo.fromString(status);
        return materialDAO.buscarPorStatus(statusEnum);
    }

    public List<Material> buscarPorTipoArquivo(String tipo) {
        return materialDAO.buscarPorTipoArquivo(tipo);
    }

    public boolean voltarParaRascunho(Integer id) {
        return materialDAO.voltarParaRascunho(id);
    }

    public List<Material> listarPublicados() {
        return materialDAO.buscarPublicados();
    }

    public Long contarDownloads(Integer id) {
        return materialDAO.contarDownloads(id);
    }
}
