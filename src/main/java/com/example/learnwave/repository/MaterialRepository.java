package com.example.learnwave.repository;

import com.example.learnwave.enums.StatusConteudo;
import com.example.learnwave.model.entity.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Integer> {
    List<Material> findByProfessorIdAndStatusNot(Integer professorId, StatusConteudo status);
    List<Material> findByProfessorIdAndStatus(Integer professorId, StatusConteudo status);
    List<Material> findByStatus(StatusConteudo status);
    List<Material> findByAreaAndStatus(String area, StatusConteudo status);
    List<Material> findByStatusNot(StatusConteudo status);
    List<Material> findByTipoArquivo(String tipoArquivo);
}
