package com.example.learnwave.repository;

import com.example.learnwave.model.entity.ProfessorFavorito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfessorFavoritoRepository extends JpaRepository<ProfessorFavorito, Integer> {
    List<ProfessorFavorito> findByAlunoId(Integer alunoId);
    Optional<ProfessorFavorito> findByAlunoIdAndProfessorId(Integer alunoId, Integer professorId);
    void deleteByAlunoIdAndProfessorId(Integer alunoId, Integer professorId);
    boolean existsByAlunoIdAndProfessorId(Integer alunoId, Integer professorId);
}
