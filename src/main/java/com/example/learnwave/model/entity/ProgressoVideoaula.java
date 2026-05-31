package com.example.learnwave.model.entity;

import com.example.learnwave.enums.StatusProgresso;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "progresso_videoaulas")
public class ProgressoVideoaula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "aluno_id", nullable = false)
    private Integer alunoId;
    @Column(name = "videoaula_id", nullable = false)
    private Integer videoaulaId;
    @Enumerated(EnumType.STRING)
    private StatusProgresso status;
    @Column(name = "tempo_assistido")
    private Integer tempoAssistido; // em segundos
    @Column(name = "data_inicio")
    private LocalDateTime dataInicio;
    @Column(name = "data_conclusao")
    private LocalDateTime dataConclusao;
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    // Relacionamentos
    @Transient
    private Usuario aluno;
    @Transient
    private Videoaula videoaula;

    public ProgressoVideoaula() {}

    public ProgressoVideoaula(Integer alunoId, Integer videoaulaId) {
        this.alunoId = alunoId;
        this.videoaulaId = videoaulaId;
        this.status = StatusProgresso.NAO_INICIADO;
        this.tempoAssistido = 0;
        this.dataInicio = LocalDateTime.now();
        this.dataAtualizacao = LocalDateTime.now();
    }

    // Getters e Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getAlunoId() { return alunoId; }
    public void setAlunoId(Integer alunoId) { this.alunoId = alunoId; }

    public Integer getVideoaulaId() { return videoaulaId; }
    public void setVideoaulaId(Integer videoaulaId) { this.videoaulaId = videoaulaId; }

    public StatusProgresso getStatus() { return status; }
    public void setStatus(StatusProgresso status) { this.status = status; }

    public Integer getTempoAssistido() { return tempoAssistido; }
    public void setTempoAssistido(Integer tempoAssistido) { this.tempoAssistido = tempoAssistido; }

    public LocalDateTime getDataInicio() { return dataInicio; }
    public void setDataInicio(LocalDateTime dataInicio) { this.dataInicio = dataInicio; }

    public LocalDateTime getDataConclusao() { return dataConclusao; }
    public void setDataConclusao(LocalDateTime dataConclusao) { this.dataConclusao = dataConclusao; }

    public LocalDateTime getDataAtualizacao() { return dataAtualizacao; }
    public void setDataAtualizacao(LocalDateTime dataAtualizacao) { this.dataAtualizacao = dataAtualizacao; }

    public Usuario getAluno() { return aluno; }
    public void setAluno(Usuario aluno) { this.aluno = aluno; }

    public Videoaula getVideoaula() { return videoaula; }
    public void setVideoaula(Videoaula videoaula) { this.videoaula = videoaula; }
}
