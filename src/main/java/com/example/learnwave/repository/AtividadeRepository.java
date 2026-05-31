package com.example.learnwave.repository;

import com.example.learnwave.enums.StatusConteudo;
import com.example.learnwave.model.entity.Atividade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AtividadeRepository extends JpaRepository<Atividade, Integer> {
    List<Atividade> findByProfessorIdAndStatusNot(Integer professorId, StatusConteudo status);
    List<Atividade> findByProfessorIdAndStatus(Integer professorId, StatusConteudo status);
    List<Atividade> findByStatus(StatusConteudo status);
    List<Atividade> findByAreaAndStatus(String area, StatusConteudo status);
    List<Atividade> findByStatusNot(StatusConteudo status);
}
