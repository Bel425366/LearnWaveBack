package com.example.learnwave.dao;


import com.example.learnwave.enums.StatusConteudo;
import com.example.learnwave.model.entity.Material;

import java.util.List;

public interface MaterialDAO {

    // Operações CRUD básicas
    Material salvar(Material material);
    Material buscarPorId(Integer id);
    List<Material> listarTodos();
    Material atualizar(Material material);
    boolean deletar(Integer id);

    // Consultas específicas
    List<Material> buscarPorProfessor(Integer professorId);
    List<Material> buscarPorArea(String area);
    List<Material> buscarPorStatus(StatusConteudo status);
    List<Material> buscarPublicados();
    List<Material> buscarPorAreaEStatus(String area, StatusConteudo status);
    List<Material> buscarPorTipoArquivo(String tipoArquivo);

    // Operações de status
    boolean publicar(Integer id);
    boolean arquivar(Integer id);
    boolean voltarParaRascunho(Integer id);

    // Estatísticas
    long contarPorProfessor(Integer professorId);
    long contarPorArea(String area);
    long contarPorStatus(StatusConteudo status);
    long contarDownloads(Integer materialId);
}
