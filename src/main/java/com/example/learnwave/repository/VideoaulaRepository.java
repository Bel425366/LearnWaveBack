package com.example.learnwave.repository;

import com.example.learnwave.enums.StatusConteudo;
import com.example.learnwave.model.entity.Videoaula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoaulaRepository extends JpaRepository<Videoaula, Integer> {
    // Buscar por professor excluindo videoaulas na lixeira
    List<Videoaula> findByProfessorIdAndStatusNot(Integer professorId, StatusConteudo status);
    // Buscar por professor com status específico (ex: LIXEIRA)
    List<Videoaula> findByProfessorIdAndStatus(Integer professorId, StatusConteudo status);
    // Buscar por status
    List<Videoaula> findByStatus(StatusConteudo status);
    // Buscar por área e status
    List<Videoaula> findByAreaAndStatus(String area, StatusConteudo status);
    // Listar todas exceto as que estão na lixeira
    List<Videoaula> findByStatusNot(StatusConteudo status);
    // Buscar por duração
    List<Videoaula> findByDuracao(String duracao);
}
