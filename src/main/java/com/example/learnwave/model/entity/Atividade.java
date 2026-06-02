package com.example.learnwave.model.entity;

import com.example.learnwave.enums.StatusConteudo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "atividades")
public class Atividade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String titulo;
    private String descricao;
    @Column(nullable = false)
    private String area;
    @Column(name = "professor_id", nullable = false)
    private Integer professorId;
    @Column(columnDefinition = "NTEXT")
    private String conteudo;
    @Enumerated(EnumType.STRING)
    private StatusConteudo status;
    // Coluna mantida no banco para compatibilidade, mas ignorada na lógica
    @JsonIgnore
    @Column(name = "situacao")
    private String situacao = "ativo";
    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @Transient
    private Usuario professor;
    @Transient
    private List<ProgressoAtividade> progressos;

    public Atividade() {}

    public Atividade(String titulo, String descricao, String area, Integer professorId, String conteudo) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.area = area;
        this.professorId = professorId;
        this.conteudo = conteudo;
        this.status = StatusConteudo.RASCUNHO;
        this.dataCriacao = LocalDateTime.now();
        this.dataAtualizacao = LocalDateTime.now();
    }

    // Getters e Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getArea() { return area; }
    public void setArea(String area) { this.area = area; }

    public Integer getProfessorId() { return professorId; }
    public void setProfessorId(Integer professorId) { this.professorId = professorId; }

    public String getConteudo() { return conteudo; }
    public void setConteudo(String conteudo) { this.conteudo = conteudo; }

    public StatusConteudo getStatus() { return status; }
    public void setStatus(StatusConteudo status) { this.status = status; }

    public String getSituacao() { return situacao; }
    public void setSituacao(String situacao) { this.situacao = situacao; }

    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }

    public LocalDateTime getDataAtualizacao() { return dataAtualizacao; }
    public void setDataAtualizacao(LocalDateTime dataAtualizacao) { this.dataAtualizacao = dataAtualizacao; }

    public Usuario getProfessor() { return professor; }
    public void setProfessor(Usuario professor) { this.professor = professor; }

    public List<ProgressoAtividade> getProgressos() { return progressos; }
    public void setProgressos(List<ProgressoAtividade> progressos) { this.progressos = progressos; }
}
