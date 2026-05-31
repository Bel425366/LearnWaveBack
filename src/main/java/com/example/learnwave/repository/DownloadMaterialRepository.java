package com.example.learnwave.repository;

import com.example.learnwave.model.entity.DownloadMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DownloadMaterialRepository extends JpaRepository<DownloadMaterial, Integer> {
    List<DownloadMaterial> findByAlunoId(Integer alunoId);
    List<DownloadMaterial> findByMaterialId(Integer materialId);
    long countByMaterialId(Integer materialId);
}
