package com.example.learnwave.repository;

import com.example.learnwave.enums.StatusConteudo;
import com.example.learnwave.model.entity.Atividade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AtividadeRepository extends JpaRepository<Atividade, Integer> {
    List<Atividade> findByProfessorIdAndSituacaoNot(Integer professorId, String situacao);
    List<Atividade> findByProfessorIdAndSituacao(Integer professorId, String situacao);
    List<Atividade> findByStatusAndSituacao(StatusConteudo status, String situacao);
    List<Atividade> findByAreaAndStatusAndSituacao(String area, StatusConteudo status, String situacao);
    List<Atividade> findBySituacaoNot(String situacao);
}
