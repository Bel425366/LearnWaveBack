package com.example.learnwave.dao.impl;

import com.example.learnwave.dao.MaterialDAO;
import com.example.learnwave.enums.StatusConteudo;
import com.example.learnwave.model.entity.Material;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MaterialDAOImpl implements MaterialDAO {

    @Override
    public Material salvar(Material material) {
        return material;
    }

    @Override
    public Material buscarPorId(Integer id) {
        return null;
    }

    @Override
    public List<Material> listarTodos() {
        return new ArrayList<>();
    }

    @Override
    public Material atualizar(Material material) {
        return material;
    }

    @Override
    public boolean deletar(Integer id) {
        return true;
    }

    @Override
    public List<Material> buscarPorProfessor(Integer professorId) {
        return new ArrayList<>();
    }

    @Override
    public List<Material> buscarPorAreaEStatus(String area, StatusConteudo status) {
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
    public long contarDownloads(Integer materialId) {
        return 0;
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
    public List<Material> buscarPublicados() {
        return new ArrayList<>();
    }

    @Override
    public List<Material> buscarPorArea(String area) {
        return new ArrayList<>();
    }

    @Override
    public List<Material> buscarPorStatus(StatusConteudo status) {
        return new ArrayList<>();
    }

    @Override
    public List<Material> buscarPorTipoArquivo(String tipoArquivo) {
        return new ArrayList<>();
    }
}