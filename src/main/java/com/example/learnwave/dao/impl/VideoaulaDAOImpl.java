package com.example.learnwave.dao.impl;

import com.example.learnwave.dao.VideoaulaDAO;
import com.example.learnwave.enums.StatusConteudo;
import com.example.learnwave.model.entity.Videoaula;
import com.example.learnwave.repository.VideoaulaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class VideoaulaDAOImpl implements VideoaulaDAO {

    @Autowired
    private VideoaulaRepository videoaulaRepository;

    @Override
    public Videoaula salvar(Videoaula videoaula) {
        if (videoaula.getStatus() == null) videoaula.setStatus(StatusConteudo.RASCUNHO);
        videoaula.setDataCriacao(LocalDateTime.now());
        videoaula.setDataAtualizacao(LocalDateTime.now());
        // Gerar thumbnail automaticamente a partir do link do YouTube
        videoaula.gerarThumbnailDoYouTube();
        return videoaulaRepository.save(videoaula);
    }

    @Override
    public Videoaula buscarPorId(Integer id) {
        return videoaulaRepository.findById(id).orElse(null);
    }

    @Override
    public List<Videoaula> listarTodas() {
        return videoaulaRepository.findByStatusNot(StatusConteudo.LIXEIRA);
    }

    @Override
    public Videoaula atualizar(Videoaula videoaula) {
        Videoaula existente = buscarPorId(videoaula.getId());
        if (existente != null) {
            if (videoaula.getDataCriacao() == null) videoaula.setDataCriacao(existente.getDataCriacao());
            if (videoaula.getStatus() == null) videoaula.setStatus(existente.getStatus());
        }
        videoaula.setDataAtualizacao(LocalDateTime.now());
        // Regerar thumbnail se URL mudou
        videoaula.gerarThumbnailDoYouTube();
        return videoaulaRepository.save(videoaula);
    }

    @Override
    public boolean deletar(Integer id) {
        Videoaula v = buscarPorId(id);
        if (v == null) return false;
        // Soft delete: mover para lixeira
        v.setStatus(StatusConteudo.LIXEIRA);
        v.setDataAtualizacao(LocalDateTime.now());
        videoaulaRepository.save(v);
        return true;
    }

    @Override
    public List<Videoaula> buscarPorProfessor(Integer professorId) {
        return videoaulaRepository.findByProfessorIdAndStatusNot(professorId, StatusConteudo.LIXEIRA);
    }

    @Override
    public List<Videoaula> buscarPorAreaEStatus(String area, StatusConteudo status) {
        return videoaulaRepository.findByAreaAndStatus(area, status);
    }

    @Override
    public boolean publicar(Integer id) {
        Videoaula v = buscarPorId(id);
        if (v == null) return false;
        v.setStatus(StatusConteudo.PUBLICADO);
        v.setDataAtualizacao(LocalDateTime.now());
        videoaulaRepository.save(v);
        return true;
    }

    @Override
    public boolean arquivar(Integer id) {
        Videoaula v = buscarPorId(id);
        if (v == null) return false;
        v.setStatus(StatusConteudo.ARQUIVADO);
        v.setDataAtualizacao(LocalDateTime.now());
        videoaulaRepository.save(v);
        return true;
    }

    @Override
    public boolean voltarParaRascunho(Integer id) {
        Videoaula v = buscarPorId(id);
        if (v == null) return false;
        v.setStatus(StatusConteudo.RASCUNHO);
        v.setDataAtualizacao(LocalDateTime.now());
        videoaulaRepository.save(v);
        return true;
    }

    @Override
    public List<Videoaula> buscarPublicadas() {
        return videoaulaRepository.findByStatus(StatusConteudo.PUBLICADO);
    }

    @Override
    public List<Videoaula> buscarPorArea(String area) {
        return videoaulaRepository.findByAreaAndStatus(area, StatusConteudo.PUBLICADO);
    }

    @Override
    public List<Videoaula> buscarPorStatus(StatusConteudo status) {
        return videoaulaRepository.findByStatus(status);
    }

    @Override
    public List<Videoaula> buscarPorStatus(String status) {
        StatusConteudo statusEnum = StatusConteudo.fromString(status);
        return videoaulaRepository.findByStatus(statusEnum);
    }

    @Override
    public List<Videoaula> buscarPorDuracao(String duracao) {
        return videoaulaRepository.findByDuracao(duracao);
    }

    @Override
    public long contarPorStatus(StatusConteudo status) {
        return videoaulaRepository.findByStatus(status).size();
    }

    @Override
    public long contarPorArea(String area) {
        return videoaulaRepository.findByAreaAndStatus(area, StatusConteudo.PUBLICADO).size();
    }

    @Override
    public long contarPorProfessor(Integer professorId) {
        return videoaulaRepository.findByProfessorIdAndStatusNot(professorId, StatusConteudo.LIXEIRA).size();
    }

    // Métodos adicionais para lixeira
    public List<Videoaula> buscarNaLixeiraPorProfessor(Integer professorId) {
        return videoaulaRepository.findByProfessorIdAndStatus(professorId, StatusConteudo.LIXEIRA);
    }
}
