package com.example.learnwave.repository;

import com.example.learnwave.enums.StatusProgresso;
import com.example.learnwave.model.entity.ProgressoAtividade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProgressoAtividadeRepository extends JpaRepository<ProgressoAtividade, Integer> {
    List<ProgressoAtividade> findByAlunoId(Integer alunoId);
    List<ProgressoAtividade> findByAtividadeId(Integer atividadeId);
    Optional<ProgressoAtividade> findByAlunoIdAndAtividadeId(Integer alunoId, Integer atividadeId);
    List<ProgressoAtividade> findByAlunoIdAndStatus(Integer alunoId, StatusProgresso status);

    /**
     * Calcula a média das notas do aluno considerando apenas atividades PUBLICADAS.
     * Atividades na LIXEIRA ou RASCUNHO não contam.
     */
    @Query("SELECT AVG(pa.nota) FROM ProgressoAtividade pa " +
           "WHERE pa.alunoId = :alunoId " +
           "AND pa.nota IS NOT NULL " +
           "AND pa.atividadeId IN (" +
           "  SELECT a.id FROM Atividade a WHERE a.status = com.example.learnwave.enums.StatusConteudo.PUBLICADO" +
           ")")
    BigDecimal calcularMediaAluno(@Param("alunoId") Integer alunoId);

    /**
     * Conta atividades concluídas pelo aluno (apenas atividades publicadas).
     */
    @Query("SELECT COUNT(pa) FROM ProgressoAtividade pa " +
           "WHERE pa.alunoId = :alunoId " +
           "AND pa.status = com.example.learnwave.enums.StatusProgresso.CONCLUIDO " +
           "AND pa.atividadeId IN (" +
           "  SELECT a.id FROM Atividade a WHERE a.status = com.example.learnwave.enums.StatusConteudo.PUBLICADO" +
           ")")
    long contarAtividadesConcluidasAtivas(@Param("alunoId") Integer alunoId);

    /**
     * Conta total de atividades publicadas disponíveis.
     */
    @Query("SELECT COUNT(a) FROM Atividade a " +
           "WHERE a.status = com.example.learnwave.enums.StatusConteudo.PUBLICADO")
    long contarAtividadesPublicadasAtivas();
}
