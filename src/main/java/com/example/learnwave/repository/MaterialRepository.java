package com.example.learnwave.repository;

import com.example.learnwave.enums.StatusConteudo;
import com.example.learnwave.model.entity.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Integer> {
    List<Material> findByProfessorIdAndSituacaoNot(Integer professorId, String situacao);
    List<Material> findByStatusAndSituacao(StatusConteudo status, String situacao);
    List<Material> findByAreaAndStatusAndSituacao(String area, StatusConteudo status, String situacao);
    List<Material> findBySituacaoNot(String situacao);
    List<Material> findByTipoArquivo(String tipoArquivo);
}
