package com.example.learnwave.model.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "professores_favoritos")
public class ProfessorFavorito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "aluno_id", nullable = false)
    private Integer alunoId;
    @Column(name = "professor_id", nullable = false)
    private Integer professorId;
    @Column(name = "data_favoritado")
    private LocalDateTime dataFavoritado;

    public ProfessorFavorito() {}

    public ProfessorFavorito(Integer alunoId, Integer professorId) {
        this.alunoId = alunoId;
        this.professorId = professorId;
        this.dataFavoritado = LocalDateTime.now();
    }

    // Getters e Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getAlunoId() { return alunoId; }
    public void setAlunoId(Integer alunoId) { this.alunoId = alunoId; }

    public Integer getProfessorId() { return professorId; }
    public void setProfessorId(Integer professorId) { this.professorId = professorId; }

    public LocalDateTime getDataFavoritado() { return dataFavoritado; }
    public void setDataFavoritado(LocalDateTime dataFavoritado) { this.dataFavoritado = dataFavoritado; }
}
