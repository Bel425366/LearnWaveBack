package com.example.learnwave.repository;

import com.example.learnwave.enums.StatusProgresso;
import com.example.learnwave.model.entity.ProgressoVideoaula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProgressoVideoaulaRepository extends JpaRepository<ProgressoVideoaula, Integer> {
    List<ProgressoVideoaula> findByAlunoId(Integer alunoId);
    List<ProgressoVideoaula> findByVideoaulaId(Integer videoaulaId);
    Optional<ProgressoVideoaula> findByAlunoIdAndVideoaulaId(Integer alunoId, Integer videoaulaId);
    List<ProgressoVideoaula> findByAlunoIdAndStatus(Integer alunoId, StatusProgresso status);

    /**
     * Conta videoaulas concluídas pelo aluno (apenas videoaulas publicadas).
     * Videoaulas na lixeira (status=LIXEIRA) não contam.
     */
    @Query("SELECT COUNT(pv) FROM ProgressoVideoaula pv " +
           "WHERE pv.alunoId = :alunoId " +
           "AND pv.status = com.example.learnwave.enums.StatusProgresso.CONCLUIDO " +
           "AND pv.videoaulaId IN (" +
           "  SELECT v.id FROM Videoaula v WHERE v.status = com.example.learnwave.enums.StatusConteudo.PUBLICADO" +
           ")")
    long contarVideoaulasConcluidasAtivas(@Param("alunoId") Integer alunoId);
}
