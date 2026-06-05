package com.example.learnwave.service;

import com.example.learnwave.dao.VideoaulaDAO;
import com.example.learnwave.dao.impl.VideoaulaDAOImpl;
import com.example.learnwave.enums.StatusConteudo;
import com.example.learnwave.model.entity.Videoaula;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoaulaService {

    @Autowired
    private VideoaulaDAO videoaulaDAO;

    public Videoaula criarVideoaula(Videoaula videoaula) {
        return videoaulaDAO.salvar(videoaula);
    }

    public Videoaula buscarPorId(Integer id) {
        return videoaulaDAO.buscarPorId(id);
    }

    public List<Videoaula> listarTodas() {
        return videoaulaDAO.listarTodas();
    }

    public List<Videoaula> listarPorProfessor(Integer professorId) {
        return videoaulaDAO.buscarPorProfessor(professorId);
    }

    public List<Videoaula> listarPorArea(String area) {
        return videoaulaDAO.buscarPorAreaEStatus(area, StatusConteudo.PUBLICADO);
    }

    public Videoaula atualizar(Videoaula videoaula) {
        return videoaulaDAO.atualizar(videoaula);
    }

    public boolean deletar(Integer id) {
        return videoaulaDAO.deletar(id);
    }

    public boolean publicar(Integer id) {
        return videoaulaDAO.publicar(id);
    }

    public boolean arquivar(Integer id) {
        return videoaulaDAO.arquivar(id);
    }

    /**
     * Move a videoaula para a lixeira (soft delete).
     * A videoaula deixa de aparecer para alunos.
     */
    public boolean moverParaLixeira(Integer id) {
        Videoaula v = videoaulaDAO.buscarPorId(id);
        if (v == null) return false;
        v.setStatus(StatusConteudo.LIXEIRA);
        videoaulaDAO.atualizar(v);
        return true;
    }

    /**
     * Restaura a videoaula da lixeira, voltando para RASCUNHO.
     * O professor pode então publicá-la novamente.
     */
    public boolean restaurar(Integer id) {
        Videoaula v = videoaulaDAO.buscarPorId(id);
        if (v == null) return false;
        v.setStatus(StatusConteudo.RASCUNHO);
        videoaulaDAO.atualizar(v);
        return true;
    }

    public List<Videoaula> listarNaLixeiraPorProfessor(Integer professorId) {
        if (videoaulaDAO instanceof VideoaulaDAOImpl) {
            return ((VideoaulaDAOImpl) videoaulaDAO).buscarNaLixeiraPorProfessor(professorId);
        }
        return List.of();
    }

    public List<Videoaula> buscarPorStatus(String status) {
        return videoaulaDAO.buscarPorStatus(status);
    }

    public boolean voltarParaRascunho(Integer id) {
        return videoaulaDAO.voltarParaRascunho(id);
    }

    public List<Videoaula> listarPublicadas() {
        return videoaulaDAO.buscarPublicadas();
    }

    public List<Videoaula> buscarPorDuracao(String duracao) {
        return videoaulaDAO.buscarPorDuracao(duracao);
    }

    public List<Integer> listarProfessoresComVideoaulasPublicadas() {
        return videoaulaDAO.buscarPublicadas().stream()
                .map(Videoaula::getProfessorId)
                .distinct()
                .toList();
    }
}
