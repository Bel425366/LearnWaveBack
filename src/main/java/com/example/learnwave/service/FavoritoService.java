package com.example.learnwave.service;

import com.example.learnwave.model.entity.ProfessorFavorito;
import com.example.learnwave.repository.ProfessorFavoritoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FavoritoService {

    @Autowired
    private ProfessorFavoritoRepository favoritoRepository;

    public ProfessorFavorito favoritar(Integer alunoId, Integer professorId) {
        // Verificar se já é favorito
        if (favoritoRepository.existsByAlunoIdAndProfessorId(alunoId, professorId)) {
            return favoritoRepository.findByAlunoIdAndProfessorId(alunoId, professorId).orElse(null);
        }
        ProfessorFavorito favorito = new ProfessorFavorito(alunoId, professorId);
        favorito.setDataFavoritado(LocalDateTime.now());
        return favoritoRepository.save(favorito);
    }

    @Transactional
    public boolean desfavoritar(Integer alunoId, Integer professorId) {
        if (!favoritoRepository.existsByAlunoIdAndProfessorId(alunoId, professorId)) {
            return false;
        }
        favoritoRepository.deleteByAlunoIdAndProfessorId(alunoId, professorId);
        return true;
    }

    public List<ProfessorFavorito> listarFavoritos(Integer alunoId) {
        return favoritoRepository.findByAlunoId(alunoId);
    }

    public boolean isFavorito(Integer alunoId, Integer professorId) {
        return favoritoRepository.existsByAlunoIdAndProfessorId(alunoId, professorId);
    }
}
