package com.example.learnwave.dao.impl;

import com.example.learnwave.dao.DownloadMaterialDAO;
import com.example.learnwave.model.entity.DownloadMaterial;
import com.example.learnwave.repository.DownloadMaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class DownloadMaterialDAOImpl implements DownloadMaterialDAO {

    @Autowired
    private DownloadMaterialRepository downloadMaterialRepository;

    @Override
    public DownloadMaterial salvar(DownloadMaterial download) {
        if (download.getDataDownload() == null) {
            download.setDataDownload(LocalDateTime.now());
        }
        return downloadMaterialRepository.save(download);
    }

    @Override
    public List<DownloadMaterial> listarPorAluno(Integer alunoId) {
        return downloadMaterialRepository.findByAlunoId(alunoId);
    }

    @Override
    public List<DownloadMaterial> listarPorMaterial(Integer materialId) {
        return downloadMaterialRepository.findByMaterialId(materialId);
    }

    @Override
    public Long contarDownloads(Integer materialId) {
        return downloadMaterialRepository.countByMaterialId(materialId);
    }
}
