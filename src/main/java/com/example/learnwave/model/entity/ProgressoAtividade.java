package com.example.learnwave.model.entity;

import com.example.learnwave.enums.StatusProgresso;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "progresso_atividades")
public class ProgressoAtividade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "aluno_id", nullable = false)
    private Integer alunoId;
    @Column(name = "atividade_id", nullable = false)
    private Integer atividadeId;
    @Enumerated(EnumType.STRING)
    private StatusProgresso status;
    @Column(precision = 4, scale = 2)
    private BigDecimal nota;
    private Integer tentativas;
    @Column(name = "data_inicio")
    private LocalDateTime dataInicio;
    @Column(name = "data_conclusao")
    private LocalDateTime dataConclusao;
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    // Relacionamentos (transient para evitar lazy loading issues)
    @Transient
    private Usuario aluno;
    @Transient
    private Atividade atividade;

    public ProgressoAtividade() {}

    public ProgressoAtividade(Integer alunoId, Integer atividadeId) {
        this.alunoId = alunoId;
        this.atividadeId = atividadeId;
        this.status = StatusProgresso.NAO_INICIADO;
        this.tentativas = 0;
        this.dataInicio = LocalDateTime.now();
        this.dataAtualizacao = LocalDateTime.now();
    }

    // Getters e Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getAlunoId() { return alunoId; }
    public void setAlunoId(Integer alunoId) { this.alunoId = alunoId; }

    public Integer getAtividadeId() { return atividadeId; }
    public void setAtividadeId(Integer atividadeId) { this.atividadeId = atividadeId; }

    public StatusProgresso getStatus() { return status; }
    public void setStatus(StatusProgresso status) { this.status = status; }

    public BigDecimal getNota() { return nota; }
    public void setNota(BigDecimal nota) { this.nota = nota; }

    public Integer getTentativas() { return tentativas; }
    public void setTentativas(Integer tentativas) { this.tentativas = tentativas; }

    public LocalDateTime getDataInicio() { return dataInicio; }
    public void setDataInicio(LocalDateTime dataInicio) { this.dataInicio = dataInicio; }

    public LocalDateTime getDataConclusao() { return dataConclusao; }
    public void setDataConclusao(LocalDateTime dataConclusao) { this.dataConclusao = dataConclusao; }

    public LocalDateTime getDataAtualizacao() { return dataAtualizacao; }
    public void setDataAtualizacao(LocalDateTime dataAtualizacao) { this.dataAtualizacao = dataAtualizacao; }

    public Usuario getAluno() { return aluno; }
    public void setAluno(Usuario aluno) { this.aluno = aluno; }

    public Atividade getAtividade() { return atividade; }
    public void setAtividade(Atividade atividade) { this.atividade = atividade; }
}
