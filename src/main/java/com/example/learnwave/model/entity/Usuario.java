package com.example.learnwave.model.entity;

import com.example.learnwave.enums.StatusVerificacao;
import com.example.learnwave.enums.TipoUsuario;
import com.example.learnwave.config.TipoUsuarioDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonAlias;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String nome;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String senha;
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_usuario", nullable = false)
    @JsonDeserialize(using = TipoUsuarioDeserializer.class)
    @JsonProperty("tipo")
    @JsonAlias({"tipoUsuario", "tipo_usuario", "userType"})
    private TipoUsuario tipo;
    private String cpf;
    private String telefone;
    private String escola;
    @Column(name = "documento_url", columnDefinition = "TEXT")
    private String documentoUrl;
    @Enumerated(EnumType.STRING)
    @Column(name = "status_verificacao")
    private StatusVerificacao statusVerificacao;
    @Column(name = "area_ensino")
    private String areaEnsino;
    private String formacao;
    private String experiencia;
    @Column(nullable = true, length = 500)
    private String bio;
    @Column(name = "fotoperfil", columnDefinition = "TEXT")
    private String fotoPerfil;
    private String status;
    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    // Relacionamentos
    @Transient
    private List<Atividade> atividades;
    @Transient
    private List<Videoaula> videoaulas;
    @Transient
    private List<Material> materiais;
    @Transient
    private List<ProgressoAtividade> progressoAtividades;
    @Transient
    private List<ProgressoVideoaula> progressoVideoaulas;
    @Transient
    private List<DownloadMaterial> downloads;

    public Usuario() {}

    public Usuario(String nome, String email, String senha, TipoUsuario tipo) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.tipo = tipo;
        // Status será definido no service baseado no tipo
        this.status = "ativo";
        this.dataCriacao = LocalDateTime.now();
        this.dataAtualizacao = LocalDateTime.now();
    }

    // Getters e Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public TipoUsuario getTipo() { return tipo; }
    public void setTipo(TipoUsuario tipo) { this.tipo = tipo; }
    
    public void setTipoFromString(String tipoStr) {
        if (tipoStr != null && !tipoStr.trim().isEmpty()) {
            this.tipo = TipoUsuario.fromString(tipoStr);
        }
    }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getEscola() { return escola; }
    public void setEscola(String escola) { this.escola = escola; }

    public String getDocumentoUrl() { return documentoUrl; }
    public void setDocumentoUrl(String documentoUrl) { this.documentoUrl = documentoUrl; }

    public StatusVerificacao getStatusVerificacao() { return statusVerificacao; }
    public void setStatusVerificacao(StatusVerificacao statusVerificacao) { this.statusVerificacao = statusVerificacao; }

    public String getAreaEnsino() { return areaEnsino; }
    public void setAreaEnsino(String areaEnsino) { this.areaEnsino = areaEnsino; }

    public String getFormacao() { return formacao; }
    public void setFormacao(String formacao) { this.formacao = formacao; }

    public String getExperiencia() { return experiencia; }
    public void setExperiencia(String experiencia) { this.experiencia = experiencia; }

    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }

    public String getFotoPerfil() { return fotoPerfil; }
    public void setFotoPerfil(String fotoPerfil) { this.fotoPerfil = fotoPerfil; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }

    public LocalDateTime getDataAtualizacao() { return dataAtualizacao; }
    public void setDataAtualizacao(LocalDateTime dataAtualizacao) { this.dataAtualizacao = dataAtualizacao; }

    public List<Atividade> getAtividades() { return atividades; }
    public void setAtividades(List<Atividade> atividades) { this.atividades = atividades; }

    public List<Videoaula> getVideoaulas() { return videoaulas; }
    public void setVideoaulas(List<Videoaula> videoaulas) { this.videoaulas = videoaulas; }

    public List<Material> getMateriais() { return materiais; }
    public void setMateriais(List<Material> materiais) { this.materiais = materiais; }

    public List<ProgressoAtividade> getProgressoAtividades() { return progressoAtividades; }
    public void setProgressoAtividades(List<ProgressoAtividade> progressoAtividades) { this.progressoAtividades = progressoAtividades; }

    public List<ProgressoVideoaula> getProgressoVideoaulas() { return progressoVideoaulas; }
    public void setProgressoVideoaulas(List<ProgressoVideoaula> progressoVideoaulas) { this.progressoVideoaulas = progressoVideoaulas; }

    public List<DownloadMaterial> getDownloads() { return downloads; }
    public void setDownloads(List<DownloadMaterial> downloads) { this.downloads = downloads; }
}
