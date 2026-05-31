package com.example.learnwave.model.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "downloads_materiais")
public class DownloadMaterial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "aluno_id", nullable = false)
    private Integer alunoId;
    @Column(name = "material_id", nullable = false)
    private Integer materialId;
    @Column(name = "data_download")
    private LocalDateTime dataDownload;

    // Relacionamentos
    @Transient
    private Usuario aluno;
    @Transient
    private Material material;

    public DownloadMaterial() {}

    public DownloadMaterial(Integer alunoId, Integer materialId) {
        this.alunoId = alunoId;
        this.materialId = materialId;
        this.dataDownload = LocalDateTime.now();
    }

    // Getters e Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getAlunoId() { return alunoId; }
    public void setAlunoId(Integer alunoId) { this.alunoId = alunoId; }

    public Integer getMaterialId() { return materialId; }
    public void setMaterialId(Integer materialId) { this.materialId = materialId; }

    public LocalDateTime getDataDownload() { return dataDownload; }
    public void setDataDownload(LocalDateTime dataDownload) { this.dataDownload = dataDownload; }

    public Usuario getAluno() { return aluno; }
    public void setAluno(Usuario aluno) { this.aluno = aluno; }

    public Material getMaterial() { return material; }
    public void setMaterial(Material material) { this.material = material; }
}
